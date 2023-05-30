package com.example.BuyMart.service.impl;

import com.example.BuyMart.Enum.CardType;
import com.example.BuyMart.dto.RequestDto.CardRequestDto;
import com.example.BuyMart.dto.RequestDto.CustomerRequestDto;
import com.example.BuyMart.dto.ResponseDto.CardResponseDto;
import com.example.BuyMart.exception.CustomerNotFoundException;
import com.example.BuyMart.model.Card;
import com.example.BuyMart.model.Customer;
import com.example.BuyMart.repository.CardRepository;
import com.example.BuyMart.repository.CustomerRepository;
import com.example.BuyMart.service.CardService;
import com.example.BuyMart.transformer.CardTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CardRepository cardRepository;

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

    @Override
    public String maximumCardsOfACardType() {

//        int visaCard = cardRepository.cardsOfACardType(CardType.VISA);
//        int masterCard = cardRepository.cardsOfACardType(CardType.MASTERCARD);
//        int rupayCard = cardRepository.cardsOfACardType(CardType.RUPAY);
//
//        if(visaCard >= masterCard && visaCard >= rupayCard){
//            return "VISA";
//        }
//        else if(masterCard >= visaCard && masterCard >= rupayCard){
//            return "MASTERCARD";
//        }
//        else{
//            return "RUPAY";
//        }

        List<Card> cards = cardRepository.findAll();

        int visaCard = 0;
        int masterCard = 0;
        int rupayCard = 0;

        for(Card card : cards){
            if(card.getCardType() == CardType.VISA){
                visaCard++;
            }
            else if (card.getCardType() == CardType.MASTERCARD) {
                masterCard++;
            }
            else {
                rupayCard++;
            }
        }

        if(visaCard >= masterCard && visaCard >= rupayCard){
            return "VISA";
        }
        else if(masterCard >= visaCard && masterCard >= rupayCard){
            return "MASTERCARD";
        }
        else{
            return "RUPAY";
        }
    }

    @Override
    public String minimumCardsOfACardType() {

        List<Card> cards = cardRepository.findAll();

        int visaCard = 0;
        int masterCard = 0;
        int rupayCard = 0;

        for(Card card : cards){
            if(card.getCardType() == CardType.VISA){
                visaCard++;
            }
            else if (card.getCardType() == CardType.MASTERCARD) {
                masterCard++;
            }
            else {
                rupayCard++;
            }
        }

        if(visaCard <= masterCard && visaCard <= rupayCard){
            return "VISA";
        }
        else if(masterCard <= visaCard && masterCard <= rupayCard){
            return "MASTERCARD";
        }
        else{
            return "RUPAY";
        }
    }
}
