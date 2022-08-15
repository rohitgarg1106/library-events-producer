package com.example.libraryeventsproducer.request;

import com.example.libraryeventsproducer.domain.Book;
import com.example.libraryeventsproducer.domain.LibraryEventType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LibraryEventRequest {

    @JsonProperty("event_id")
    private Integer libraryEventId;

    @JsonProperty("book")
    private Book book;
}
