package com.org.demoentity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 借阅记录表
 * @TableName borrow_record
 */
@TableName(value ="borrow_record")
@Data
public class BorrowRecord implements Serializable {

    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 借阅记录表主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 借阅人id
     */
    private Long userId;

    /**
     * 借出书籍id
     */
    private Long bookId;

    /**
     * 借出时间
     */
    private LocalDateTime borrowTime;

    /**
     * 预期归还时间
     */
    private LocalDateTime expectedReturnTime;

    /**
     * 实际归还时间
     */
    private LocalDateTime actualReturnTime;

    /**
     * 借阅状态 借阅中0 已归还1
     */
    private Integer borrowStatus;

    /**
     * 归还状态 未逾期0 已逾期1
     */
    @TableField(exist = false)
    private Integer isOverdue;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 已删除1 未删除0
     */
    @TableLogic
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
        BorrowRecord other = (BorrowRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getBookId() == null ? other.getBookId() == null : this.getBookId().equals(other.getBookId()))
            && (this.getBorrowTime() == null ? other.getBorrowTime() == null : this.getBorrowTime().equals(other.getBorrowTime()))
            && (this.getExpectedReturnTime() == null ? other.getExpectedReturnTime() == null : this.getExpectedReturnTime().equals(other.getExpectedReturnTime()))
            && (this.getActualReturnTime() == null ? other.getActualReturnTime() == null : this.getActualReturnTime().equals(other.getActualReturnTime()))
            && (this.getBorrowStatus() == null ? other.getBorrowStatus() == null : this.getBorrowStatus().equals(other.getBorrowStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getBookId() == null) ? 0 : getBookId().hashCode());
        result = prime * result + ((getBorrowTime() == null) ? 0 : getBorrowTime().hashCode());
        result = prime * result + ((getExpectedReturnTime() == null) ? 0 : getExpectedReturnTime().hashCode());
        result = prime * result + ((getActualReturnTime() == null) ? 0 : getActualReturnTime().hashCode());
        result = prime * result + ((getBorrowStatus() == null) ? 0 : getBorrowStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
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
        sb.append(", userId=").append(userId);
        sb.append(", bookId=").append(bookId);
        sb.append(", borrowTime=").append(borrowTime);
        sb.append(", expectedReturnTime=").append(expectedReturnTime);
        sb.append(", actualReturnTime=").append(actualReturnTime);
        sb.append(", borrowStatus=").append(borrowStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}