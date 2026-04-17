package com.org.demoentity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 图书表
 * @TableName book
 */
@TableName(value ="book")
@Data
public class Book implements Serializable {

    /**
     * 固定版本号
     */
    @TableField(exist = false)
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 图书表主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 书名
     */
    private String name;

    /**
     * 作者
     */
    private String author;

    /**
     * 国际标准书号
     */
    private String isbn;

    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 总库存
     */
    private Long totalStock;

    /**
     * 可借库存
     */
    private Long borrowableStock;

    /**
     * 出版社
     */
    private String publisher;

    /**
     * 出版日期
     */
    private LocalDate publishDate;

    /**
     * 图书描述
     */
    private String description;

    /**
     * 可借出1 不可借出0
     */
    private Integer isBorrowable;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    /**
     * 已删除1 未删除0
     */
    @TableLogic                //表明软删除字段对应的属性
    private Integer isDelete;

    @TableField(exist = false) // 标注：数据库不存在这个字段
    private String categoryName;

    @Override
    public boolean equals(Object that) { //重写equals方法，比较两个对象是否相等
        if (this == that) {              //同时也要重写hashCode方法,保证equal功能
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Book other = (Book) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getAuthor() == null ? other.getAuthor() == null : this.getAuthor().equals(other.getAuthor()))
            && (this.getIsbn() == null ? other.getIsbn() == null : this.getIsbn().equals(other.getIsbn()))
            && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
            && (this.getTotalStock() == null ? other.getTotalStock() == null : this.getTotalStock().equals(other.getTotalStock()))
            && (this.getBorrowableStock() == null ? other.getBorrowableStock() == null : this.getBorrowableStock().equals(other.getBorrowableStock()))
            && (this.getPublisher() == null ? other.getPublisher() == null : this.getPublisher().equals(other.getPublisher()))
            && (this.getPublishDate() == null ? other.getPublishDate() == null : this.getPublishDate().equals(other.getPublishDate()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getIsBorrowable() == null ? other.getIsBorrowable() == null : this.getIsBorrowable().equals(other.getIsBorrowable()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getAuthor() == null) ? 0 : getAuthor().hashCode());
        result = prime * result + ((getIsbn() == null) ? 0 : getIsbn().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getTotalStock() == null) ? 0 : getTotalStock().hashCode());
        result = prime * result + ((getBorrowableStock() == null) ? 0 : getBorrowableStock().hashCode());
        result = prime * result + ((getPublisher() == null) ? 0 : getPublisher().hashCode());
        result = prime * result + ((getPublishDate() == null) ? 0 : getPublishDate().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getIsBorrowable() == null) ? 0 : getIsBorrowable().hashCode());
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
        sb.append(", name=").append(name);
        sb.append(", author=").append(author);
        sb.append(", isbn=").append(isbn);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", totalStock=").append(totalStock);
        sb.append(", borrowableStock=").append(borrowableStock);
        sb.append(", publisher=").append(publisher);
        sb.append(", publishDate=").append(publishDate);
        sb.append(", description=").append(description);
        sb.append(", isBorrowable=").append(isBorrowable);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", categoryName=").append(categoryName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}