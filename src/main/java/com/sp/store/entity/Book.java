package com.sp.store.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;


@TableName("book")
@Data
@ToString
public class Book {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String isbn;
    private String name;
    private String author;
    private String press;
    private String description;
    private Integer stock;
    private BigDecimal price;
    private String imageUrl;
    @TableLogic
    private Integer isDeleted;
}
