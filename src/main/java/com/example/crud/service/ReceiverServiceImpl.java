package com.example.crud.service;

import com.example.crud.model.Employee;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ReceiverServiceImpl implements ReceiverService, RabbitListenerConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(ReceiverServiceImpl.class);


    @Override
    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receiveMessage(String message) {
        if(isNumeric(message)){
            logger.info("Number of employees " + message);
        }else {
            logger.info("Employee Details Received is.. " + message);
        }
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {

    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
