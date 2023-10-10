//package com.mdframework.rabbitmq.config;
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitMQConfig {
//    @Bean
//    public DirectExchange directExchange(){
//        return new DirectExchange("bootDirectExchange");
//    }
//
//    @Bean
//    public Queue directQueue(){
//        return new Queue("bootDirectQueue");
//    }
//
//    @Bean
//    public Binding directBinding(Queue directQueue, DirectExchange directExchange){
//        return BindingBuilder.bind(directQueue).to(directExchange).with("bootDirectRoutingKey");
//    }
//}
