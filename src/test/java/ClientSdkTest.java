import com.yupi.yucongming.dev.client.YuCongMingClient;
import com.yupi.yucongming.dev.common.BaseResponse;
import com.yupi.yucongming.dev.model.DevChatRequest;
import com.yupi.yucongming.dev.model.DevChatResponse;

/**
 * ClientSdk.
 *
 * @date 2023-06-07
 */
public class ClientSdkTest {
    public static void main(String[] args) {
        String accessKey = "6dmz5g6ulrw0jrpjcv7oyilwyi5n3n8w";
        String secretKey = "ozooxkjuelrft8vwmb1pfb9i9v5ay0yp";
        YuCongMingClient yuCongMingClient = new YuCongMingClient(accessKey, secretKey);
        DevChatRequest devChatRequest = new DevChatRequest();
        devChatRequest.setModelId(1654785040361893889L);
        devChatRequest.setMessage("周杰伦最好听的10首歌");
        BaseResponse<DevChatResponse> devChatResponseBaseResponse = yuCongMingClient.doChat(devChatRequest);
        System.out.println(devChatResponseBaseResponse);
        DevChatResponse data = (DevChatResponse)devChatResponseBaseResponse.getData();
        if (data != null) {
            String content = data.getContent();
            System.out.println(content);
        }

    }
}
