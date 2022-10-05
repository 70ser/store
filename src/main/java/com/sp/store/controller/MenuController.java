package com.sp.store.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sp.store.entity.Role;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sp.store.common.Result;

import com.sp.store.service.IMenuService;
import com.sp.store.entity.Menu;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zc
 * @since 2022-10-04
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private IMenuService menuService;

    // 新增或者更新
    @PostMapping
    public Result<?> save(@RequestBody Menu menu) {
        menuService.saveOrUpdate(menu);
        System.out.println("saveorupdate success");
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        menuService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result<?> deleteBatch(@RequestBody List<Integer> ids) {
        menuService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result<?> findAll(@RequestParam(defaultValue = "") String search) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        if (search != null && search.length() > 0) {
//            queryWrapper.like("name", search).or().like("id",search).or().like("description",search);
            queryWrapper.like("name", search).or().like("id",search).or().like("description",search);
        }
        queryWrapper.orderByAsc("id");
        List<Menu> list=menuService.list(queryWrapper);
        //最多只有两级
        List<Menu> parentNode = list.stream().filter(menu -> menu.getPid() == null).collect(Collectors.toList());
        for (Menu menu : parentNode) {
            menu.setChildren(list.stream().filter(m -> menu.getId().equals(m.getPid())).collect(Collectors.toList()));

        }
        System.out.println("findAll success");
        return Result.success(parentNode);
    }

    @GetMapping("/{id}")
    public Result<?> findOne(@PathVariable Integer id) {
        return Result.success(menuService.getById(id));
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNumber,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        if (search != null && search.length() > 0) {
//            queryWrapper.like("name", search).or().like("id",search).or().like("description",search);
            queryWrapper.like("name", search).or().like("id",search).or().like("description",search);
        }
        queryWrapper.orderByDesc("id");
        return Result.success(menuService.page(new Page<>(pageNumber, pageSize), queryWrapper));
    }

}

