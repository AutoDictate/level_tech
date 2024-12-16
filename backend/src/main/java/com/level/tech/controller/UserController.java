package com.level.tech.controller;

import com.level.tech.dto.UserDTO;
import com.level.tech.dto.request.UserRegisterRequest;
import com.level.tech.dto.request.UserUpdateRequest;
import com.level.tech.dto.response.PagedResponseDTO;
import com.level.tech.dto.response.ResponseData;
import com.level.tech.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> addUser(
            @RequestBody @Valid UserRegisterRequest request
    ) {
        return new ResponseEntity<>(
                userService.addUser(request), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable(name = "userId") Long userId,
            @RequestBody @Valid UserUpdateRequest request
    ) {
        return new ResponseEntity<>(
                userService.updateUser(userId, request), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable(name = "userId") Long userId
    ) {
        return new ResponseEntity<>(
                userService.getUser(userId), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<PagedResponseDTO> getAllUsers(
            @RequestParam(name = "pageNo") Integer pageNo,
            @RequestParam(name = "count") Integer count,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "orderBy", required = false, defaultValue = "asc") String orderBy,
            @RequestParam(name = "search", required = false) String search
    ) {
        if (pageNo != null && count != null && pageNo > 0) {
            pageNo = pageNo - 1;
        }
        return new ResponseEntity<>(
                userService.getAllUsers(pageNo, count, sortBy, orderBy, search), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ResponseData> deleteUsers(
            @RequestBody List<Long> userIds
    ) {
        userService.deleteUsers(userIds);
        return new ResponseEntity<>(
                new ResponseData("User's deleted successfully") , HttpStatus.OK);
    }

}
