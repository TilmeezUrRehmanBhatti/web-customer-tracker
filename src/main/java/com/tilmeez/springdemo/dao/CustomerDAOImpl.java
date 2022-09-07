package com.tilmeez.springdemo.dao;

import com.tilmeez.springdemo.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

    // need to inject the session factory
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Customer> getCustomers() {

        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();


        // create a  query ... sort by the last name
        Query<Customer> theQuery =
                currentSession.createQuery("from Customer ORDER BY lastName",
                        Customer.class);

        // execute query and get result list
        List<Customer> customers = theQuery.getResultList();

        // return the result
        return customers;
    }

    @Override
    public void saveCustomer(Customer theCustomer) {

        // get current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();


        // save the customer
        currentSession.saveOrUpdate(theCustomer);
    }

    @Override
    public Customer getCustomers(int theId) {

        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // now retrieve / read from the database using the primary key
        Customer theCustomer = currentSession.get(Customer.class, theId);

        return theCustomer;
    }

    @Override
    public void deleteCustomer(int theId) {

        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // delete the customer with primary key
        Query theQuery =
                currentSession.createQuery("delete from Customer  where id=:customerId");

        theQuery.setParameter("customerId", theId);

        theQuery.executeUpdate();

    }
}































