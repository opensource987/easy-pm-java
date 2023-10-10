package com.mdframework.rabbitmq.topic;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Receive02 {
    public static void main(String[] args){
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

            channel.queueDeclare("topicQueue02", true, false, false, null);
            channel.exchangeDeclare("topicExchange", "topic", true);
            channel.queueBind("topicQueue02", "topicExchange", "aa.*");
            channel.basicConsume("topicQueue02", true, "", new DefaultConsumer(channel){
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body);
                    System.out.println("Receive01消费者aa.*---" + message);
                }
            });
        }catch (Exception e){

        }

    }
}
