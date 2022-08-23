package com.sp.store.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("Image")
@Data
public class File {
    @TableId(type = IdType.ASSIGN_UUID)
    private String uuid;
    private byte[] img;
    private String name;
}
