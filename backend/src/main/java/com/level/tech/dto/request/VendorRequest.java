package com.level.tech.dto.request;

import com.level.tech.enums.Title;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorRequest {

    @NotNull(message = "Title is mandatory")
    private Title title;

    @NotNull(message = "Vendor name is mandatory")
    private String name;

    private String phoneNo;

    private String postCode;

    private String ifscCode;

    private String address1;

    private String address2;

    private String bankBranch;

    private String accountName;

    private Long accountType;

    private String bankAddress;

    @NotNull(message = "Vendor city is mandatory")
    private String city;

    private Long stateId;

    private String accountNo;

    private String terms;

    private Long bankId;

}
