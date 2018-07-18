package com.myapp.spring.catalog;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CatalogItemRepository extends JpaRepository<CatalogItemEntity, Integer> {
   
}
