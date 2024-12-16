package com.level.tech.service;

import com.level.tech.dto.CustomerDTO;
import com.level.tech.dto.request.CustomerRequest;
import com.level.tech.dto.response.PagedResponseDTO;

import java.util.List;

public interface CustomerService {

    CustomerDTO addCustomer(CustomerRequest request);

    CustomerDTO updateCustomer(Long id, CustomerRequest request);

    CustomerDTO getCustomer(Long id);

    List<CustomerDTO> getCustomers();

    PagedResponseDTO getAllCustomer(Integer pageNo, Integer count, String sortBy, String orderBy, String search);

    void deleteCustomer(Long id);

    void deleteCustomers(List<Long> ids);

}
