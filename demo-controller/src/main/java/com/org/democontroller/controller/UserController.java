package com.org.democontroller.controller;


import com.org.democommon.Result.R;
import com.org.demoentity.DTO.LoginDTO;
import com.org.demoentity.User;
import com.org.demoentity.VO.LoginVO;
import com.org.demoentity.VO.UserVO;
import com.org.demoservice.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping("/info")
    @PreAuthorize("hasRole('admin')")
    public R<UserVO> getUserInfo(@RequestParam(required = false) String accountNum,
                                 @RequestParam(required = false) String tel,
                                 @RequestParam(required = false) String userName) {
        UserVO userInfo = userService.getUserInfo(accountNum, tel, userName);
        return R.success(userInfo);
    }

    @PostMapping("/register")
    public R<Void> registerUser(@RequestBody User user) {
        userService.registerUser(user);
        return R.success();
    }

    @PostMapping("/login")
    public R<LoginVO> loginUser(@RequestBody LoginDTO loginDTO) {
        LoginVO loginVO = userService.loginUser(loginDTO);
        return R.success(loginVO);
    }

}
