package com.sp.store.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@TableName("user_info")
@Data
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @TableField("user_name")
    private String userName;
    private String password;
    private String nickName;
    private String avatarUrl;
    private String email;
    private String phoneNumber;
    private String address;
    @TableLogic
    private Integer isDeleted;
}
