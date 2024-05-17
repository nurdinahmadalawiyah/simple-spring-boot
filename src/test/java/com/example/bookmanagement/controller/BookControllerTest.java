package com.example.bookmanagement.controller;

import com.example.bookmanagement.model.Book;
import com.example.bookmanagement.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    public void getAllBooks_ShouldReturnBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(
                Arrays.asList(
                        new Book("1", "Spring in Action", "Craig Walls"),
                        new Book("2", "Effective Java", "Joshua Bloch")
                )
        );

        mockMvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":\"1\",\"title\":\"Spring in Action\",\"author\":\"Craig Walls\"}," +
                        "{\"id\":\"2\",\"title\":\"Effective Java\",\"author\":\"Joshua Bloch\"}]"));
    }

    @Test
    public void getBookById_ShouldReturnBook() throws Exception {
        when(bookService.getBookById("1")).thenReturn(new Book("1", "Spring in Action", "Craig Walls"));

        mockMvc.perform(get("/books/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"1\",\"title\":\"Spring in Action\",\"author\":\"Craig Walls\"}"));
    }
}