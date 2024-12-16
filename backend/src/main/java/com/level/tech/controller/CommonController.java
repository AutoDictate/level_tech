package com.level.tech.controller;

import com.level.tech.entity.AccountType;
import com.level.tech.entity.Bank;
import com.level.tech.entity.State;
import com.level.tech.repository.AccountTypeRepository;
import com.level.tech.repository.BankRepository;
import com.level.tech.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/common")
public class CommonController {

    private final BankRepository bankRepository;

    private final StateRepository stateRepository;

    private final AccountTypeRepository accountTypeRepository;

    @GetMapping("/banks")
    public ResponseEntity<List<Bank>> getAllBanks() {
        return new ResponseEntity<>(bankRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/states")
    public ResponseEntity<List<State>> getAllStates() {
        return new ResponseEntity<>(stateRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/account-types")
    public ResponseEntity<List<AccountType>> getAllAccountTypes() {
        return new ResponseEntity<>(accountTypeRepository.findAll(), HttpStatus.OK);
    }
}
