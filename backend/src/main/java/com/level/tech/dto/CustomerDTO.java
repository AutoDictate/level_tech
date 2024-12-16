package com.level.tech.dto;

import com.level.tech.entity.PhoneNumber;
import com.level.tech.entity.State;
import com.level.tech.enums.Title;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private Long id;

    private Title title;

    private String name;

    private String email;

    private List<PhoneNumber> phoneNo;

    private String fullAddress;

    private String city;

    private String postCode;

    private State state;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;

    private String createdBy;

    private String lastModifiedBy;

}
