package com.level.tech.service;

import com.level.tech.dto.UserDTO;
import com.level.tech.dto.request.UserRegisterRequest;
import com.level.tech.dto.request.UserUpdateRequest;
import com.level.tech.dto.response.PagedResponseDTO;
import com.level.tech.entity.Role;
import com.level.tech.entity.User;
import com.level.tech.exception.AlreadyExistsException;
import com.level.tech.exception.BadCredentialException;
import com.level.tech.exception.EntityNotFoundException;
import com.level.tech.mapper.UserMapper;
import com.level.tech.repository.RoleRepository;
import com.level.tech.repository.TokenRepository;
import com.level.tech.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final TokenRepository tokenRepository;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserMapper userMapper;

    @Override
    public UserDTO addUser(UserRegisterRequest request) {

        if (request.getTermsAndConditions().equals(Boolean.FALSE)) {
            throw new BadCredentialException("Terms and conditions should be accepted");
        }

        if (Boolean.TRUE.equals(userRepository.existsByEmail(request.getEmail()))) {
            throw new AlreadyExistsException("User already exists with the email");
        }

        var savedUser = userMapper.toUserEntity(request);
        return userMapper.toUserDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(final Long userId, final UserUpdateRequest request) {
        var savedUser = userMapper.updateUser(userId, request);
        return userMapper.toUserDTO(savedUser);
    }

    @Override
    public UserDTO getUser(final Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::toUserDTO)
                .orElseThrow(()-> new EntityNotFoundException("User not found"));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDTO)
                .toList();
    }

    @Override
    public PagedResponseDTO getAllUsers(
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

        Role role = roleRepository.findByName("USER");
        Page<User> userList = (search == null || search.isBlank())
                ? userRepository.findAll(pageable)
                : userRepository.searchUsers(search, role, pageable);

        List<UserDTO> allUsers = userList.getContent()
                .stream()
                .map(userMapper::toUserDTO)
                .toList();

        return userMapper.userResponseDTO(allUsers, pageNo, userList);
    }

    @Override
    public void deleteUser(final Long userId) {
        User user = userRepository.findById(userId)
                        .orElseThrow(()-> new EntityNotFoundException("User not found"));
        user.setIsDeleted(true);
        tokenRepository.deleteAllByUserId(userId);
        userRepository.save(user);
    }

    @Override
    public void deleteUsers(final List<Long> userIds) {
        if (userIds.isEmpty()) {
            throw new BadCredentialException("Empty list of users can't be deleted");
        } else {
            userIds.forEach(this::deleteUser);
        }
    }
}
