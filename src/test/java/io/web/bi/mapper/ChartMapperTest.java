package io.web.bi.mapper;

import cn.hutool.core.date.StopWatch;
import io.web.bi.model.entity.Chart;
import io.web.bi.model.entity.UserCount;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Aaron
 * @date 2023/10/31&18:47
 */
@SpringBootTest
public class ChartMapperTest {
    @Resource
    private ChartMapper chartMapper;

    @Test
    public void test() {
        for (int i = 0; i < 10000; i++) {
            Chart chart = new Chart();
            chart.setName("用户增长图");
            chart.setGoal("分析网站的用户增长情况");
            chart.setChartType("折线图");
            chart.setChartData("拿到暑期实习啦，面试我回答的贼烂，一堆没答出来，真的是运气问题，知道这个消息都蒙了，差点还哭出来了。从三月份做完两个项目就，四月份开始投简历一直在boss开始沟通到现在，差不多300家了，中间还出了很多事情。很诡异的是上周开始，突然好几家公司给了面试机会，我线下面试了四家，还有两家线上的面试题，其中三家是100-499，三家是20-99的公司。因为都是两三天同时面的，具体问题大差不差，我也记不太得哪家是哪家，这里整理成一份。\n" +
                    "1.&nbsp;加密算法不能适用文本加密的是( )？RSA\n" +
                    "2.&nbsp;java权限修饰符各个的使用范围（一个最常见那个打勾的表）\n" +
                    "3.&nbsp;java中overload与override的区别？（我英语不好，没认出这两个英文，然后说了一句不会。。。）\n" +
                    "4.&nbsp;写sql语句，两个表的连表查询。（我没写出来）\n" +
                    "一个领导的表，字段id和name，有两个数据。一个员工的表，字段id，name和一个外键，有三个数据，\n" +
                    "但是领导表其中一个数据的name是null，然后查询员工对应的领导，如果领导为null，显示出来为“无”。（是不是用COALESCE，我不记得了）\n" +
                    "5.&nbsp;实现多线程有几种方法？（三种，一个继承，两个实现）\n" +
                    "6.&nbsp;请写画出设计模式种适配器模式的经典UML图或者对它的了解，还有讲讲你最熟悉的设计模式。\n" +
                    "7.&nbsp;然后又是一道两表查询，查范围的，具体忘记了\n" +
                    "8.&nbsp;现有1000个苹果，分别装到10个箱子里，要求随意拿任意几个箱子都能拿完1000个，请问这10个箱子该怎么放苹果？（一脸懵，好像跟网上的经典题也不一样，我也看不懂，不知道题目对不对。。。有可能我也记错了）\n" +
                    "9.&nbsp;问了我了不了解索引（我不会，但是几乎每个面试都问了这个问题）\n" +
                    "10.&nbsp;对集合的了解有多少，hashmap底层是怎么处理相同的值？线程安全吗？不安全该用什么？\n" +
                    "11.&nbsp;你对jdk1.8的新特性了解多少？\n" +
                    "12.&nbsp;spring里bean的生命周期\n" +
                    "13.&nbsp;对事务的了解\n" +
                    "14.&nbsp;数据库的四种隔离级别\n" +
                    "15.&nbsp;springboot处理异常的几个注解，例如全局异常\n" +
                    "16.&nbsp;springboot接受参数的两个注解\n" +
                    "17.&nbsp;访问了不存在的页面，显示的是什么？答：404\n" +
                    "18.&nbsp;数据结构有哪些？答：链表，栈等等。。\n" +
                    "19.&nbsp;手写一个冒泡排序。答：我特么不记得冒泡排序是什么了。。不过面试官有提示，还是写出来了。\n" +
                    "20.&nbsp;有没有部署过项目？对docker了解多少？\n" +
                    "21.&nbsp;你对docker的容器是怎样理解？容器之间能互相通信吗？（我记得需要建立子网啥的）\n" +
                    "22.&nbsp;mysql种哪个关键字可以去重？排序是哪个关键字？\n" +
                    "23.&nbsp;redis有哪些数据类型？还可以做什么？（作为消息队列）\n" +
                    "24.&nbsp;如果redis数据库发生殆机了咋办？（从集群和主从节点入手）\n" +
                    "25.&nbsp;缓存穿透、缓存击穿、缓存雪崩有什么区别，该如何解决？（分开问的）\n" +
                    "26.&nbsp;如何保证缓存与数据库的双写一致性？\n" +
                    "\n" +
                    "后面就是问项目的，api接口问的少，黑马点评问的多\n" +
                    "黑马点评部分：\n" +
                    "1.&nbsp;店铺查询那里，你是怎么存储店铺的信息？用什么数据结构？（应该是问key怎么设置的）\n" +
                    "2.&nbsp;秒杀系统你是怎么设计的？\n" +
                    "3.&nbsp;超卖问题你是怎么解决的？（黑马是用库存来当版本号，面试官问我这样会出现什么问题，我蒙了，虽然我也记得是有问题的，但是我不记得了，那集视频弹幕有弹出来，我记不得了）\n" +
                    "4.&nbsp;还问了一个秒杀系统相关的，具体不记得了\n" +
                    "5.&nbsp;前面问redis数据类型，还可以回答这个项目里使用的一些基于基础数据类型实现的特殊数据类型，例如Geo之类。\n" +
                    "api接口项目：\n" +
                    "1.&nbsp;accessKey和secretKey是怎么实现的？怎么加密？在哪进行判断？\n" +
                    "2.&nbsp;为什么使用RPC框架？还有没有了解其他的方法？对open feign有了解吗？（之前有个球友面试被问过，我留了个心眼，所以这里我还好答出来了）\n" +
                    "3.&nbsp;为什么使用nacos？（问了一些技术选型上面的问题）\n" +
                    "4.&nbsp;你在网关做了哪些操作？\n" +
                    "然后也没怎么问了\n" +
                    "其他项目上的问题：\n" +
                    "1.&nbsp;如果现在已经有个现成的项目，我需要你面对某个功能添加一个日志，你会怎么做？\n" +
                    "2.&nbsp;具体会用到哪种spring的思想？（面试官真的很好，提示我到这个地步。。。）\n" +
                    "3.&nbsp;前端传来一个数据，后端接受后报错，会有哪些可能？列举几个可能性\n" +
                    "4.&nbsp;你项目的异常处理是怎么实现的？（有点类似前面问的全局异常那个问题）\n" +
                    "\n" +
                    "还有一个面试题目是发了一个pdf文件，我怀疑是春招的面试题，因为比较难，我这里也发给大家看看，之前我发过一次。我之前还整理了一份两个项目面试可能被问的问题，属于复习的类型把，我是没有整理完全的，希望后面能够完善。\n" +
                    "\n" +
                    "我觉得我还欠缺刷题，一些情景题还有刷法题，我看到过一些大厂会问这些问题，自己碰到就死。。。\n" +
                    "\n" +
                    "总结：\n" +
                    "这几轮面试发现，其实暑期实习的面试真的不会问什么特别难得和离谱的问题，都是一些基础题，问的最多的就是sql的索引，每一个面试官都问了，还有集合。项目就是主要问秒杀系统那一块。都并没有特别钻的题目，什么redis一些数据类型的底层数据结构（其他球友有被问到），还有消息队列的一些实现和底层原理（也是其它球友的），就这些。而且之前有个大佬发了一个面经，我随便瞅过一眼，非常好！！特别简洁，我觉得那个面经掌握，面试就解决一大半了。不过我建议大家还是要自己再次总结一遍，因为上面有的太简洁了，掠过了过程，有的又太复杂了，不够口语，所以还是建议我们二次整理，我也还在努力。兄弟们，冲！！！\n" +
                    "\n" +
                    "关于我为什么能过，我觉得还有几种可能：\n" +
                    "1.&nbsp;我当过兵，我跟面试官有说过，然后讲了自己当兵拿了一些荣誉，然后回来之后在社团和班委那边很拼，拿了很多跟专业没有相关的奖项。（我专业比赛一个奖项都没有。。。）\n" +
                    "2.&nbsp;我可以全职，迅速入岗，而且家在深圳，也是深圳户口，这周四就入岗了，我觉得这也是很大一种可能。\n" +
                    "3.&nbsp;去年这家公司拿到了很多奖项和投资，可能今年准备扩招啥的，因为站在了国家对全国税务管理的风口上，正好公司软件做的很好，我觉得也有可能。\n" +
                    "面经链接：");

            chart.setGenChart("{\n" +
                    "  \"xAxis\": {\n" +
                    "    \"type\": \"category\",\n" +
                    "    \"data\": [\"1号\", \"2号\", \"3号\", \"4号\", \"5号\", \"6号\", \"7号\"]\n" +
                    "  },\n" +
                    "  \"yAxis\": {\n" +
                    "    \"type\": \"value\"\n" +
                    "  },\n" +
                    "  \"series\": [{\n" +
                    "    \"data\": [10, 20, 30, 90, 0, 10, 20],\n" +
                    "    \"type\": \"line\"\n" +
                    "  }]\n" +
                    "}");
            chart.setGenResult("根据分析，网站的用户增长情况如下：\n" +
                    "\n" +
                    "- 1号到6号用户数不断增加，从10人到90人，然后又下降到0人，最后又回升到20人。\n" +
                    "- 4号的用户数最高，达到了90人。\n" +
                    "- 5号用户数为0，可能是因为数据缺失或者是网站出现故障导致用户数量为0。\n" +
                    "\n" +
                    "基于以上数据分析，可以发现网站的用户数在这段时间内呈现波动增长的趋势，但仍存在一些异常情况。为了更好地理解用户增长情况和异常情况，可以进一步分析其他因素对用户增长的影响，以便于优化网站运营策略。");
            chart.setStatus("succeed");
            chart.setUserId(1L);
            chartMapper.insert(chart);
            //            chartService.save(chart);
        }
    }

    @Test
    public void testTimes() {
        long start2 = System.currentTimeMillis();
//        Chart chart = chartMapper.selectById(1719329856237461525L);
        Chart chart = chartMapper.selectEntireByTableName(1719329856237461525L);
        long end2 = System.currentTimeMillis();
        System.out.println("全表查询时间为" + (end2 - start2)); //

        long start = System.currentTimeMillis();
        Chart chart1719321267825299465 = chartMapper.selectBySingleTableName(1719329856237461513L);// 1719329856237461525
        long end = System.currentTimeMillis();
        System.out.println("单表查询时间为" + (end - start)); //


//        System.out.println(chart.getChartData());
    }
}