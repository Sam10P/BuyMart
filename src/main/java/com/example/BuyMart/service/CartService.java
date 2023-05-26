package com.example.BuyMart.service;

import com.example.BuyMart.dto.RequestDto.CheckOutCartRequestDto;
import com.example.BuyMart.dto.RequestDto.ItemRequestDto;
import com.example.BuyMart.dto.ResponseDto.CartResponseDto;
import com.example.BuyMart.dto.ResponseDto.OrderResponseDto;
import com.example.BuyMart.exception.CustomerNotFoundException;
import com.example.BuyMart.exception.EmptyCartException;
import com.example.BuyMart.exception.InsufficientQuantityException;
import com.example.BuyMart.exception.InvalidCardException;
import com.example.BuyMart.model.Item;

public interface CartService {
    public CartResponseDto addToCart(Item item, ItemRequestDto itemRequestDto);

    public OrderResponseDto checkOutCart(CheckOutCartRequestDto checkOutCartRequestDto) throws CustomerNotFoundException, InvalidCardException, EmptyCartException, InsufficientQuantityException;
}
