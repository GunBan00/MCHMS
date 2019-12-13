package com.jeonbuk.mchms.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataDomain {

    int id;
    String title;
    String titleMy;
    String period;
    String location;
    String origin;
    String material;
    String serialNumber;
    double latitude;
    double longitude;
    int latitude2;
    int longitude2;
    String remarksEn;
    String remarksMy;
    String referenceEn;
    String referenceMy;
    int visibility;
    int cityId;
    int classificationId;
    String fileName;
    String registrant;
    String registrationDate;
}
