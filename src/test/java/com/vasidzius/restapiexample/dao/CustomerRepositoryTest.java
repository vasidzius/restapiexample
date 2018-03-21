package com.vasidzius.restapiexample.dao;

import com.vasidzius.restapiexample.model.Customer;
import com.vasidzius.restapiexample.model.PartnerMapping;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    CustomerRepository repository;

    @Test
    public void findAll(){
        //when
        List<Customer> all = repository.findAll();

        //then
        assertEquals(4, all.size());
    }

}