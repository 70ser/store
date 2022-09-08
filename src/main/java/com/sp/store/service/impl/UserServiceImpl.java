package com.sp.store.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sp.store.mapper.UserMapper;
import com.sp.store.service.IUserService;
import com.sp.store.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements IUserService {
}
