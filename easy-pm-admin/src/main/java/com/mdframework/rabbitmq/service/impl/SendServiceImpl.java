//package com.mdframework.rabbitmq.service.impl;
//
//import com.mdframework.rabbitmq.service.SendService;
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.amqp.rabbit.annotation.Exchange;
//import org.springframework.amqp.rabbit.annotation.Queue;
//import org.springframework.amqp.rabbit.annotation.QueueBinding;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//
//@Service("sendService")
//public class SendServiceImpl implements SendService {
//    @Resource
//    private AmqpTemplate amqpTemplate;
//
//    @Override
//    public void sendMessage(String message) {
//        amqpTemplate.convertAndSend("bootDirectExchange", "bootDirectRoutingKey", message);
//    }
//
//    @Override
//    public void sendFanoutMessage(String message) {
//        amqpTemplate.convertAndSend("fanoutExchange", "", message);
//    }
//
//    @Override
//    public void sendTopicMessage(String message) {
//        amqpTemplate.convertAndSend("topicExchange", "aa.bb.cc", message);
//    }
//
//    @RabbitListener(queues = {"bootDirectQueue"})
//    public void directReceive(String message){
//        System.out.println("监听器接收的消息---" + message);
//    }
//
//    @RabbitListener(bindings = {@QueueBinding(value = @Queue(), exchange=@Exchange(name="fanoutExchange", type="fanout"))})
//    public void fanoutReceive01(String message){
//        System.out.println("fanout01监听器接收的消息--" + message);
//    }
//
//    @RabbitListener(bindings = {@QueueBinding(value = @Queue(), exchange=@Exchange(name="fanoutExchange", type="fanout"))})
//    public void fanoutReceive02(String message){
//        System.out.println("fanout02监听器接收的消息--" + message);
//    }
//
//    @RabbitListener(bindings = {@QueueBinding(value = @Queue("topic01"), key = {"aa"}, exchange=@Exchange(name="topicExchange", type="topic"))})
//    public void topicReceive01(String message){
//        System.out.println("topic01监听器接收的消息--aa--" + message);
//    }
//
//    @RabbitListener(bindings = {@QueueBinding(value = @Queue("topic02"), key = {"aa.*"}, exchange=@Exchange(name="topicExchange", type="topic"))})
//    public void topicReceive02(String message){
//        System.out.println("topic02监听器接收的消息--aa.*--" + message);
//    }
//
//    @RabbitListener(bindings = {@QueueBinding(value = @Queue("topic03"), key = {"aa.#"}, exchange=@Exchange(name="topicExchange", type="topic"))})
//    public void topicReceive03(String message){
//        System.out.println("topic03监听器接收的消息--aa.#--" + message);
//    }
//}
