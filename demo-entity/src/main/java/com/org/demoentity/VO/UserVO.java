package com.org.demoentity.VO;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String accountNum;

    private String userName;

    private Integer status;

    public UserVO() {}

    public UserVO(Long id, String accountNum, String userName, Integer status) {
        this.id = id;
        this.accountNum = accountNum;
        this.userName = userName;
        this.status = status;
    }
}
