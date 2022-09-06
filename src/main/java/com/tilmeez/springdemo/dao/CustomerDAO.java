package com.tilmeez.springdemo.dao;

import com.tilmeez.springdemo.entity.Customer;

import java.util.List;

public interface CustomerDAO {

    public List<Customer> getCustomers();

   public void saveCustomer(Customer theCustomer);
}
