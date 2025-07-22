package com.colvir.delivery.repository;

import com.colvir.delivery.model.Customer;
import com.colvir.delivery.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    @Query(value = """
        select c.id, c.name, c.address, c.phone_number
        from customers c
        where c.name = :name
    """, nativeQuery = true)
    Optional<Customer> findByName(@Param("name") String name);

    @Query(value = """
        select c.id, c.name, c.address, c.phone_number
        from customers c
        where c.id = :id
    """, nativeQuery = true)
    Optional<Customer> findById(@Param("id") Long id);
}
