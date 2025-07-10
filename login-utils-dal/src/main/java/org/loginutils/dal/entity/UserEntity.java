package org.loginutils.dal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String username;

    private String password;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    public boolean isPresent() {
        return false;
    }
}
