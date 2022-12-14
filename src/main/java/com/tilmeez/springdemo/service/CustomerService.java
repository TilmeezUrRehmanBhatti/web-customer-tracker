package com.tilmeez.springdemo.service;

import com.tilmeez.springdemo.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CustomerService {
    public List<Customer> getCustomers(int theSortField);

    public void saveCustomer(Customer theCustomer);

    public Customer getCustomer (int theId);

    public void deleteCustomer(int theId);

    public List<Customer> searchCustomer(String theSearchName);
}
