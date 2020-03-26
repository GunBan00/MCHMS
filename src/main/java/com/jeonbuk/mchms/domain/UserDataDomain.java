package com.jeonbuk.mchms.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDataDomain {
    int id;
    String title;
    String serialNumber;
    String cityId;
    int classificationId;
    String registrationDate;
    String clResult;
    String ciResult;
    int index;
}
