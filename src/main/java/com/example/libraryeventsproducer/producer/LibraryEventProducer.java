package com.example.libraryeventsproducer.producer;

import com.example.libraryeventsproducer.domain.LibraryEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
public class LibraryEventProducer {

    @Autowired
    KafkaTemplate<Integer, String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    public void sendLibraryEvent(LibraryEvent event) throws JsonProcessingException {
        Integer key = event.getLibraryEventId();
        String value = objectMapper.writeValueAsString(event);

        ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate.sendDefault(key,value);
        future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                handleFailure(key, value, ex);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                handleSuccess(key, value, result);
            }
        });

    }

    private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
        log.info("{} + {} + {}", key, value, result);
    }

    private void handleFailure(Integer key, String value, Throwable ex) {

        try {
            throw ex;
        } catch (Throwable e) {
            log.error("Error:" + e.getMessage());
        }
    }

}
