package com.example.BuyMart.service;

import com.example.BuyMart.dto.RequestDto.ItemRequestDto;
import com.example.BuyMart.exception.CustomerNotFoundException;
import com.example.BuyMart.exception.InsufficientQuantityException;
import com.example.BuyMart.exception.OutOfStockException;
import com.example.BuyMart.exception.ProductNotFoundException;
import com.example.BuyMart.model.Item;

public interface ItemService {

    public Item createItem(ItemRequestDto itemRequestDto) throws ProductNotFoundException, CustomerNotFoundException, InsufficientQuantityException, OutOfStockException;
}
