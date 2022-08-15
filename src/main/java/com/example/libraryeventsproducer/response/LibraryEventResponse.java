package com.example.libraryeventsproducer.response;

import com.example.libraryeventsproducer.domain.Book;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LibraryEventResponse {

    @JsonProperty("library_event_id")
    private Integer LibraryEventId;

    @JsonProperty("book")
    private Book book;
}
