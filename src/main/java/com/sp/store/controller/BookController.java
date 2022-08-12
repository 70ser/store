package com.sp.store.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sp.store.common.Result;
import com.sp.store.entity.Book;
import com.sp.store.mapper.BookMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Wrapper;

@RestController
@RequestMapping("/book")
@CrossOrigin
public class BookController {

    @Resource
    private BookMapper bookMapper;
    @PostMapping
    public Result<?> save(@RequestBody Book book) {
        bookMapper.insert(book);
        return Result.success();
    }
    @PutMapping
    public Result<?> update(@RequestBody Book book) {
        bookMapper.updateById(book);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        bookMapper.deleteById(id);
        return Result.success();
    }
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNumber, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Book> wrapper = Wrappers.lambdaQuery();
        if (search != null && search.length() > 0) {
            wrapper.like(Book::getName, search);
        }
        wrapper.orderByAsc(Book::getId);
        Page<Book> bookPage=bookMapper.selectPage(new Page<>(pageNumber, pageSize), wrapper);
        return Result.success(bookPage);
    }
}
