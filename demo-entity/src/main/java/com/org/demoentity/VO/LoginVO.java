package com.org.demoentity.VO;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class LoginVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    String token;
    String userName;
    Integer role;
    Long userId;

}
