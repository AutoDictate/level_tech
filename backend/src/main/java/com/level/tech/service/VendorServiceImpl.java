package com.level.tech.service;

import com.level.tech.dto.VendorDTO;
import com.level.tech.dto.request.VendorRequest;
import com.level.tech.dto.response.PagedResponseDTO;
import com.level.tech.entity.Vendor;
import com.level.tech.exception.EntityNotFoundException;
import com.level.tech.mapper.VendorMapper;
import com.level.tech.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;

    private final VendorMapper vendorMapper;

    @Override
    public VendorDTO addVendor(final VendorRequest request) {
        var savedVendor = vendorMapper.toVendorEntity(request);
        return vendorMapper.toVendorDTO(savedVendor);
    }

    @Override
    public VendorDTO updateVendor(final Long vendorId, final VendorRequest request) {
        var savedVendor = vendorMapper.updateVendorEntity(vendorId, request);
        return vendorMapper.toVendorDTO(savedVendor);
    }

    @Override
    public VendorDTO getVendor(final Long vendorId) {
        return vendorRepository.findById(vendorId)
                .map(vendorMapper::toVendorDTO)
                .orElseThrow(()-> new EntityNotFoundException("Vendor not found"));
    }

    @Override
    public List<VendorDTO> getVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(vendorMapper::toVendorDTO)
                .toList();
    }

    @Override
    public PagedResponseDTO getAllVendors(
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

        Page<Vendor> vendorList = (search == null || search.isBlank())
                ? vendorRepository.findAll(pageable)
                : vendorRepository.findAllVendors(search, pageable);

        List<VendorDTO> allVendors = vendorList.getContent()
                .stream()
                .map(vendorMapper::toVendorDTO)
                .toList();

        return vendorMapper.vendorResponseDTO(allVendors, pageNo, vendorList);
    }

    @Override
    @Transactional
    public void deleteVendor(final Long vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(()-> new EntityNotFoundException("Vendor not found"));

        vendorRepository.delete(vendor);
    }

    @Override
    public void deleteVendors(final List<Long> vendorIds) {
        vendorIds.forEach(this::deleteVendor);
    }
}
