package com.example.bankapp.dao;

import com.example.bankapp.model.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
@NoArgsConstructor
public class CustomerDaoImpl implements CustomerDao {
    @Override
    public Customer depositMoney(Customer customer, float money) {
        float balance = customer.getCustomerBalance() + money;
        customer.setCustomerBalance(balance);
        return customer;
    }
    @Override
    public Customer withdrawMoney(Customer customer, float money) {
        float balance = customer.getCustomerBalance() - money;
        if (balance < 1000) {
            return null;
        }
        customer.setCustomerBalance(balance);
        return customer;
    }
    @Override
    public float openFd(Customer customer, float money, float years) {
        float balance = customer.getCustomerBalance();
        if (balance <= 2 * money || money < 0) {
            return -1;
        }
        customer.setCustomerBalance(balance - money);
        return (float) (balance * (1 + 0.07 * years));
    }

    @Override
    public float applyLoan(Customer customer, float money, float years) {
        float balance = customer.getCustomerBalance();
        if (balance <= 2 * money) {
            return -1;
        }
        customer.setCustomerBalance(balance - money);
        return (float) ((balance * Math.pow(1.1, years)) / years);
    }
}
