package com.org.demoentity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户表
 * @TableName user
 */
@TableName(value ="user")
@Data
@Schema(description = "用户信息")
public class User implements Serializable {

    /**
     * 固定版本号
     */
    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户表主键
     */
    @TableId(type = IdType.AUTO)
    @Schema(description = "用户主键", example = "1")
    private Long id;

    /**
     * 账号id
     */
    @NotBlank(message = "账号不能为空")
    @Schema(description = "用户账号", example = "10000001")
    private String accountNum;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名", example = "Lofi")
    private String userName;

    /**
     * 账号密码
     */
    @NotBlank(message = "密码不能为空")
    @Schema(description = "账号密码", example = "password123")
    private String password;

    /**
     * 角色权限 用户0 管理员1
     */
    @Schema(description = "角色权限 用户0 管理员1", example = "1")
    private Integer role;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Schema(description = "手机号", example = "12345678910")
    private String tel;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间", hidden = true)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "更新时间", hidden = true)
    private LocalDateTime updateTime;

    /**
     * 启用1 停用0
     */
    @Schema(description = "启用1 停用0", hidden = true)
    private Integer status;

    /**
     * 已删除1 未删除0
     */
    @TableLogic
    @Schema(description = "已删除1 未删除0", hidden = true)
    private Integer isDelete;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        User other = (User) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAccountNum() == null ? other.getAccountNum() == null : this.getAccountNum().equals(other.getAccountNum()))
            && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getRole() == null ? other.getRole() == null : this.getRole().equals(other.getRole()))
            && (this.getTel() == null ? other.getTel() == null : this.getTel().equals(other.getTel()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAccountNum() == null) ? 0 : getAccountNum().hashCode());
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getRole() == null) ? 0 : getRole().hashCode());
        result = prime * result + ((getTel() == null) ? 0 : getTel().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", accountNum=").append(accountNum);
        sb.append(", userName=").append(userName);
        sb.append(", password=").append(password);
        sb.append(", role=").append(role);
        sb.append(", tel=").append(tel);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", status=").append(status);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}