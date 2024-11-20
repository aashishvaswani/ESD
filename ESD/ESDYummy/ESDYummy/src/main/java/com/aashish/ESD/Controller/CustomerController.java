package com.aashish.ESD.Controller;

import com.aashish.ESD.dto.CustomerRequest;
import com.aashish.ESD.dto.CustomerResponse;
import com.aashish.ESD.dto.LoginRequest;
import com.aashish.ESD.service.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<String> createCustomer(@RequestBody @Valid CustomerRequest request) {
        return ResponseEntity.ok(customerService.createCustomer(request));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request) {
        String token = customerService.login(request);
        return ResponseEntity.ok(token);
    }

    @GetMapping
    public ResponseEntity<CustomerResponse> getCustomerDetails(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.ok(customerService.getCustomerDetails(authHeader));
    }

    @PatchMapping
    public ResponseEntity<CustomerResponse> updateCustomer(@RequestHeader("Authorization") String authHeader, @RequestBody @Valid CustomerRequest request) {
        return ResponseEntity.ok(customerService.updateCustomer(authHeader, request));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCustomer(@RequestHeader("Authorization") String authHeader, @RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(customerService.deleteCustomer(authHeader, request));
    }
}
