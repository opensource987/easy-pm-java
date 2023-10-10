package com.mdframework.rabbitmq.direct;

import com.rabbitmq.client.*;

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

            channel.queueDeclare("myDirectQueue", true, false, false, null);
            channel.exchangeDeclare("directExchange", "direct", true);
            channel.queueBind("myDirectQueue", "directExchange", "directRoutingKey");
            String message = "direct的测试消息！";
            channel.confirmSelect();
            channel.addConfirmListener(new ConfirmListener() {
                @Override
                public void handleAck(long l, boolean b) throws IOException {
                    System.out.println("消息被确认了");
                }

                @Override
                public void handleNack(long l, boolean b) throws IOException {
                    System.out.println("消息没被确认了");
                }
            });
            for(int i=0; i<1000; i++){
                channel.basicPublish("directExchange", "directRoutingKey", null, message.getBytes(StandardCharsets.UTF_8));
            }

//            channel.waitForConfirms();
//            channel.waitForConfirmsOrDie();
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
