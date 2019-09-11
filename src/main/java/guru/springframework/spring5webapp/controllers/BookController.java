package guru.springframework.spring5webapp.controllers;

import guru.springframework.spring5webapp.model.Author;
import guru.springframework.spring5webapp.model.Book;
import guru.springframework.spring5webapp.repositories.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    private BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @RequestMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String, Object>> getBooks() {

        List<Book> books = bookRepository.findAll();

        List<Map<String, Object>> response = new ArrayList<>(books.size());

        bookRepository.findAll().forEach(book -> {
            Map<String, Object> oneBook = new HashMap<>();

            oneBook.put("id", book.getId());
            oneBook.put("title", book.getTitle());
            oneBook.put("publisher", book.getPublisher().getName());
            List<String> authors = book.getAuthors().stream()
                    .map(Author::getFirstName)
                    .collect(Collectors.toList());

            oneBook.put("authors", authors);

            response.add(oneBook);
        });

        return response;
    }
}
