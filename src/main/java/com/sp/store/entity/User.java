package com.sp.store.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("Userinfo")
@Data
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("user_name")
    private String name;
    private String password;

}
