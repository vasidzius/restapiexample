package com.vasidzius.restapiexample.controller;

import com.vasidzius.restapiexample.dao.CustomerRepository;
import com.vasidzius.restapiexample.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * The type Customer resource.
 */
@RestController
public class CustomerResource {

    @Autowired
    private CustomerRepository repository;

    /**
     * Retrieve all customers.
     *
     * @return all customers, otherwise FORBIDDEN if no authorization
     */
    @GetMapping("/customers")
    public ResponseEntity retrieveAllCustomers(HttpServletRequest request) {
        String token = Utils.getToken(request);
        if (Utils.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.OK).body(repository.findAll());
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You aren't admin");
    }

    /**
     * Retrieve customer.
     *
     * @param id the customer id
     * @return the customer, otherwise FORBIDDEN if no authorization
     */
    @GetMapping("/customers/{id}")
    public ResponseEntity retrieveCustomer(@PathVariable long id, HttpServletRequest request) {
        String token = Utils.getToken(request);

        if (Utils.isAdmin(token) || Utils.theSameAccount(id, token)) {
            Optional<Customer> byId = repository.findById(id);
            return byId.<ResponseEntity>map(partnerMapping -> ResponseEntity.status(HttpStatus.OK).body(partnerMapping))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't find customer with id " + id));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You aren't admin, only your account information is accessible");
    }

    /**
     * Retrieve current logged in customer.
     *
     * @return the current logged in customer
     */
    @GetMapping("/customers/me")
    public ResponseEntity retrieveCurrentCustomer(HttpServletRequest request) {
        String token = Utils.getToken(request);
        Long id = Long.parseLong(token);
        Optional<Customer> byId = repository.findById(id);
        return byId.<ResponseEntity>map(partnerMapping -> ResponseEntity.status(HttpStatus.OK).body(partnerMapping))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't find customer with id " + id));
    }
}
