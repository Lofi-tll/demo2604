package com.org.demoentity.DTO;

import java.io.Serial;
import java.io.Serializable;

public class UserUpdateDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String accountNum;

    private String tel;

    private String userName;

    private String password;

}
