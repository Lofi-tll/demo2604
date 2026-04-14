package com.org.demoentity.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class BookUpdateDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull(message = "图书id不能为空")
    private Long id;

    private String name;

    private String author;

    private String isbn;

    private Long categoryId;

    private Long totalStock;

    private Long borrowableStock;

    private String publisher;

    private LocalDate publishDate;

    private String description;

    private Integer isBorrowable;

}
