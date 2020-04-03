package com.jeonbuk.mchms.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
    private String id;
    private String name;
    private String email;
    private String nickname;
    private String grade;
    private int index;
}