package com.level.tech.mapper;

import com.level.tech.dto.KeyValueDTO;
import com.level.tech.dto.VendorDTO;
import com.level.tech.dto.request.VendorRequest;
import com.level.tech.dto.response.PagedResponseDTO;
import com.level.tech.entity.AccountType;
import com.level.tech.entity.Bank;
import com.level.tech.entity.State;
import com.level.tech.entity.Vendor;
import com.level.tech.exception.EntityNotFoundException;
import com.level.tech.repository.AccountTypeRepository;
import com.level.tech.repository.BankRepository;
import com.level.tech.repository.StateRepository;
import com.level.tech.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorMapper {

    private final AccountTypeRepository accountTypeRepository;

    private final VendorRepository vendorRepository;

    private final StateRepository stateRepository;

    private final BankRepository bankRepository;

    @Transactional
    public Vendor toVendorEntity(final VendorRequest request) {
        AccountType accountType = accountTypeRepository.findById(request.getAccountType())
                .orElseThrow(()-> new EntityNotFoundException("Account type not found"));

        State state = stateRepository.findById(request.getStateId())
                .orElseThrow(()-> new EntityNotFoundException("State not found"));

        Bank bank = bankRepository.findById(request.getBankId())
                .orElseThrow(()-> new EntityNotFoundException("Bank not found"));

        Vendor vendor = new Vendor();
        vendor.setTitle(request.getTitle());
        vendor.setPhoneNo(request.getPhoneNo());
        vendor.setName(request.getName());
        vendor.setIfscCode(request.getIfscCode());
        vendor.setAccountNo(request.getAccountNo());
        vendor.setAccountName(request.getAccountName());
        vendor.setAccountType(accountType);
        vendor.setBankAddress(request.getBankAddress());
        vendor.setCity(request.getCity());
        vendor.setAddress1(request.getAddress1());
        vendor.setAddress2(request.getAddress2());
        vendor.setTerms(request.getTerms());
        vendor.setPostCode(request.getPostCode());
        vendor.setBankBranch(request.getBankBranch());
        vendor.setState(state);
        vendor.setBank(bank);

        return vendorRepository.save(vendor);
    }

    @Transactional
    public Vendor updateVendorEntity(final Long id, final VendorRequest request) {
        AccountType accountType = accountTypeRepository.findById(request.getAccountType())
                .orElseThrow(()-> new EntityNotFoundException("Account type not found"));

        State state = stateRepository.findById(request.getStateId())
                .orElseThrow(()-> new EntityNotFoundException("State not found"));

        Bank bank = bankRepository.findById(request.getBankId())
                .orElseThrow(()-> new EntityNotFoundException("Bank not found"));

        Vendor vendor = vendorRepository.findById(id)
                        .orElseThrow(()-> new EntityNotFoundException("Vendor not found"));
        vendor.setTitle(request.getTitle());
        vendor.setPhoneNo(request.getPhoneNo());
        vendor.setName(request.getName());
        vendor.setIfscCode(request.getIfscCode());
        vendor.setAccountNo(request.getAccountNo());
        vendor.setAccountName(request.getAccountName());
        vendor.setAccountType(accountType);
        vendor.setBankAddress(request.getBankAddress());
        vendor.setCity(request.getCity());
        vendor.setAddress1(request.getAddress1());
        vendor.setAddress2(request.getAddress2());
        vendor.setTerms(request.getTerms());
        vendor.setPostCode(request.getPostCode());
        vendor.setBankBranch(request.getBankBranch());
        vendor.setState(state);
        vendor.setBank(bank);

        return vendorRepository.save(vendor);
    }

    @Transactional
    public VendorDTO toVendorDTO(final Vendor vendor) {
        return VendorDTO.builder()
                .id(vendor.getId())
                .title(vendor.getTitle())
                .phoneNo(vendor.getPhoneNo())
                .name(vendor.getName())
                .ifscCode(vendor.getIfscCode())
                .accountNo(vendor.getAccountNo())
                .accountName(vendor.getAccountName())
                .accountType(vendor.getAccountType().getName())
                .bankAddress(vendor.getBankAddress())
                .city(vendor.getCity())
                .address1(vendor.getAddress1())
                .address2(vendor.getAddress2())
                .terms(vendor.getTerms())
                .postCode(vendor.getPostCode())
                .bankBranch(vendor.getBankBranch())
                .state(toKeyValueDTO(vendor.getState().getId(), vendor.getState().getName()))
                .createdAt(vendor.getCreatedAt())
                .createdBy(vendor.getCreatedBy())
                .lastModifiedAt(vendor.getLastModifiedAt())
                .lastModifiedBy(vendor.getLastModifiedBy())
                .bank(toKeyValueDTO(vendor.getBank().getId(), vendor.getBank().getName()))
                .build();
    }

    public PagedResponseDTO vendorResponseDTO(
            final List<VendorDTO> allVendors,
            final Integer pageNo,
            final Page<Vendor> vendorList
    ) {
        return new PagedResponseDTO(
                allVendors,
                pageNo + 1,
                vendorList.getTotalElements(),
                vendorList.getTotalPages()
        );
    }

    private KeyValueDTO toKeyValueDTO(Long id, String name) {
        return new KeyValueDTO(id, name);
    }

}
