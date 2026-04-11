package com.org.demoservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.org.demoentity.User;
import org.springframework.stereotype.Service;
import com.org.demomapper.UserMapper;
import com.org.demoservice.UserService;

/**
* @author Lofi
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2026-04-11 15:18:19
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

}




