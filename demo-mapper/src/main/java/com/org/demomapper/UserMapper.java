package com.org.demomapper;

import com.org.demoentity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.awt.font.TextLayout;

/**
* @author MR
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2026-04-11 15:18:19
* @Entity com.org.demoentity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("""
        <script>
            SELECT id, account_num, user_name, password, status, role, tel FROM user
            WHERE 
            is_delete = 0
            <if test="accountNum != null">
                AND account_num = #{accountNum}
            </if>
            <if test="tel != null">
                AND tel = #{tel}
            </if>
            <if test="userName != null">
                AND user_name = #{userName}
            </if>
        </script>
            """)
    public User selectUserInfo(
            @Param("accountNum") String accountNum,
            @Param("tel") String tel,
            @Param("userName") String userName
    );

}
