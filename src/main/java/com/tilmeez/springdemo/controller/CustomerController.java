package com.tilmeez.springdemo.controller;

import com.tilmeez.springdemo.dao.CustomerDAO;
import com.tilmeez.springdemo.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    // need to inject the customer dao
    @Autowired
    private CustomerDAO customerDAO;

    @GetMapping("/list")
    public String listCustomer(Model theModel) {

        // get customer from the dao
        List<Customer> theCustomers = customerDAO.getCustomers();

        // add the customer to the model
        theModel.addAttribute("customers", theCustomers);

        return "list-customers";
    }
}
