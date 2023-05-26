package com.example.BuyMart.service;

import com.example.BuyMart.dto.RequestDto.SellerRequestDto;
import com.example.BuyMart.dto.ResponseDto.SellerResponseDto;

public interface SellerService {

    public SellerResponseDto addSeller(SellerRequestDto sellerRequestDto);
}
