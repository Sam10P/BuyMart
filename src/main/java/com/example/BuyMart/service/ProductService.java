package com.example.BuyMart.service;

import com.example.BuyMart.Enum.Category;
import com.example.BuyMart.dto.RequestDto.ProductRequestDto;
import com.example.BuyMart.dto.ResponseDto.ProductResponseDto;
import com.example.BuyMart.exception.ProductNotFoundException;
import com.example.BuyMart.exception.SellerNotFoundException;

import java.util.List;

public interface ProductService {

    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) throws SellerNotFoundException;

    public List<ProductResponseDto> getAllProductsByCategoryAndPrice(Category category, int price);

    public void changeCategoryOfProduct(Category category, int productId) throws ProductNotFoundException;

    public List<ProductResponseDto> allProductsOfACategory(Category category);

    public List<ProductResponseDto> productsOfACategoryHavePriceGreaterThanK(Category category, int price);

    public List<ProductResponseDto> top5CheapestProduct();

    public List<ProductResponseDto> top5CostliestProduct();

    public List<ProductResponseDto> productsBySellerEmailId(String emailId) throws SellerNotFoundException;

    public List<ProductResponseDto> outOfStockProduct();
}
