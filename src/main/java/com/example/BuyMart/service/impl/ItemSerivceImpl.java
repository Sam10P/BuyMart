package com.example.BuyMart.service.impl;

import com.example.BuyMart.dto.RequestDto.ItemRequestDto;
import com.example.BuyMart.exception.CustomerNotFoundException;
import com.example.BuyMart.exception.InsufficientQuantityException;
import com.example.BuyMart.exception.OutOfStockException;
import com.example.BuyMart.exception.ProductNotFoundException;
import com.example.BuyMart.model.Customer;
import com.example.BuyMart.model.Item;
import com.example.BuyMart.model.Product;
import com.example.BuyMart.repository.CustomerRepository;
import com.example.BuyMart.repository.ProductRepository;
import com.example.BuyMart.service.ItemService;
import com.example.BuyMart.transformer.ItemTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemSerivceImpl implements ItemService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomerRepository customerRepository;
    @Override
    public Item createItem(ItemRequestDto itemRequestDto) throws ProductNotFoundException, CustomerNotFoundException, InsufficientQuantityException, OutOfStockException {

        Optional<Product> productOptional = productRepository.findById(itemRequestDto.getProductId());
        if(productOptional.isEmpty()){
            throw new ProductNotFoundException("Product does not exist!!!");
        }

        Customer customer = customerRepository.findByEmailId(itemRequestDto.getCustomerEmailId());
        if(customer == null){
            throw new CustomerNotFoundException("Customer doesn't exist!!");
        }

        Product product = productOptional.get();

        if(product.getQuantity() == 0){
            throw new OutOfStockException("Product is Out Of Stock!!");
        }

        if(product.getQuantity() < itemRequestDto.getRequiredQuantity()){
            throw new InsufficientQuantityException("Sorry! The required quantity is not available.");
        }

        Item item = ItemTransformer.ItemRequestDtoToItem(itemRequestDto.getRequiredQuantity());

        return item;
    }
}
