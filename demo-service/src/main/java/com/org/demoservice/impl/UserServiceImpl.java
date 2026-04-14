package com.org.demoservice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.org.democommon.enumeration.ErrorCode;
import com.org.democommon.exception.UsualException;
import com.org.democommon.util.HashUtil;
import com.org.democommon.util.JwtUtil;
import com.org.demoentity.DTO.LoginDTO;
import com.org.demoentity.DTO.UserUpdateDTO;
import com.org.demoentity.User;
import com.org.demoentity.VO.LoginVO;
import com.org.demoentity.VO.UserVO;
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

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) { this.userMapper = userMapper; }

    @Override
    public void registerUser(User user) {
        if(userMapper.selectUserInfo(user.getAccountNum(), user.getTel(), user.getUserName()) != null){
            throw new UsualException(ErrorCode.USER_EXIST);
        }
        user.setPassword(HashUtil.encode(user.getPassword()));
        userMapper.insert(user);
    }

    @Override
    public LoginVO loginUser(LoginDTO loginDTO) {

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
    public void deleteCurrentUser(String password) {

    }

    @Override
    public UserVO getCurrentUser() {
        return null;
    }

    @Override
    public void updateCurrentUserInfo(LoginDTO loginDTO) {

    }

    //=============================================================================

    @Override
    public UserVO getUserInfo(String accountNum, String tel, String userName) {
        User user = userMapper.selectUserInfo(accountNum, tel, userName);

        if(user == null){
            throw new UsualException(ErrorCode.USER_NOT_EXIST);
        }

        return new UserVO(
                user.getId(),
                user.getAccountNum(),
                user.getUserName(),
                user.getStatus()
        );
    }

    @Override
    public void updateUserByAdmin(Long id, UserUpdateDTO userUpdateDTO) {

    }

    @Override
    public void deleteUserByAdmin(Long id) {

    }

}




