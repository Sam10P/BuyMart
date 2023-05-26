package com.example.BuyMart.service;

import com.example.BuyMart.Enum.Category;
import com.example.BuyMart.dto.RequestDto.ProductRequestDto;
import com.example.BuyMart.dto.ResponseDto.ProductResponseDto;
import com.example.BuyMart.exception.SellerNotFoundException;

import java.util.List;

public interface ProductService {

    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) throws SellerNotFoundException;

    public List<ProductResponseDto> getAllProductsByCategoryAndPrice(Category category, int price);
}
