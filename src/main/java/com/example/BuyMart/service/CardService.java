package com.example.BuyMart.service;

import com.example.BuyMart.Enum.CardType;
import com.example.BuyMart.dto.RequestDto.CardRequestDto;
import com.example.BuyMart.dto.RequestDto.CustomerRequestDto;
import com.example.BuyMart.dto.ResponseDto.CardResponseDto;
import com.example.BuyMart.dto.ResponseDto.CustomerResponseDto;
import com.example.BuyMart.exception.CustomerNotFoundException;

public interface CardService {

    public CardResponseDto addCard(CardRequestDto cardRequestDto) throws CustomerNotFoundException;

    public String maximumCardsOfACardType();

    public String minimumCardsOfACardType();
}
