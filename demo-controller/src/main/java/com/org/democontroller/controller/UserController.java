package com.org.democontroller.controller;


import com.org.democommon.Result.R;
import com.org.democommon.enumeration.ErrorCode;
import com.org.democommon.exception.UsualException;
import com.org.demoentity.DTO.LoginDTO;
import com.org.demoentity.DTO.UserPasswordDTO;
import com.org.demoentity.DTO.UserUpdateDTO;
import com.org.demoentity.User;
import com.org.demoentity.VO.LoginVO;
import com.org.demoentity.VO.UserVO;
import com.org.demoservice.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "用户管理", description = "提供用户相关的API接口")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping("/info")
    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "获取用户信息", description = "返回所有符合条件的用户信息")
    public R<UserVO> getUserInfo(@RequestParam(required = false) @Parameter(name = "accountNum", description = "用户账号", required = true, example = "10000001") String accountNum,
                                 @RequestParam(required = false) @Parameter(name = "tel", description = "用户手机号", required = true, example = "12345678910") String tel,
                                 @RequestParam(required = false) @Parameter(name = "userName", description = "用户名", required = true, example = "Lofi") String userName) {
        UserVO userInfo = userService.getUserInfo(accountNum, tel, userName);
        return R.success(userInfo);
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "注册新用户")
    public R<Void> registerUser(
            @Valid
            @RequestBody
            @Schema(description = "用户注册信息", example = """
                {
                  "accountNum": "10000001",
                  "password": "123456",
                  "userName": "张三",
                  "tel": "13800138000"
                }""")
            User user) {
        userService.registerUser(user);
        return R.success();
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录获取token，支持账号/手机号/用户名三种方式登录，密码一致即可")
    public R<LoginVO> loginUser(
            @Valid
            @RequestBody
            @Schema(description = "用户登录信息", example = """
                {
                  "accountNum": "10000001",
                  "password": "123456",
                  "userName": "张三",
                  "tel": "13800138000"
                }""")
            LoginDTO loginDTO) {
        LoginVO loginVO = userService.loginUser(loginDTO);
        return R.success(loginVO);
    }

    @DeleteMapping("/user")
    @PreAuthorize("hasRole('user')")
    @Operation(summary = "用户注销", description = "用户注销账号")
    public R<Void> deleteCurrentUser(
            @Valid
            @RequestBody
            @Schema(description = "用户确认密码", example = """
                {
                  "password": "123456"
                }""")
            UserPasswordDTO userPasswordDTO) {
        userService.deleteCurrentUser(userPasswordDTO);
        return R.success();
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('user')")
    @Operation(summary = "当前用户信息", description = "加载当前用户信息")
    public R<UserVO> getCurrentUser() {
        UserVO currentUser = userService.getCurrentUser();
        return R.success(currentUser);
    }

    @PutMapping("/user")
    @PreAuthorize("hasRole('user')")
    @Operation(summary = "用户更新信息", description = "用户更新个人信息")
    public R<Void> updateCurrentUser(
            @Valid
            @RequestBody
            @Schema(description = "用户更新信息", example = """
                {
                  "userName": "张三",
                  "tel": "13800138000",
                  "password": "123456"
                }""")
            UserUpdateDTO userUpdateDTO) {
        userService.updateCurrentUserInfo(userUpdateDTO);
        return R.success();
    }

    @PutMapping("/admin")
    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "更改用户", description = "管理员更改用户信息")
    public R<Void> updateUser(
            @Valid
            @RequestBody
            @Schema(description = "待更新信息", example = """
                {
                  "id": 1,
                  "userName": "张三",
                  "tel": "13800138000",
                  "role": 0
                  "status": 0
                }""")
            UserUpdateDTO userUpdateDTO) {
        if(userUpdateDTO.getId() == null){
            throw new UsualException(ErrorCode.PARAM_ERROR);
        }
        userService.updateUserByAdmin(userUpdateDTO);
        return R.success();
    }

    @DeleteMapping("/admin")
    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "删除用户", description = "管理员删除用户")
    public R<Void> deleteUser(
            @NotNull(message = "id不能为空")
            @RequestParam
            @Parameter(name = "id", description = "待删除用户id", required = true, example = "123")
            Long id) {
        userService.deleteUserByAdmin(id);
        return R.success();
    }
}
