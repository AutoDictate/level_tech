package com.level.tech.service;

import com.level.tech.dto.response.PagedResponseDTO;
import com.level.tech.enums.Branch;

public interface StocksService {

    PagedResponseDTO getAllStocks(Branch branch, Integer pageNo, Integer count, String sortBy, String orderBy, String search);

}
