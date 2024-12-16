package com.level.tech.service;

import com.level.tech.dto.CustomerDTO;
import com.level.tech.dto.request.CustomerRequest;
import com.level.tech.dto.response.PagedResponseDTO;
import com.level.tech.entity.Customer;
import com.level.tech.exception.AlreadyExistsException;
import com.level.tech.exception.EntityNotFoundException;
import com.level.tech.mapper.CustomerMapper;
import com.level.tech.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    @Override
    public CustomerDTO addCustomer(final CustomerRequest request) {
        if (request.getEmail().isEmpty()
            && customerRepository.existsByEmail(request.getEmail())
        ) {
            throw new AlreadyExistsException("Email already exists");
        }

        var savedCustomer = customerMapper.toEntity(request);
        return customerMapper.toDTO(savedCustomer);
    }

    @Override
    public CustomerDTO updateCustomer(final Long id, final CustomerRequest request) {
        var savedCustomer = customerMapper.updateEntity(id, request);
        return customerMapper.toDTO(savedCustomer);
    }

    @Override
    public CustomerDTO getCustomer(final Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::toDTO)
                .orElseThrow(()-> new EntityNotFoundException("Customer not Found"));
    }

    @Override
    public List<CustomerDTO> getCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toDTO)
                .toList();
    }

    @Override
    public PagedResponseDTO getAllCustomer(
            final Integer pageNo,
            final Integer count,
            final String sortBy,
            final String orderBy,
            final String search
    ) {
        Pageable pageable = PageRequest.of(
                pageNo,
                count,
                sortBy.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()
        );

        Page<Customer> customerList = (search == null || search.isBlank())
                ? customerRepository.findAll(pageable)
                : customerRepository.findAllCustomers(search, pageable);

        List<CustomerDTO> allCustomers = customerList.getContent()
                .stream()
                .map(customerMapper::toDTO)
                .toList();

        return customerMapper.customerResponseDTO(allCustomers, pageNo, customerList);
    }

    @Override
    public void deleteCustomer(final Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Customer not Found"));
        customerRepository.delete(customer);
    }

    @Override
    public void deleteCustomers(final List<Long> ids) {
        ids.forEach(this::deleteCustomer);
    }
}
