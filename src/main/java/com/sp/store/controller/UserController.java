package com.sp.store.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sp.store.common.Result;
import com.sp.store.entity.User;
import com.sp.store.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Wrapper;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Resource
    private UserMapper userMapper;
    @PostMapping("/register")
    public Result<?> register (@RequestBody User user) {
        QueryWrapper<User> queryWrapper=Wrappers.query();
        queryWrapper.eq("user_name",user.getUserName());
        User only=userMapper.selectOne(queryWrapper);
        if(only==null){
            userMapper.insert(user);
            return Result.success();
        }
        else{
            return Result.error("-1","用户名已被使用");
        }

    }
    @PostMapping("/login")
    public Result<?> login (@RequestBody User user) {
        QueryWrapper<User> queryWrapper=Wrappers.query();
        queryWrapper.eq("user_name",user.getUserName());
        User only=userMapper.selectOne(queryWrapper);
        if(only==null||(!only.getPassword().equals(user.getPassword()))){
            return Result.error("-1","用户名或密码错误");
        }
        else{
            return Result.success(only);
        }
    }
    @PutMapping
    public Result<?> update(@RequestBody User user) {
        userMapper.updateById(user);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        userMapper.deleteById(id);
        return Result.success();
    }
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNumber, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        if (search != null && search.length() > 0) {
            wrapper.like(User::getNickName, search);
        }
        wrapper.orderByAsc(User::getId);
        Page<User> userPage=userMapper.selectPage(new Page<>(pageNumber, pageSize), wrapper);
        return Result.success(userPage);
    }
}

