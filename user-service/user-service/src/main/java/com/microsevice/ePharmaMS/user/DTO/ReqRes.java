package com.microsevice.ePharmaMS.user.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.microsevice.ePharmaMS.user.modal.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqRes {

    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String name;
    private String address;
    private String gender;
    private String role;
    private String email;
    private String password;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private User user;
    private List<User> userList;
}
