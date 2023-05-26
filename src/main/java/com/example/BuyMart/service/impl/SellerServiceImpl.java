package com.example.BuyMart.service.impl;

import com.example.BuyMart.dto.RequestDto.SellerRequestDto;
import com.example.BuyMart.dto.ResponseDto.SellerResponseDto;
import com.example.BuyMart.model.Seller;
import com.example.BuyMart.repository.SellerRepository;
import com.example.BuyMart.service.SellerService;
import com.example.BuyMart.transformer.SellerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    SellerRepository sellerRepository;

    @Override
    public SellerResponseDto addSeller(SellerRequestDto sellerRequestDto) {

        // dto -> entity
        Seller seller = SellerTransformer.SellerRequestDtoToSeller(sellerRequestDto);

        Seller savedSeller = sellerRepository.save(seller);

        // entity -> response dto
        return SellerTransformer.SellerToSellerResponseDto(savedSeller);


    }
}
