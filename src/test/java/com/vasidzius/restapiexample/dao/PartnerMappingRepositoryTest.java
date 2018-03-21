package com.vasidzius.restapiexample.dao;

import com.vasidzius.restapiexample.model.PartnerMapping;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PartnerMappingRepositoryTest {

    @Autowired
    private
    PartnerMappingRepository repository;

    @Test
    public void findAll(){
        //when
        List<PartnerMapping> all = repository.findAll();

        //then
        assertEquals(3, all.size());
    }


}