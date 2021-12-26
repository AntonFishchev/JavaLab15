package com.vyatsu.task15.services;

import com.vyatsu.task15.entities.Product;
import com.vyatsu.task15.repositories.ProductRepository;
import com.vyatsu.task15.repositories.ProductRepositoryImol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {
    private ProductRepositoryImol productRepository;

    private ProductRepository productRepositoryR;

    @Autowired
    public void setProductRepository(ProductRepositoryImol productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepositoryR = productRepository;
    }

    public void deleteById(Integer id) {
        productRepositoryR.deleteById(id);
    }

//    public List<Product> getAllProducts(Integer Min, Integer Max, String title) {
//        List<Product> prod = productRepository.findAll();
//        if(Min != null) {
//            prod = prod.stream().filter(p -> p.getPrice() > Min).collect(Collectors.toList());
//        }
//        if(Max != null) {
//            prod = prod.stream().filter(p -> p.getPrice() < Max).collect(Collectors.toList());
//        }
//        if(title != null) {
//            prod = prod.stream().filter(p -> p.getTitle().contains(title)).collect(Collectors.toList());
//        }
//        return prod;
//    }

    public void add(String name, Integer price) {
       productRepository.add(name, price);
    }

    public void changeById(Integer id,String name, Integer price) {
        productRepository.change(id,name,price);
    }

    public Page<Product> listSpecByPage(Pageable pageable, List<Product> list) {

        Page<Product> pages1 = new PageImpl<Product>(list, pageable, list.size() );
        return pages1;
    }

}
