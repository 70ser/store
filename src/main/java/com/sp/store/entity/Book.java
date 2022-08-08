package com.sp.store.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@TableName("Book")
@Data
public class Book {
    @TableId
    private String ISBN;
    private String name;
    private String author;
    private String press;
    private String classify;
    private String description;
    private int stock;
    private int price;//以分为单位
    private String imageUrl;
}
