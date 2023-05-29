package com.example.BuyMart.service.impl;

import com.example.BuyMart.Enum.Category;
import com.example.BuyMart.dto.RequestDto.ProductRequestDto;
import com.example.BuyMart.dto.ResponseDto.ProductResponseDto;
import com.example.BuyMart.exception.ProductNotFoundException;
import com.example.BuyMart.exception.SellerNotFoundException;
import com.example.BuyMart.model.Product;
import com.example.BuyMart.model.Seller;
import com.example.BuyMart.repository.ProductRepository;
import com.example.BuyMart.repository.SellerRepository;
import com.example.BuyMart.service.ProductService;
import com.example.BuyMart.transformer.ProductTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) throws SellerNotFoundException {

        Seller seller = sellerRepository.findByEmailId(productRequestDto.getSellerEmailId());
        if(seller == null){
            throw new SellerNotFoundException("EmailId is not registered");
        }

        // dto -> entity
        Product product = ProductTransformer.ProductRequestDtoToProduct(productRequestDto);
        product.setSeller(seller);
        seller.getProducts().add(product);

        // save product
        Seller savedSeller = sellerRepository.save(seller); // save both seller and product because seller is parent table
        Product savedProduct = savedSeller.getProducts().get(savedSeller.getProducts().size()-1);

        // entity -> response dto
        return ProductTransformer.ProductToProductResponseDto(savedProduct);



    }

    @Override
    public List<ProductResponseDto> getAllProductsByCategoryAndPrice(Category category, int price) {

        List<Product> products = productRepository.findByCategoryAndPrice(category, price);

        // prepare a list of Dtos
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for(Product product : products){
            productResponseDtos.add(ProductTransformer.ProductToProductResponseDto(product));
        }

        return productResponseDtos;
    }

    @Override
    public void changeCategoryOfProduct(Category category, int productId) throws ProductNotFoundException {

        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(!optionalProduct.isPresent()){
            throw new ProductNotFoundException("product does not exist!!");
        }
//        // category id
//        // 1 : FASHION
//        // 2 : SPORTS
//        // 3 : FOOD
//        // 4 : ELECTRONICS
//        // 5 : COSMETICS
//        Product product = optionalProduct.get();
//        Category newCategory = Category.FASHION;
//        if(category == 2){
//            newCategory = Category.SPORTS;
//        }
//        else if(category == 3){
//            newCategory = Category.FOOD;
//        } else if (category == 4) {
//            newCategory = Category.ELECTRONICS;
//        } else if (category == 5) {
//            newCategory = Category.COSMETICS;
//        }
        Product product = optionalProduct.get();
        product.setCategory(category);
        productRepository.save(product);
    }
}
