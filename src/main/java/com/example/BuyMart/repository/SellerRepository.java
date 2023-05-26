package com.example.BuyMart.repository;

import com.example.BuyMart.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Integer> {

//    @Query(value = "SELECT * FROM seller WHERE email_id = :emailId", nativeQuery = true)
    Seller findByEmailId(String emailId);

}
