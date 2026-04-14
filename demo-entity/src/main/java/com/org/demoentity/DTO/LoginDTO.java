package com.org.demoentity.DTO;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class LoginDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String accountNum;

    private String tel;

    private String userName;

    private String password;

}
