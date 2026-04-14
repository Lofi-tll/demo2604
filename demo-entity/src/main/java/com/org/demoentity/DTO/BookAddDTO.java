package com.org.demoentity.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class BookAddDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "书名不能为空")
    private String name;

    private String author;

    @NotBlank(message = "ISBN不能为空")
    private String isbn;

    @NotNull(message = "分类id不能为空")
    private Long categoryId;

    @NotNull(message = "总库存不能为空")
    private Long totalStock;

    @NotNull(message = "可借库存不能为空")
    private Long borrowableStock;

    private String publisher;

    private LocalDate publishDate;

    private String description;

    @NotNull(message = "可借状态不能为空")
    private Integer isBorrowable;

}
