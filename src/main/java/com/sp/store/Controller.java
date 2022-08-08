package com.sp.store;

import com.google.gson.Gson;
import com.sp.store.entity.Book;
import com.sp.store.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {
    @Autowired
    private BookMapper bookMapper;
    private final Gson gson = new Gson();
    @GetMapping("/")
    public String showbooks() {

        List<Book> books = bookMapper.selectList(null);
        return gson.toJson(books);
    }
}
