package com.vasidzius.restapiexample.controller;

import com.vasidzius.restapiexample.dao.PartnerMappingRepository;
import com.vasidzius.restapiexample.model.Customer;
import com.vasidzius.restapiexample.model.PartnerMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.vasidzius.restapiexample.controller.Utils.isValidToken;

/**
 * The type Partner mapping resource.
 */
@RestController
@RequestMapping(path = "/customers")
public class PartnerMappingResource {

    @Autowired
    private PartnerMappingRepository repository;

    /**
     * Retrieve all partner mappings for customer.
     *
     * @param customerId the customer id
     * @return all partner mappings for customer, otherwise FORBIDDEN if no authorization
     */
    @GetMapping("/{customerId}/partnerMappings")
    public ResponseEntity retrieveAllPartnerMappings(@PathVariable("customerId") Long customerId, HttpServletRequest request) {
        if (isValidToken(request, customerId)) {
            return ResponseEntity.status(HttpStatus.OK).body(repository.findByCustomerId(customerId));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You aren't admin, only your account information is accessible");

    }

    /**
     * Retrieve partner mapping for customer.
     *
     * @param customerId the customer id
     * @param id         the partner mapping id
     * @return the partner mapping if exist, otherwise FORBIDDEN if no authorization
     */
    @GetMapping("/{customerId}/partnerMappings/{id}")
    public ResponseEntity retrievePartnerMappings(@PathVariable("customerId") long customerId, @PathVariable("id") long id, HttpServletRequest request) {
        if (isValidToken(request, customerId)) {
            List<PartnerMapping> all = repository.findByCustomerId(customerId);
            Optional<PartnerMapping> partnerMapping = all.stream().filter(item -> item.getId() == id).findFirst();
            if (partnerMapping.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(partnerMapping);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You aren't admin, only your account information is accessible");


    }

    /**
     * Create partner mapping for customer.
     *
     * @param partnerMapping the new partner mapping in Request Body
     * @param customerId     the customer id
     * @return the new partner mapping location, otherwise FORBIDDEN if no authorization
     */
    @PostMapping(value = "/{customerId}/partnerMappings")
    public ResponseEntity createPartnerMapping(
            @RequestBody PartnerMapping partnerMapping,
            @PathVariable("customerId") long customerId,
            HttpServletRequest request) {
        if (isValidToken(request, customerId)) {
            Customer customer = new Customer();
            customer.setId(customerId);
            partnerMapping.setCustomer(customer);
            repository.save(partnerMapping);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(partnerMapping.getId()).toUri();
            return ResponseEntity.created(location).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You aren't admin, only your account information is accessible to change");

    }

    /**
     * Update partner mapping for customer.
     *
     * @param updatedPartnerMapping the updated partner mapping in Request Body
     * @param customerId            the customer id
     * @param id                    the partner mapping id
     * @return the NO_CONTENT if success, NOT_FOUND, otherwise FORBIDDEN if no authorization
     */
    @PutMapping("/{customerId}/partnerMappings/{id}")
    public ResponseEntity updatePartnerMapping(
            @RequestBody PartnerMapping updatedPartnerMapping,
            @PathVariable("customerId") long customerId,
            @PathVariable("id") long id,
            HttpServletRequest request) {
        if (isValidToken(request, customerId)) {
            List<PartnerMapping> all = repository.findByCustomerId(customerId);
            Optional<PartnerMapping> originalPartnerMapping = all.stream().filter(item -> item.getId() == id).findFirst();
            if (!originalPartnerMapping.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            updatedPartnerMapping.setCustomer(originalPartnerMapping.get().getCustomer());
            updatedPartnerMapping.setId(id);
            repository.save(updatedPartnerMapping);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You aren't admin, only your account information is accessible to change");
    }

    /**
     * Delete partner mapping for customer.
     *
     * @param customerId the customer id
     * @param id         the partner mapping id
     * @return the OK if success, otherwise FORBIDDEN if no authorization
     */
    @DeleteMapping("/{customerId}/partnerMappings/{id}")
    public ResponseEntity deletePartnerMapping(@PathVariable("customerId") long customerId,
                                               @PathVariable("id") long id,
                                               HttpServletRequest request) {
        if (isValidToken(request, customerId)) {
            List<PartnerMapping> all = repository.findByCustomerId(customerId);
            Optional<PartnerMapping> originalPartnerMapping = all.stream().filter(item -> item.getId() == id).findFirst();
            if (!originalPartnerMapping.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You aren't admin, only your account information is accessible to change");
    }
}
