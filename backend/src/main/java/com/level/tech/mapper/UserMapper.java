package com.level.tech.mapper;

import com.level.tech.dto.UserDTO;
import com.level.tech.dto.request.UserRegisterRequest;
import com.level.tech.dto.request.UserUpdateRequest;
import com.level.tech.dto.response.PagedResponseDTO;
import com.level.tech.entity.Role;
import com.level.tech.entity.User;
import com.level.tech.exception.EntityNotFoundException;
import com.level.tech.repository.RoleRepository;
import com.level.tech.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Transactional
    public User toUserEntity(final UserRegisterRequest request) {

        Role role = roleRepository.findByName("USER");

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        user.setPhoneNo(request.getPhoneNo());
        user.setTermsAndConditions(request.getTermsAndConditions());

        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(final Long userId, final UserUpdateRequest request) {
        Role role = roleRepository.findByName("USER");

        User user = userRepository.findById(userId)
                        .orElseThrow(()-> new EntityNotFoundException("User not found"));
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(role);
        user.setPhoneNo(request.getPhoneNo());

        return userRepository.save(user);
    }

    @Transactional
    public UserDTO toUserDTO(final User user) {
        return new UserDTO(
                user.getName(),
                user.getEmail(),
                user.getTitle().name(),
                user.getRole().getName(),
                user.getPhoneNo(),
                user.getBranch().name()
        );
    }

    public PagedResponseDTO userResponseDTO(
            final List<UserDTO> allUsers,
            final Integer pageNo,
            final Page<User> userList
    ) {
        return new PagedResponseDTO(
                allUsers,
                pageNo + 1,
                userList.getTotalElements(),
                userList.getTotalPages()
        );
    }


}
