package com.join.me.modules.datainitializer;

import com.join.me.common.constants.InitializerOrder;
import com.join.me.common.enums.ErrorCode;
import com.join.me.common.exceptions.AppException;
import com.join.me.modules.configs.AdminProperties;
import com.join.me.modules.user.constants.role.PredefinedRole;
import com.join.me.modules.user.entity.Role;
import com.join.me.modules.user.entity.User;
import com.join.me.modules.user.enums.UserStatus;
import com.join.me.modules.user.repository.RoleRepository;
import com.join.me.modules.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Order(InitializerOrder.ADMIN)
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AdminInitializer implements ApplicationRunner {
    UserRepository usersRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    AdminProperties adminProperties;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        log.info("Initializing admin ...");

        boolean existed = usersRepository.existsByUsername(adminProperties.getUsername());
        if (!existed) {
            Role adminRole = roleRepository.findByRoleName(PredefinedRole.ADMIN_ROLE)
                    .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

            User admin = User.builder()
                    .username(adminProperties.getUsername())
                    .password(passwordEncoder.encode(adminProperties.getPassword()))
                    .email(adminProperties.getUsername() + "@admin.local")
                    .status(UserStatus.ACTIVE)
                    .roles(Set.of(adminRole))
                    .build();
            usersRepository.save(admin);

            log.info("Admin has been created with username {}", admin.getUsername());
        } else {
            log.info("Admin already exists with username {}", adminProperties.getUsername());
        }
    }
}
