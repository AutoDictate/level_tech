package com.level.tech.service;

import com.level.tech.dto.UserDTO;
import com.level.tech.dto.request.UserRegisterRequest;
import com.level.tech.dto.request.UserUpdateRequest;
import com.level.tech.dto.response.PagedResponseDTO;

import java.util.List;

public interface UserService {

    UserDTO addUser(UserRegisterRequest request);

    UserDTO updateUser(Long userId, UserUpdateRequest request);

    UserDTO getUser(Long userId);

    List<UserDTO> getAllUsers();

    PagedResponseDTO getAllUsers(Integer pageNo, Integer count, String sortBy, String orderBy, String search);

    void deleteUser(Long userId);

    void deleteUsers(List<Long> userIds);

}
