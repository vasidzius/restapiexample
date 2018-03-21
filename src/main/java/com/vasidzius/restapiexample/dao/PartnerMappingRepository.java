package com.vasidzius.restapiexample.dao;

import com.vasidzius.restapiexample.model.PartnerMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerMappingRepository extends JpaRepository<PartnerMapping, Long> {

    @Query("SELECT t FROM PartnerMapping t WHERE t.customer.id = ?1")
    List<PartnerMapping> findByCustomerId(Long customerId);

}
