package com.example.bankapp.service;

import com.example.bankapp.dao.CustomerDaoImpl;
import com.example.bankapp.exceptions.CustomRestServiceException;
import com.example.bankapp.exceptions.CustomerNotFoundException;
import com.example.bankapp.model.Customer;
import com.example.bankapp.model.Transaction;
import com.example.bankapp.repository.CustomerRepo;
import com.example.bankapp.repository.TransactionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepo customerRepo;
    private final TransactionRepo transactionRepo;
    private final CustomerDaoImpl customerDao;
    @Override
    public Customer createCustomer(Customer customer) throws CustomRestServiceException {
        try {
            customer.setCustomerId(customer.getCustomerAadhar());
            return customerRepo.save(customer);
        } catch (Exception e) {
            throw new CustomRestServiceException("Error in createCustomer is: ", e);
        }
    }
    @Override
    public Customer getSingleCustomerById(String customerId) {
        return customerRepo.findById(customerId).orElseThrow();
    }
    @Override
    public List<Customer> getAllCustomers() throws CustomRestServiceException {
        try {
            return customerRepo.findAll();
        } catch (RuntimeException e) {
            throw new CustomRestServiceException("Error in getAllCustomers is: ", e);
        }
    }
    @Override
    public Customer deleteCustomer(String customerId) {
        return customerRepo.findById(customerId).orElseThrow();
    }
    @Override
    public Boolean checkCustomer(String customerId, String password) {
        boolean customerIdMatches = customerRepo.findById(customerId).isPresent();
        String passwordFromDatabase = customerRepo.findPasswordByCustomerID(customerId);
        return (customerIdMatches && Objects.equals(passwordFromDatabase, password));
    }
    @Override
    public List<Transaction> getLastTransaction(String customerId) throws CustomerNotFoundException {
        Optional<Customer> checkCustomerIsPresent = customerRepo.findById(customerId);
        if (checkCustomerIsPresent.isPresent()) {
            return transactionRepo.findTransactions(customerId);
        }else{
            throw new CustomerNotFoundException("Customer not found");
        }
    }
    @Override
    public float depositMoney(String customerId, float money) throws CustomerNotFoundException {
        Optional<Customer> checkCustomerIsPresent = customerRepo.findById(customerId);
        Customer customer;
        try {
            if (checkCustomerIsPresent.isPresent()) {
                customer = checkCustomerIsPresent.get();
                if (money < 0) {
                    System.out.println("Invalid Money");
                    return -2;
                }
                customer = customerDao.depositMoney(customer, money);
                customerRepo.save(customer);
                this.transactionRepo.save(Transaction.builder()
                        .customer(customer)
                        .transactionMoney(money)
                        .customerBalance(customer.getCustomerBalance())
                        .transactionDate(new Date())
                        .transactionType("Deposit")
                        .build());
                return customer.getCustomerBalance();
            } else {
                throw new CustomerNotFoundException("Customer not found");
            }
        } catch (Exception e){
            throw new CustomerNotFoundException("Money not deposited");
        }
    }
    @Override
    public float withdrawMoney(String customerId, float money) throws CustomerNotFoundException {
        Optional<Customer> checkCustomerIsPresent = customerRepo.findById(customerId);
        Customer customer;
        try {
            if (checkCustomerIsPresent.isPresent()) {
                customer = checkCustomerIsPresent.get();
                if (money < 0) {
                    System.out.println("Invalid Money");
                    return -2;
                }
                customer = customerDao.withdrawMoney(customer, money);
                if (customer == null) {
                    return -1;
                }
                this.customerRepo.save(customer);
                this.transactionRepo.save(Transaction.builder()
                        .customer(customer)
                        .transactionMoney(money)
                        .customerBalance(customer.getCustomerBalance())
                        .transactionDate(new Date())
                        .transactionType("Withdraw")
                        .build());
                return customer.getCustomerBalance();
            } else {
                throw new CustomerNotFoundException("Customer not found");
            }
        } catch (Exception e){
            throw new CustomerNotFoundException("Money not withdrawn");
        }
    }
    @Override
    public float openFd(String customerId, float money, float years) throws CustomerNotFoundException {
        Optional<Customer> checkCustomerIsPresent = customerRepo.findById(customerId);
        Customer customer;
        try {
            if (checkCustomerIsPresent.isPresent()) {
                customer = checkCustomerIsPresent.get();
                float amount = customerDao.openFd(customer, money, years);
                if (amount == -1) {
                    System.out.println("ONE");
                    return amount;
                }
                this.transactionRepo.save(Transaction.builder()
                        .customer(customer)
                        .transactionMoney(money)
                        .customerBalance(customer.getCustomerBalance())
                        .transactionDate(new Date())
                        .transactionType("Fd")
                        .build());
                return amount;
            } else {
                throw new CustomerNotFoundException("Customer not found");
            }
        } catch (Exception e){
            throw new CustomerNotFoundException("Open FD failed");
        }
    }
    @Override
    public float applyLoan(String customerId, float money, float years) throws CustomerNotFoundException {
        Optional<Customer> checkCustomerIsPresent = customerRepo.findById(customerId);
        Customer customer;
        try {
            if (checkCustomerIsPresent.isPresent()) {
                customer = checkCustomerIsPresent.get();
                float amount = customerDao.applyLoan(customer, money, years);
                if (amount == -1) {
                    return amount;
                }
                this.transactionRepo.save(Transaction.builder()
                        .customer(customer)
                        .transactionMoney(money)
                        .customerBalance(customer.getCustomerBalance())
                        .transactionDate(new Date())
                        .transactionType("Loan")
                        .build());
                return amount;
            } else {
                throw new CustomerNotFoundException("Customer not found");
            }
        } catch (Exception e){
            throw new CustomerNotFoundException("Open FD failed");
        }
    }
}
