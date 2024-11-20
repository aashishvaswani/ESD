package com.aashish.ESD.service;

import com.aashish.ESD.dto.CustomerRequest;
import com.aashish.ESD.dto.CustomerResponse;
import com.aashish.ESD.dto.LoginRequest;
import com.aashish.ESD.entity.Customer;
import com.aashish.ESD.helper.JwtUtil;
import com.aashish.ESD.mapper.CustomerMapper;
import com.aashish.ESD.repo.CustomerRepo;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String validateAndExtractEmail(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid Authorization header");
        }
        String token = authHeader.substring(7);

        if (!jwtUtil.isTokenValid(token)) {
            throw new RuntimeException("Invalid or expired token");
        }
        return jwtUtil.extractEmail(token);
    }

    public String createCustomer(CustomerRequest request) {
        Customer customer = customerMapper.toEntity(request);
        customer.setPassword(passwordEncoder.encode(request.password()));
        customerRepo.save(customer);
        return "Customer created successfully";
    }

    public String login(LoginRequest request) {
        Optional<Customer> customerOptional = customerRepo.findByEmail(request.email());

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            if (passwordEncoder.matches(request.password(), customer.getPassword())) {//raw string, encrypt. pass
                return jwtUtil.generateToken(customer.getEmail());
            }
        }
        throw new RuntimeException("Invalid credentials");
    }

    public CustomerResponse updateCustomer(String authHeader, CustomerRequest request) {
        String headerEmail = validateAndExtractEmail(authHeader);
        if(headerEmail!=null) {
            Customer customer = customerRepo.findByEmail(request.email()).get();

            if(customer.getEmail().equals(headerEmail)) {
                customer.setFirstName(request.firstName());
                customer.setLastName(request.lastName());
                customer.setPassword(passwordEncoder.encode(request.password()));
                customer.setCity(request.city());
                customer.setAddress(request.address());
                customer.setPincode(request.pincode());
                customerRepo.save(customer);
                return customerMapper.toCustomerResponse(customer);
            }
        }
        throw new RuntimeException("Invalid credentials");
    }

    public String deleteCustomer(String authHeader, LoginRequest request) {
        String headerEmail = validateAndExtractEmail(authHeader);
        if(headerEmail!=null) {
            Customer customer = customerRepo.findByEmail(request.email()).get();

            if(customer.getEmail().equals(headerEmail)) {
                customerRepo.delete(customer);
                return "Customer deleted successfully";
            }
        }

        return "Invalid credentials";
    }

    public CustomerResponse getCustomerDetails(String authHeader) {
        String headerEmail = validateAndExtractEmail(authHeader);
        if(headerEmail!=null) {
            Customer customer = customerRepo.findByEmail(headerEmail).get();
            return customerMapper.toCustomerResponse(customer);
        }

        throw new RuntimeException("Invalid credentials");
    }
}
