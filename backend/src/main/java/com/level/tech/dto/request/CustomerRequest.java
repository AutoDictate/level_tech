package com.level.tech.dto.request;

import com.level.tech.enums.Title;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

    private Title title;

    private String name;

    private String email;

    private List<String> phoneNo;

    private String fullAddress;

    private String city;

    private String postCode;

    private Long state;

}
