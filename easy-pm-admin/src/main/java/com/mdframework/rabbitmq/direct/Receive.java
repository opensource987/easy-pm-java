package com.mdframework.rabbitmq.direct;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Receive {
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

            channel.queueDeclare("myDirectQueue", true, false, false, null);
            channel.exchangeDeclare("directExchange", "direct", true);
            channel.queueBind("myDirectQueue", "directExchange", "directRoutingKey");
            channel.basicConsume("myDirectQueue", false, "", new DefaultConsumer(channel){

               public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                   long tag = envelope.getDeliveryTag();
                   Channel c = this.getChannel();
                   //如果是true，说明消息曾被接收过但没ack，要进行消息的防重复处理
                   boolean isRedeliver =  envelope.isRedeliver();
                   if(!isRedeliver){
                       String message = new String(body);
                       System.out.println("消费者---" + message);
                       c.basicAck(tag, true);
                   }else{
                       //防重复处理
                       c.basicAck(tag, true);
                   }

               }
            });
        }catch (Exception e){

        }

    }
}
