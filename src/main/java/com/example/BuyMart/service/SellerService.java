package com.example.BuyMart.service;

import com.example.BuyMart.Enum.Category;
import com.example.BuyMart.dto.RequestDto.SellerRequestDto;
import com.example.BuyMart.dto.ResponseDto.SellerResponseDto;
import com.example.BuyMart.exception.SellerNotFoundException;

import java.util.HashMap;
import java.util.List;

public interface SellerService {

    public SellerResponseDto addSeller(SellerRequestDto sellerRequestDto);

    public SellerResponseDto updateInfoByEmail(String emailId, String name) throws SellerNotFoundException;

    public List<SellerResponseDto> sellerSellProductOfCategory(Category category);

    public HashMap<String, Integer> allProductsSoldByASeller(String emailId) throws SellerNotFoundException;

    public SellerResponseDto sellerWithHighestNoOfProducts();

    public SellerResponseDto sellerWithMinimumNoOfProducts();

    public SellerResponseDto sellerSellingCostliestProduct();

    public SellerResponseDto sellerSellingCheapestProduct();

}
