package com.org.demoentity.DTO;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserPasswordDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String password;

}
