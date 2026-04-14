package com.org.democontroller.controller;


import com.org.democommon.Result.R;
import com.org.demoentity.DTO.LoginDTO;
import com.org.demoentity.DTO.UserPasswordDTO;
import com.org.demoentity.DTO.UserUpdateDTO;
import com.org.demoentity.User;
import com.org.demoentity.VO.LoginVO;
import com.org.demoentity.VO.UserVO;
import com.org.demoservice.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
    public R<Void> registerUser(@Valid @RequestBody User user) {
        userService.registerUser(user);
        return R.success();
    }

    @PostMapping("/login")
    public R<LoginVO> loginUser(@Valid @RequestBody LoginDTO loginDTO) {
        LoginVO loginVO = userService.loginUser(loginDTO);
        return R.success(loginVO);
    }

    @DeleteMapping("/user")
    @PreAuthorize("hasRole('user')")
    public R<Void> deleteCurrentUser(@Valid UserPasswordDTO userPasswordDTO) {
        userService.deleteCurrentUser(userPasswordDTO);
        return R.success();
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('user')")
    public R<UserVO> getCurrentUser() {
        UserVO currentUser = userService.getCurrentUser();
        return R.success(currentUser);
    }

    @PutMapping("/user")
    @PreAuthorize("hasRole('user')")
    public R<Void> updateCurrentUser(@Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        userService.updateCurrentUserInfo(userUpdateDTO);
        return R.success();
    }

    @PutMapping("/admin")
    @PreAuthorize("hasRole('admin')")
    public R<Void> updateUser(@NotNull(message = "id不能为空") @RequestParam Long id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        userService.updateUserByAdmin(id, userUpdateDTO);
        return R.success();
    }

    @DeleteMapping("/admin")
    @PreAuthorize("hasRole('admin')")
    public R<Void> deleteUser(@NotNull(message = "id不能为空") @RequestParam Long id) {
        userService.deleteUserByAdmin(id);
        return R.success();
    }
}
