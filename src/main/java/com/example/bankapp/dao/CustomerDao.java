package com.example.bankapp.dao;

import com.example.bankapp.exceptions.CustomRestServiceException;
import com.example.bankapp.model.Customer;

public interface CustomerDao {
    Customer depositMoney(Customer customer, float money);
    Customer withdrawMoney(Customer customer, float money) throws CustomRestServiceException;
    float openFd(Customer customer, float money, float years);
    float applyLoan(Customer customer, float money, float years);
}
