package io.web.bi.component;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import io.web.bi.config.ModelIdProperties;
import io.web.bi.manager.AIHelper;
import io.web.bi.model.entity.Assistant;
import io.web.bi.model.enums.ChartStatusEnum;
import io.web.bi.service.AssistantService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 消费者消费消息
 *
 * @date 2023-06-16
 */
@Component
@AllArgsConstructor
@Slf4j
public class QaComponent {

    private final static Gson GSON = new Gson();

    private final AIHelper aiHelper;
    private final ModelIdProperties modelIdProperties;

    private final AssistantService assistantService;

    /**
     * 指定转发消息规则：exchange 通过 key 将消息转发到 value(queue)
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("qaQueue"),
            exchange = @Exchange("qaDirectExchange"),
            key = "qaRouting"
    ))
    public void handle(Message message, Channel channel) throws IOException {
        Assistant assistant = null;
        try {
            String data = new String(message.getBody());
            assistant = GSON.fromJson(data, Assistant.class);
            String goal = assistant.getGoal();

            long biModelId = modelIdProperties.getAssistant();
            String result = aiHelper.doChat(biModelId, goal);
            assistant.setQuestionRes(result);
            assistant.setStatus(ChartStatusEnum.SUCCEED.getValue());
            assistantService.updateById(assistant);

            // 确认接受消息，消息可以从队列中移除了
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            // 出现异常后拒绝接受消息
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            if (assistant != null) {
                assistant.setStatus(ChartStatusEnum.FAILED.getValue());
                assistantService.updateById(assistant);
            }
        }
    }
}
