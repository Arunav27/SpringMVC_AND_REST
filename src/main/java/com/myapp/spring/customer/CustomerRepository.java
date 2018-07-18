package com.myapp.spring.customer;

import org.springframework.data.jpa.repository.*;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {

}
