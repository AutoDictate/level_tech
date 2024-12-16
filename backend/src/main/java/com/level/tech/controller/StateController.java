package com.level.tech.controller;

import com.level.tech.dto.response.ResponseData;
import com.level.tech.entity.State;
import com.level.tech.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/state")
public class StateController {

    private final StateService stateService;

    @PostMapping
    public ResponseEntity<ResponseData> addState(
            @RequestBody String name
    ) {
        stateService.addState(name);
        return new ResponseEntity<>(
                new ResponseData("State added successfully"), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ResponseData> addState(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "name") String name
    ) {
        stateService.updateState(id, name);
        return new ResponseEntity<>(
                new ResponseData("State updated successfully"), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<State>> getStates() {
        return new ResponseEntity<>(
                stateService.getStates(), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<ResponseData> deleteStates(
        @RequestBody List<Long> stateIds
    ) {
        stateService.deleteStates(stateIds);
        return new ResponseEntity<>(
                new ResponseData("Selected States Successfully"), HttpStatus.CREATED);
    }

}
