package com.level.tech.config;

import com.level.tech.entity.Role;
import com.level.tech.entity.User;
import com.level.tech.repository.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PostConfig {

    private final MasterData masterData;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BankRepository bankRepository;

    private final StateRepository stateRepository;

    private final PasswordEncoder passwordEncoder;

    private final SpringTemplateEngine templateEngine;

    private final CategoryRepository categoryRepository;

    private final AccountTypeRepository accountTypeRepository;

    @Value("${application.admin.email}")
    private String email;

    @Value("${application.admin.password}")
    private String password;

    @Value("${application.admin.phone-no}")
    private String phoneNo;

    @Transactional
    @PostConstruct
    public void initializeAdmin() {

        preloadTemplates();

        if (roleRepository.findAll().isEmpty()) {
            List<Role> roles = Arrays.asList(
                    new Role("SUPER ADMIN"),
                    new Role("ADMIN"),
                    new Role("STAFF")
            );
            roleRepository.saveAll(roles);
        }

        if (Boolean.FALSE.equals(userRepository.existsByEmail(email))) {
            User admin = new User();
            admin.setName("Kanal Kannan");
            admin.setEmail(email);
            admin.setPassword(passwordEncoder.encode(password));
            admin.setPhoneNo(phoneNo);
            admin.setTermsAndConditions(true);

            Role role = roleRepository.findByName("SUPER ADMIN");
            admin.setRole(role);

            userRepository.save(admin);
        }

        if (bankRepository.findAll().isEmpty()) {
            masterData.loadBanks();
        }

        if (stateRepository.findAll().isEmpty()) {
            masterData.loadStates();
        }

        if (categoryRepository.findAll().isEmpty()) {
            masterData.loadCategories();
        }
        if (accountTypeRepository.findAll().isEmpty()) {
            masterData.loadAccountType();
        }

    }

    public void preloadTemplates() {
        Context context = new Context();
        User user = new User();
        user.setName("dummy");
        context.setVariable("user", user);
        context.setVariable("otp", "123456");
        context.setVariable("admin", user);
        context.setVariable("password", password);

        templateEngine.process("login_otp", context);
    }
}
