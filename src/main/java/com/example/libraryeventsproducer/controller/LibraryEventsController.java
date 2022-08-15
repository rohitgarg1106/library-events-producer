package com.example.libraryeventsproducer.controller;

import com.example.libraryeventsproducer.domain.LibraryEvent;
import com.example.libraryeventsproducer.domain.LibraryEventType;
import com.example.libraryeventsproducer.producer.LibraryEventProducer;
import com.example.libraryeventsproducer.request.LibraryEventRequest;
import com.example.libraryeventsproducer.response.LibraryEventResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LibraryEventsController {

    @Autowired
    private LibraryEventProducer libraryEventProducer;

    @PostMapping("/v1/libraryevent")
    public ResponseEntity<LibraryEventResponse> postLibraryEvent(@RequestBody LibraryEventRequest request) throws JsonProcessingException {
        //invoke kafka producer
        LibraryEvent event = requestToEntity(request);
        event.setLibraryEventType(LibraryEventType.CREATE);
        libraryEventProducer.sendLibraryEvent2(event);
        LibraryEventResponse response = entityToResponse(event);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/v1/libraryevent")
    public ResponseEntity<LibraryEventResponse> updateLibraryEvent(@RequestBody LibraryEventRequest request) throws JsonProcessingException {
        //invoke kafka producer
        LibraryEvent event = requestToEntity(request);
        event.setLibraryEventType(LibraryEventType.UPDATE);
        libraryEventProducer.sendLibraryEvent(event);
        LibraryEventResponse response = entityToResponse(event);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private LibraryEvent requestToEntity(LibraryEventRequest request) {
        LibraryEvent event = new LibraryEvent();
        event.setLibraryEventId(request.getLibraryEventId());
        event.setBook(request.getBook());
        return event;
    }

    private LibraryEventResponse entityToResponse(LibraryEvent event) {
        LibraryEventResponse response = new LibraryEventResponse();
        response.setBook(event.getBook());
        response.setLibraryEventId(event.getLibraryEventId());

        return response;
    }

}
