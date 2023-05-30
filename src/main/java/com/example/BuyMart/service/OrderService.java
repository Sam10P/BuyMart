package com.example.BuyMart.service;

import com.example.BuyMart.dto.RequestDto.OrderRequestDto;
import com.example.BuyMart.dto.ResponseDto.OrderResponseDto;
import com.example.BuyMart.exception.CustomerNotFoundException;
import com.example.BuyMart.exception.InsufficientQuantityException;
import com.example.BuyMart.exception.InvalidCardException;
import com.example.BuyMart.exception.ProductNotFoundException;
import com.example.BuyMart.model.Card;
import com.example.BuyMart.model.Cart;
import com.example.BuyMart.model.OrderEntity;
import jakarta.persistence.criteria.Order;

import java.util.List;

public interface OrderService {

    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) throws CustomerNotFoundException, ProductNotFoundException, InsufficientQuantityException, InvalidCardException;

    public OrderEntity placeOrder(Cart cart, Card card) throws InsufficientQuantityException;

    public List<OrderResponseDto> top5OrderWithHighestValue();

    public List<OrderResponseDto> allOrdersOfACustomer(String emailId) throws CustomerNotFoundException;

    public List<OrderResponseDto> top5HighestValueOrdersOfACustomer(String emailId) throws CustomerNotFoundException;

    public List<OrderResponseDto> top5RecentOrdersOfACustomer(String emailId) throws CustomerNotFoundException;
}
