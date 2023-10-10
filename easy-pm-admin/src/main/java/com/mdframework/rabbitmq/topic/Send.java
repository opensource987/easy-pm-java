package com.mdframework.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Send {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("root");
        factory.setPassword("root");
        factory.setVirtualHost("test");
        Connection connection = null;
        Channel channel = null;
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();


//            String queueName = channel.queueDeclare().getQueue();
            channel.exchangeDeclare("topicExchange", "topic", true);
//            channel.queueBind(queueName, "fanoutExchange", "");
            String message = "topic的测试消息！";
            channel.basicPublish("topicExchange", "aa.bb.cc", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("消息发送成功");
        }catch (Exception e){

        }finally {
            if(channel != null){
                channel.close();
            }
            if(connection != null){
                connection.close();
            }

        }

    }
}
