package com.sp.store.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sp.store.common.Result;
import com.sp.store.entity.Book;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.sp.store.service.IRoleService;
import com.sp.store.entity.Role;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zc
 * @since 2022-09-28
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private IRoleService roleService;

    // 新增或者更新
    @PostMapping
    public Result<?> save(@RequestBody Role role) {
        roleService.saveOrUpdate(role);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        roleService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result<?> deleteBatch(@RequestBody List<Integer> ids) {
        roleService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result<?> findAll() {
        return Result.success(roleService.list());
    }

    @GetMapping("/{id}")
    public Result<?> findOne(@PathVariable Integer id) {
        return Result.success(roleService.getById(id));
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNumber,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if (search != null && search.length() > 0) {
            queryWrapper.like("name", search).or().like("id",search).or().like("description",search);
        }
        queryWrapper.orderByDesc("id");
        return Result.success(roleService.page(new Page<>(pageNumber, pageSize), queryWrapper));
    }

}

