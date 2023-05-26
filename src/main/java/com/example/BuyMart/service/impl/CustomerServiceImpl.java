package com.example.BuyMart.service.impl;

import com.example.BuyMart.dto.RequestDto.CustomerRequestDto;
import com.example.BuyMart.dto.ResponseDto.CustomerResponseDto;
import com.example.BuyMart.model.Cart;
import com.example.BuyMart.model.Customer;
import com.example.BuyMart.repository.CustomerRepository;
import com.example.BuyMart.service.CustomerService;
import com.example.BuyMart.transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public CustomerResponseDto addCustomer(CustomerRequestDto customerRequestDto) {

        // dto -> entity
        Customer customer = CustomerTransformer.CustomerRequestDtoToCustomer(customerRequestDto);
        Cart cart = Cart.builder()
                .cartTotal(0)
                .customer(customer)
                .build();

        customer.setCart(cart);

        Customer savedCustomer = customerRepository.save(customer);  // save both customer and cart

        // prepare response Dto
        return CustomerTransformer.CustomerToCustomerResponseDto(savedCustomer);
    }
}
