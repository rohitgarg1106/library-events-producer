package com.example.libraryeventsproducer.request;

import com.example.libraryeventsproducer.domain.Book;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LibraryEventRequest {

    @JsonProperty("book")
    private Book book;
}
