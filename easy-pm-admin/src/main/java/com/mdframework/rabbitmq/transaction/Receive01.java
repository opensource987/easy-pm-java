package com.mdframework.rabbitmq.transaction;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Receive01 {
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

            channel.queueDeclare("topicQueue01", true, false, false, null);
            channel.exchangeDeclare("topicTransactionExchange", "topic", true);
            channel.queueBind("topicQueue01", "topicTransactionExchange", "aa");
            channel.basicConsume("topicQueue01", true, "", new DefaultConsumer(channel){
               public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                   String message = new String(body);
                   System.out.println("Receive01消费者aa---" + message);
               }
            });
        }catch (Exception e){

        }

    }
}
