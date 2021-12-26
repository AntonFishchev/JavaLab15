package com.vyatsu.task15.repositories;

import com.vyatsu.task15.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

//    @Query("")
//    public void Add(Product product);

    Page<Product> findAll(Pageable pageable);

    Product findById(Product product);

    void deleteById(Integer id);
}
