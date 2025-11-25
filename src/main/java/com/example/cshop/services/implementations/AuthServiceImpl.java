package com.example.cshop.services.implementations;

import com.example.cshop.dtos.user.UserLoginDto;
import com.example.cshop.dtos.user.UserRegisterDto;
import com.example.cshop.models.Role;
import com.example.cshop.models.User;
import com.example.cshop.repositories.UserRepository;
import com.example.cshop.services.interfaces.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(UserRegisterDto dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        Set<Role> roles = new HashSet<>();

        if (dto.getEmail().toLowerCase().endsWith("@cshop.com")) {
            roles.add(Role.ROLE_ADMIN);
            roles.add(Role.ROLE_USER);
            System.out.println("Admin role assigned to: " + dto.getEmail());
        } else {
            roles.add(Role.ROLE_USER);
        }

        user.setRoles(roles);
        user.setEnabled(true);

        return userRepository.save(user);
    }


    @Override
    public User login(UserLoginDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }

    @Override
    public void logout() {
        SecurityContextHolder.clearContext();
    }
}
