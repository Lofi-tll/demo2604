package com.org.demoservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.org.democommon.context.UserContext;
import com.org.democommon.enumeration.ErrorCode;
import com.org.democommon.exception.UsualException;
import com.org.democommon.security.LoginUser;
import com.org.democommon.util.HashUtil;
import com.org.democommon.util.JwtUtil;
import com.org.demoentity.DTO.LoginDTO;
import com.org.demoentity.DTO.UserPasswordDTO;
import com.org.demoentity.DTO.UserUpdateDTO;
import com.org.demoentity.User;
import com.org.demoentity.VO.LoginVO;
import com.org.demoentity.VO.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.org.demomapper.UserMapper;
import com.org.demoservice.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;


/**
* @author Lofi
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2026-04-11 15:18:19
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) { this.userMapper = userMapper; }

    @Override
    @Transactional
    public void registerUser(User user) {
        if (user == null) {
            throw new UsualException(ErrorCode.PARAM_ERROR);
        }

        List<User> Exist = userMapper.selectUserList(user.getAccountNum(), user.getTel(), user.getUserName());

        if(!Exist.isEmpty()){
            throw new UsualException(ErrorCode.USER_EXIST);
        }
        user.setPassword(HashUtil.encode(user.getPassword()));
        user.setRole(0);
        userMapper.insert(user);
    }

    @Override
    @Transactional(readOnly = true)
    public LoginVO loginUser(LoginDTO loginDTO) {

        if(loginDTO == null){
            throw new UsualException(ErrorCode.PARAM_ERROR);
        }

        // 判空必须先判断是否为null，再判断是否为空字符串
        if (StringUtils.isEmpty(loginDTO.getAccountNum())
                && StringUtils.isEmpty(loginDTO.getTel())
                && StringUtils.isEmpty(loginDTO.getUserName())) {
            throw new UsualException(ErrorCode.PARAM_ERROR);
        }

        User userInDB = userMapper.selectUserInfo(loginDTO.getAccountNum(),loginDTO.getTel(),loginDTO.getUserName());

        //登录校验
        if(userInDB == null){
            throw new UsualException(ErrorCode.USER_NOT_EXIST);
        }

        if(userInDB.getStatus() == 0){
            throw new UsualException(ErrorCode.USER_STATUS_ERROR);
        }

        if(!HashUtil.matches(loginDTO.getPassword(), userInDB.getPassword())){
            throw new UsualException(ErrorCode.PASSWORD_ERROR);
        }

        //token封装操作
        String token = JwtUtil.createToken(userInDB.getUserName(), userInDB.getRole(), userInDB.getId());
        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        loginVO.setUserName(userInDB.getUserName());
        loginVO.setRole(userInDB.getRole());
        loginVO.setUserId(userInDB.getId());
        return loginVO;

    }

    //=============================================================================

    @Override
    @Transactional
    public void deleteCurrentUser(UserPasswordDTO userPasswordDTO) {
        LoginUser user = UserContext.getUser();
        User userInDB = userMapper.selectById(user.getId());
        if(userInDB == null){
            throw new UsualException(ErrorCode.USER_NOT_EXIST);
        }
        if(!HashUtil.matches(userPasswordDTO.getPassword(), userInDB.getPassword())){
            throw new UsualException(ErrorCode.PASSWORD_ERROR);
        }
        userMapper.deleteById(user.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public UserVO getCurrentUser() {
        LoginUser user = UserContext.getUser();
        User userInDB = userMapper.selectById(user.getId());
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userInDB,userVO);

        return userVO;
    }

    @Override
    @Transactional
    public void updateCurrentUserInfo(UserUpdateDTO userUpdateDTO) {
        User user = new User();
        user.setId(UserContext.getUser().getId());
        BeanUtils.copyProperties(userUpdateDTO,user);
        userMapper.updateById(user);
    }

    //=============================================================================

    @Override
    @Transactional(readOnly = true)
    public UserVO getUserInfo(String accountNum, String tel, String userName) {
        if (accountNum == null && tel == null && userName == null) {
            throw new UsualException(ErrorCode.PARAM_ERROR.getCode(), ErrorCode.PARAM_ERROR.getMessage());
        }

        User user = userMapper.selectUserInfo(accountNum, tel, userName);

        if(user == null){
            throw new UsualException(ErrorCode.USER_NOT_EXIST);
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);
        return userVO;
    }

    @Override
    @Transactional
    public void updateUserByAdmin(UserUpdateDTO userUpdateDTO) {
        User user = new User();
        BeanUtils.copyProperties(userUpdateDTO,user);
        userMapper.updateById(user);
    }

    @Override
    @Transactional
    public void deleteUserByAdmin(Long id) {
        userMapper.deleteById(id);
    }

}