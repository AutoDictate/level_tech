package com.level.tech.mapper;

import com.level.tech.dto.SaleDTO;
import com.level.tech.dto.SaleItemDTO;
import com.level.tech.dto.request.SaleRequest;
import com.level.tech.dto.response.PagedResponseDTO;
import com.level.tech.entity.Customer;
import com.level.tech.entity.Sale;
import com.level.tech.entity.SaleItem;
import com.level.tech.exception.EntityNotFoundException;
import com.level.tech.repository.CustomerRepository;
import com.level.tech.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleMapper {

    private final CustomerRepository customerRepository;

    private final SaleItemMapper saleItemMapper;

    private final SaleRepository saleRepository;

    @Transactional
    public Sale toEntity(final SaleRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(()-> new EntityNotFoundException("Customer not found"));

        Sale sale = new Sale();
        sale.setCustomer(customer);
        sale.setGstNo(request.getGstNo());
        sale.setDispatchCity(request.getDispatchCity());
        sale.setTransporter(request.getTransporter());
        sale.setPaymentMode(request.getPaymentMode());
        sale.setSameShipping(request.getSameShipping());
        sale.setInvoiceNo(request.getInvoiceNo());
        sale.setYear(request.getYear());
        sale.setBillDate(request.getBillDate());
        sale.setDeliveryNo(request.getDeliveryNo());
        sale.setDeliveryDate(request.getDeliveryDate());
        sale.setPurchaseOrderNo(request.getPurchaseOrderNo());
        sale.setReverseCharge(request.getReverseCharge());
        sale.setPackingCharge(request.getPackingCharge());
        sale.setTransportCharge(request.getTransportCharge());
        sale.setCgst(request.getCgst());
        sale.setSgst(request.getSgst());
        sale.setIgst(request.getIgst());
        sale.setTaxAmountGst(request.getTaxAmountGst());
        sale.setPaidDate(request.getPaidDate());
        sale.setPaidAmount(request.getPaidAmount());
        sale.setSubTotal(request.getSubTotal());
        sale.setGrandTotal(request.getGrandTotal());

        List<SaleItem> saleItems = request.getSaleItemRequest()
                .stream()
                .map(si -> saleItemMapper.toEntity(si, sale))
                .toList();

        sale.setSaleItems(saleItems);

        return saleRepository.save(sale);
    }

    @Transactional
    public Sale updateSale(final Long saleId, final SaleRequest request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(()-> new EntityNotFoundException("Customer not found"));

        Sale sale = saleRepository.findById(saleId)
                        .orElseThrow(()-> new EntityNotFoundException("Sales not found"));
        sale.setCustomer(customer);
        sale.setGstNo(request.getGstNo());
        sale.setDispatchCity(request.getDispatchCity());
        sale.setTransporter(request.getTransporter());
        sale.setPaymentMode(request.getPaymentMode());
        sale.setPaymentStatus(request.getPaymentStatus());
        sale.setSameShipping(request.getSameShipping());
        sale.setInvoiceNo(request.getInvoiceNo());
        sale.setYear(request.getYear());
        sale.setBillDate(request.getBillDate());
        sale.setDeliveryDate(request.getDeliveryDate());
        sale.setPurchaseOrderNo(request.getPurchaseOrderNo());
        sale.setReverseCharge(request.getReverseCharge());
        sale.setPackingCharge(request.getPackingCharge());
        sale.setTransportCharge(request.getTransportCharge());
        sale.setCgst(request.getCgst());
        sale.setSgst(request.getSgst());
        sale.setIgst(request.getIgst());
        sale.setTaxAmountGst(request.getTaxAmountGst());
        sale.setPaidDate(request.getPaidDate());
        sale.setPaidAmount(request.getPaidAmount());
        sale.setSubTotal(request.getSubTotal());
        sale.setGrandTotal(request.getGrandTotal());

        List<SaleItem> saleItems = request.getSaleItemRequest()
                .stream()
                .map(si -> saleItemMapper.toEntity(si, sale))
                .toList();

        sale.setSaleItems(saleItems);

        return saleRepository.save(sale);
    }

    public SaleDTO toDTO(final Sale sale) {
        List<SaleItemDTO> saleItemDTOS = sale.getSaleItems()
                .stream()
                .map(saleItemMapper::toDTO)
                .toList();

        SaleDTO saleDTO = new SaleDTO();
        saleDTO.setId(sale.getId());
        saleDTO.setCustomerName(sale.getCustomer().getName());
        saleDTO.setGstNo(sale.getGstNo());
        saleDTO.setDispatchCity(sale.getDispatchCity());
        saleDTO.setTransporter(sale.getTransporter());
        saleDTO.setPaymentMode(sale.getPaymentMode());
        saleDTO.setPaymentStatus(sale.getPaymentStatus());
        saleDTO.setSameShipping(sale.getSameShipping());
        saleDTO.setInvoiceNo(sale.getInvoiceNo());
        saleDTO.setYear(sale.getYear());
        saleDTO.setBillDate(sale.getBillDate());
        saleDTO.setDeliveryNo(saleDTO.getDeliveryNo());
        saleDTO.setDeliveryDate(sale.getDeliveryDate());
        saleDTO.setPurchaseOrderNo(sale.getPurchaseOrderNo());
        saleDTO.setReverseCharge(sale.getReverseCharge());
        saleDTO.setPackingCharge(sale.getPackingCharge());
        saleDTO.setTransportCharge(sale.getTransportCharge());
        saleDTO.setCgst(sale.getCgst());
        saleDTO.setSgst(sale.getSgst());
        saleDTO.setIgst(sale.getIgst());
        saleDTO.setTaxAmountGst(sale.getTaxAmountGst());
        saleDTO.setPaidDate(sale.getPaidDate());
        saleDTO.setPaidAmount(sale.getPaidAmount());
        saleDTO.setSubTotal(sale.getSubTotal());
        saleDTO.setGrandTotal(sale.getGrandTotal());
        saleDTO.setSaleItems(saleItemDTOS);

        return saleDTO;
    }

    public PagedResponseDTO saleResponseDTO(
            final List<SaleDTO> allSales,
            final Integer pageNo,
            final Page<Sale> saleList
    ) {
        return new PagedResponseDTO(
                allSales,
                pageNo + 1,
                saleList.getTotalElements(),
                saleList.getTotalPages()
        );
    }

}
