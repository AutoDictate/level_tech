package com.level.tech.controller;

import com.level.tech.enums.Branch;
import com.level.tech.service.StocksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stocks")
public class StocksController {

    private final StocksService stocksService;

    @GetMapping
    public ResponseEntity<?> getAllStocksByBranch(
            @RequestParam(name = "pageNo") Integer pageNo,
            @RequestParam(name = "count") Integer count,
            @RequestParam(name = "branch", defaultValue = "CHENNAI") Branch branch,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "orderBy", required = false, defaultValue = "asc") String orderBy,
            @RequestParam(name = "search", required = false) String search
    ) {
        return new ResponseEntity<>(
                stocksService.getAllStocks(branch, pageNo, count, sortBy, orderBy, search), HttpStatus.OK);
    }

}
