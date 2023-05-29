package com.example.BuyMart.service;

import com.example.BuyMart.dto.RequestDto.CustomerRequestDto;
import com.example.BuyMart.dto.ResponseDto.CustomerResponseDto;

import java.util.List;

public interface CustomerService {

    public CustomerResponseDto addCustomer(CustomerRequestDto customerRequestDto);

    public List<CustomerResponseDto> femaleCustomer();

    public List<CustomerResponseDto> maleCustomer();

    public List<CustomerResponseDto> customersOrderedKOrders(int k);
}
