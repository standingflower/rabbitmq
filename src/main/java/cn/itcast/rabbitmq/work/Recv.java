package cn.itcast.rabbitmq.work;

import cn.itcast.rabbitmq.util.ConnectionUtil;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

public class Recv {

    private final static String QUEUE_NAME = "test_queue_work";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);
        // 定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        // 监听队列，手动返回完成
        /**
         * 手动确认
         */
        channel.basicConsume(QUEUE_NAME, false, consumer);
        // 获取消息
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
            //休眠
            Thread.sleep(10);
            System.out.print("");
            // 返回确认状态
            /**
             * 手动确认消息
             */
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }
    /**
     * work模式：
     *  一般模式:MQ会将所有消息轮询平均分配给消费者，注意这里是一次性能分配完的
     *          比如MQ中有50个消息，2个消费者，消费者在获取消息时，一次全部获取完50个
     *  能者多劳模式：在消费者中执行channel.basicQos(1);方法，表示MQ在分配消息时，只会返回1个消息
     *              给消费者，消费者处理完后，再请求MQ处理消息
     */
}