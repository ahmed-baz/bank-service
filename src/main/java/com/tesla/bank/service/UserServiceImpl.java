package com.tesla.bank.service;

import com.tesla.bank.dto.CustomerRequest;
import com.tesla.bank.model.CustomerEntity;
import com.tesla.bank.repo.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final CustomerRepository customerRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomerEntity customer = customerRepo.findByEmailIgnoreCase(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return User.builder()
                .username(customer.getEmail())
                .password(customer.getPassword())
                .authorities(List.of(new SimpleGrantedAuthority(customer.getRole())))
                .build();
    }

    @Override
    public void registerCustomer(CustomerRequest request) {
        Optional<CustomerEntity> customer = customerRepo.findByEmailIgnoreCase(request.email());
        if (customer.isPresent()) throw new RuntimeException("email already exists");
        CustomerEntity user = CustomerEntity.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role("user")
                .build();
        customerRepo.save(user);
    }
}
