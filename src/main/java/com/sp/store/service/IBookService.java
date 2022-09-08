package com.sp.store.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sp.store.entity.Book;

import java.util.List;

public interface IBookService extends IService<Book> {
    boolean saveAll(List<Book> books);
}
