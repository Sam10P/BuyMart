package com.example.BuyMart.transformer;

import com.example.BuyMart.dto.RequestDto.ItemRequestDto;
import com.example.BuyMart.dto.ResponseDto.ItemResponseDto;
import com.example.BuyMart.model.Customer;
import com.example.BuyMart.model.Item;
import com.example.BuyMart.model.Product;

public class ItemTransformer {

    public static Item ItemRequestDtoToItem(int quantity){

        return Item.builder()
                .requiredQuantity(quantity)
                .build();
    }

    public static ItemResponseDto ItemToItemResponseDto(Item item) {

        return ItemResponseDto.builder()
                .quantityAdded(item.getRequiredQuantity())
                .productName(item.getProduct().getName())
                .price(item.getProduct().getPrice())
                .build();
    }
}
