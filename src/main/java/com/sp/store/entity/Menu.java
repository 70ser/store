package com.sp.store.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author zc
 * @since 2022-10-04
 */
@Getter
@Setter
@TableName("sys_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String path;

    private String icon;

    private String description;

    private Integer pid;

    private String pagePath;

    private Integer sortNum;

    @TableField(exist = false)
    private List<Menu> children;
}
