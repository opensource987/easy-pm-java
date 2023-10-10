package com.mdframework.rabbitmq.fanout;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Receive03 {
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

            channel.queueDeclare("fanoutQueue", true, false, false, null);
            channel.exchangeDeclare("fanoutExchange", "fanout", true);
            channel.queueBind("fanoutQueue", "fanoutExchange", "");
            channel.basicConsume("fanoutQueue", true, "", new DefaultConsumer(channel){
               public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                   String message = new String(body);
                   System.out.println("Receive03消费者---" + message);
               }
            });
        }catch (Exception e){

        }

    }
}
