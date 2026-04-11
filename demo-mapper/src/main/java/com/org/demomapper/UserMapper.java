package com.org.demomapper;

import com.org.demoentity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author MR
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2026-04-11 15:18:19
* @Entity com.org.demoentity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




