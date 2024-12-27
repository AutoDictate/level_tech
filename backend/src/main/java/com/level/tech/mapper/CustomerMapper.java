package com.level.tech.mapper;

import com.level.tech.dto.CustomerDTO;
import com.level.tech.dto.request.CustomerRequest;
import com.level.tech.dto.response.PagedResponseDTO;
import com.level.tech.entity.Customer;
import com.level.tech.entity.PhoneNumber;
import com.level.tech.entity.State;
import com.level.tech.exception.EntityNotFoundException;
import com.level.tech.repository.CustomerRepository;
import com.level.tech.repository.PhoneNumberRepository;
import com.level.tech.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerMapper {

    private final PhoneNumberRepository phoneNumberRepository;

    private final CustomerRepository customerRepository;

    private final  StateRepository stateRepository;

    @Transactional
    public Customer toEntity(final CustomerRequest request) {
        State state = stateRepository.findById(request.getState())
                .orElseThrow(() -> new IllegalArgumentException("Invalid state ID"));

        Customer customer = new Customer();
        customer.setTitle(request.getTitle());
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setFullAddress(request.getFullAddress());
        customer.setCity(request.getCity());
        customer.setPostCode(request.getPostCode());
        customer.setState(state);

        List<PhoneNumber> phoneNumbers = request.getPhoneNo()
                .stream()
                .map(p -> new PhoneNumber(p, customer)) // PhoneNumber constructor associates with Customer
                .toList();

        customer.setPhoneNo(phoneNumbers);

        return customerRepository.save(customer);
    }

    @Transactional
    public Customer updateEntity(final Long id, final CustomerRequest request) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not Found"));

        State state = stateRepository.findById(request.getState())
                .orElseThrow(() -> new IllegalArgumentException("Invalid state ID"));

        customer.setTitle(request.getTitle());
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setFullAddress(request.getFullAddress());
        customer.setCity(request.getCity());
        customer.setPostCode(request.getPostCode());
        customer.setState(state);

        List<String> newPhoneNumbers = request.getPhoneNo();
        List<PhoneNumber> existingPhoneNumbers = customer.getPhoneNo();

        List<PhoneNumber> toRemove = existingPhoneNumbers.stream()
                .filter(phone -> !newPhoneNumbers.contains(phone.getPhoneNo()))
                .toList();
        toRemove.forEach(phoneNumberRepository::delete);

        List<PhoneNumber> toAdd = newPhoneNumbers.stream()
                .filter(phone -> existingPhoneNumbers.stream()
                        .noneMatch(existing -> existing.getPhoneNo().equals(phone)))
                .map(phone -> new PhoneNumber(phone, customer))
                .toList();

        phoneNumberRepository.saveAll(toAdd);

        existingPhoneNumbers.removeAll(toRemove);
        existingPhoneNumbers.addAll(toAdd);
        customer.setPhoneNo(existingPhoneNumbers);

        return customerRepository.save(customer);
    }

    @Transactional
    public CustomerDTO toDTO(final Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getTitle(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhoneNo(),
                customer.getFullAddress(),
                customer.getCity(),
                customer.getPostCode(),
                customer.getState(),
                customer.getCreatedAt(),
                customer.getLastModifiedAt(),
                customer.getCreatedBy(),
                customer.getLastModifiedBy()
        );
    }

    public PagedResponseDTO customerResponseDTO(
            final List<CustomerDTO> allCustomers,
            final Integer pageNo,
            final Page<Customer> customerList
    ) {
        return new PagedResponseDTO(
                allCustomers,
                pageNo + 1,
                customerList.getTotalElements(),
                customerList.getTotalPages()
        );
    }

}
