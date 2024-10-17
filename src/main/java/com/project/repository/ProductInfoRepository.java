package com.project.repository;

import com.project.table.ProductInfo;
import com.project.table.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductInfoRepository extends JpaRepository<ProductInfo,Long> {
//    ProductInfo findByProductName(String productname);

    Optional<ProductInfo> findByProductName(String productname);

    long countByProductName(String ProductName);
}
