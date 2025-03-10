package com.level.tech.controller;

import com.level.tech.dto.CustomerDTO;
import com.level.tech.dto.request.CustomerRequest;
import com.level.tech.dto.response.ResponseData;
import com.level.tech.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDTO> addCustomer(
            @RequestBody @Valid CustomerRequest request
    ) {
        return new ResponseEntity<>(
                customerService.addCustomer(request), HttpStatus.CREATED);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> updateCustomer(
            @PathVariable(name = "customerId") Long customerId,
            @RequestBody @Valid CustomerRequest request
    ) {
        return new ResponseEntity<>(
                customerService.updateCustomer(customerId, request), HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerById(
            @PathVariable(name = "customerId") Long customerId
    ) {
        return new ResponseEntity<>(
                customerService.getCustomer(customerId), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllCustomers(
            @RequestParam(name = "pageNo") Integer pageNo,
            @RequestParam(name = "count") Integer count,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "orderBy", required = false, defaultValue = "asc") String orderBy,
            @RequestParam(name = "search", required = false) String search
    ) {
        if (pageNo != null && count != null && pageNo > 0) {
            pageNo = pageNo - 1;
            return new ResponseEntity<>(
                    customerService.getAllCustomer(pageNo, count, sortBy, orderBy, search), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                    customerService.getCustomers(), HttpStatus.OK);
        }
    }

    @DeleteMapping
    public ResponseEntity<ResponseData> deleteCustomers(
            @RequestBody List<Long> customerIds
    ) {
        customerService.deleteCustomers(customerIds);
        return new ResponseEntity<>(
                new ResponseData("Customers deleted successfully"), HttpStatus.OK);
    }

}
