package com.tilmeez.springdemo.controller;

import com.tilmeez.springdemo.dao.CustomerDAO;
import com.tilmeez.springdemo.entity.Customer;
import com.tilmeez.springdemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    // need to inject our customer service
    @Autowired
    private CustomerService customerService;

    @GetMapping("/list")
    public String listCustomer(Model theModel) {

        // get customer from the service
        List<Customer> theCustomers = customerService.getCustomers();

        // add the customer to the model
        theModel.addAttribute("customers", theCustomers);

        return "list-customers";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {

        // create model attribute to bind form data
        Customer theCustomer = new Customer();

        theModel.addAttribute("customer", theCustomer);

        return "customer-form";
    }

    @PostMapping("/saveCustomer")
    public String saveCustomer(@ModelAttribute("customer") Customer theCustomer){

        // save the customer using our service
        customerService.saveCustomer(theCustomer);

        return "redirect:/customer/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("customerId") int theId, Model theModel) {

        // get the customer from the service
        Customer theCustomer = customerService.getCustomers(theId);

        // set customer as a model attribute to pre-populate the form
        theModel.addAttribute("customer", theCustomer);

        // send over to form
        return "customer-form";
    }

    @GetMapping("/delete")
    public String deleteCustomer(@RequestParam("customerId") int theId) {

        // delete the customer
        customerService.deleteCustomer(theId);

        return "redirect:/customer/list";
    }

    @GetMapping("/search")
    public String searchCustomer(@RequestParam("theSearchName") String theSearchName,Model theModel) {

        // search customer from the service
        List<Customer> theCustomer = customerService.searchCustomer(theSearchName);

        // add the customer to model
        theModel.addAttribute(("customers"), theCustomer);

        return "list-customers";
    }
}



















