package cn.itcast.rabbitmq.util;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

public class ConnectionUtil {

    public static Connection getConnection() throws Exception {
        //定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置服务地址
        factory.setHost("192.168.198.130");
        //端口
        factory.setPort(5672);
        /**
         * Virtual host类似MySQL中数据库
         */
        //设置账号信息，用户名、密码、vhost
        factory.setVirtualHost("/test");
        factory.setUsername("rabbitmq_test");
        factory.setPassword("test");
        // 通过工程获取连接
        Connection connection = factory.newConnection();
        return connection;
    }

}
