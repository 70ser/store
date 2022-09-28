package com.sp.store.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author zc
 * @since 2022-09-28
 */
@Data
@TableName("sys_role")

public class Role {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String description;

}
