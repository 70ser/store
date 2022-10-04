package com.sp.store.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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

    private Integer id;

    private String name;

    private String path;

    private String icon;

    private String description;

    private Integer pid;

    private String pagePath;

    private Integer sortNum;


}
