package com.example.BuyMart.service.impl;

import com.example.BuyMart.dto.RequestDto.CardRequestDto;
import com.example.BuyMart.dto.RequestDto.CustomerRequestDto;
import com.example.BuyMart.dto.ResponseDto.CardResponseDto;
import com.example.BuyMart.exception.CustomerNotFoundException;
import com.example.BuyMart.model.Card;
import com.example.BuyMart.model.Customer;
import com.example.BuyMart.repository.CustomerRepository;
import com.example.BuyMart.service.CardService;
import com.example.BuyMart.transformer.CardTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public CardResponseDto addCard(CardRequestDto cardRequestDto) throws CustomerNotFoundException {

        Customer customer = customerRepository.findByEmailId(cardRequestDto.getEmailId());
        if(customer==null){
            throw new CustomerNotFoundException("Invalid Email ID!!!");
        }

        // dto -> entity
        Card card = CardTransformer.CardRequestDtoToCard(cardRequestDto);
        card.setCustomer(customer);

        // add the card to customer's card list
        customer.getCards().add(card);
        // save the card and customer
        Customer savedCustomer = customerRepository.save(customer);

        // preparing the response Dto
        return CardTransformer.CardToCardResponseDto(card);
    }
}
