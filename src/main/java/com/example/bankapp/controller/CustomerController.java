package com.example.bankapp.controller;

import com.example.bankapp.exceptions.CustomRestServiceException;
import com.example.bankapp.model.Customer;
import com.example.bankapp.model.Transaction;
import com.example.bankapp.service.CustomerService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/customer")
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) throws CustomRestServiceException {
        try {
            return new ResponseEntity<>(customerService.createCustomer(customer), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new CustomRestServiceException("Error in createCustomer is:", e);
        }
    }

    @PutMapping("/deposit/{customerId}/{money}")
    public ResponseEntity<Float> depositMoney(@PathVariable String customerId, @PathVariable float money) throws CustomRestServiceException {
        try{
            return ResponseEntity.ok(customerService.depositMoney(customerId, money));
        } catch (Exception e){
            throw new CustomRestServiceException("Error in depositMoney is: ", e);
        }
    }

    @PutMapping("/withdraw/{customerId}/{money}")
    public ResponseEntity<Float> withdrawMoney(@PathVariable String customerId, @PathVariable float money) throws CustomRestServiceException {
        try {

            return ResponseEntity.ok(customerService.withdrawMoney(customerId, money));
        } catch (Exception e){
            throw new CustomRestServiceException("Error in withdrawMoney is: ", e);
        }
    }

    @GetMapping("/fd/{customerId}/{money}/{years}")
    public ResponseEntity<Float> openFd(
            @PathVariable String customerId,
            @PathVariable float money,
            @PathVariable float years) throws CustomRestServiceException {
        try {
            return ResponseEntity.ok(customerService.openFd(customerId, money, years));
        } catch (Exception e){
            throw new CustomRestServiceException("Error in openFd is: ", e);
        }
    }

    @GetMapping("/loan/{customerId}/{money}/{years}")
    public ResponseEntity<Float> applyLoan(
            @PathVariable String customerId,
            @PathVariable float money,
            @PathVariable float years) throws CustomRestServiceException {
        try {
            return ResponseEntity.ok(customerService.applyLoan(customerId, money, years));
        } catch (Exception e){
            throw new CustomRestServiceException("Error in applyLoan is: ", e);
        }
    }

    @Hidden
    @GetMapping("/customer-by-id/{customerId}")
    public ResponseEntity<Customer> getSingleCustomer(@PathVariable String customerId) throws CustomRestServiceException {
        try {
            return ResponseEntity.ok(customerService.getSingleCustomerById(customerId));
        } catch (Exception e){
            throw new CustomRestServiceException("Error in getSingleCustomer is: ", e);
        }
    }

    @Hidden
    @GetMapping("/show-customers")
    public ResponseEntity<List<Customer>> getAllCustomers() throws CustomRestServiceException {
        try {
            return ResponseEntity.ok(customerService.getAllCustomers());
        } catch (Exception e) {
            throw new CustomRestServiceException("Error in getAllCustomers is: ", e);
        }
    }

    @GetMapping("/check/{customerId}/{password}")
    public ResponseEntity<Boolean> checkCustomer(@PathVariable String customerId, @PathVariable String password) throws CustomRestServiceException {
        try {
            System.out.println(customerService.checkCustomer(customerId, password));
            return ResponseEntity.ok(customerService.checkCustomer(customerId, password));
        } catch (Exception e) {
            throw new CustomRestServiceException("Error in checkCustomer is: ", e);
            
        }
    }

    @DeleteMapping("/remove/{customerId}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable String customerId) throws CustomRestServiceException {
        try {
            return ResponseEntity.ok(customerService.deleteCustomer(customerId));
        } catch (Exception e){
            throw new CustomRestServiceException("Error in deleteCustomer is: ", e);
        }
    }

    @GetMapping("/transactions/{customerId}")
    public ResponseEntity<List<Transaction>> getLastTransaction(@PathVariable String customerId) throws CustomRestServiceException {
        try {
            return ResponseEntity.ok(customerService.getLastTransaction(customerId));
        } catch (Exception e){
            throw new CustomRestServiceException("Error in getAllTransactions is: ", e);
        }
    }
}
