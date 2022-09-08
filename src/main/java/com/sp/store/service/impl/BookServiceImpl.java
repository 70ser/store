package com.sp.store.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sp.store.entity.Book;
import com.sp.store.mapper.BookMapper;
import com.sp.store.service.IBookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements IBookService {
    @Resource
    BookMapper bookMapper;
    @Override
    public boolean saveAll(List<Book> books){
        try{
            for (Book book: books
            ) {
                save(book);
            }
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

}
