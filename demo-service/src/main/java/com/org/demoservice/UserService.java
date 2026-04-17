package com.org.demoservice;

import com.org.demoentity.DTO.LoginDTO;
import com.org.demoentity.DTO.UserPasswordDTO;
import com.org.demoentity.DTO.UserUpdateDTO;
import com.org.demoentity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.org.demoentity.VO.LoginVO;
import com.org.demoentity.VO.UserVO;

/**
* @author Lofi
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2026-04-11 15:18:19
*/
public interface UserService extends IService<User> {

    //注册登录接口

    public void registerUser(User user);

    public LoginVO loginUser(LoginDTO loginDTO);

    //用户权限接口

    public void deleteCurrentUser(UserPasswordDTO userPasswordDTO);

    public UserVO getCurrentUser();

    public void updateCurrentUserInfo(UserUpdateDTO userUpdateDTO);

    //管理员权限接口

    public UserVO getUserInfo(String accountNum, String tel, String userName);

    public void updateUserByAdmin(UserUpdateDTO userUpdateDTO);

    public void deleteUserByAdmin(Long id);


}
