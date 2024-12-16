package com.level.tech.dto;

import com.level.tech.enums.Title;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorDTO {

    private Long id;

    private Title title;

    private String name;

    private String phoneNo;

    private String postCode;

    private String ifscCode;

    private String address1;

    private String address2;

    private String bankBranch;

    private String accountName;

    private String accountType;

    private String bankAddress;

    private String city;

    private KeyValueDTO state;

    private String accountNo;

    private String terms;

    private KeyValueDTO bank;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;

    private String createdBy;

    private String lastModifiedBy;

}
