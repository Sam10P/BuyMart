package com.example.BuyMart.service.impl;

import com.example.BuyMart.dto.RequestDto.CheckOutCartRequestDto;
import com.example.BuyMart.dto.RequestDto.ItemRequestDto;
import com.example.BuyMart.dto.ResponseDto.CartResponseDto;
import com.example.BuyMart.dto.ResponseDto.OrderResponseDto;
import com.example.BuyMart.exception.CustomerNotFoundException;
import com.example.BuyMart.exception.EmptyCartException;
import com.example.BuyMart.exception.InsufficientQuantityException;
import com.example.BuyMart.exception.InvalidCardException;
import com.example.BuyMart.model.*;
import com.example.BuyMart.repository.*;
import com.example.BuyMart.service.CartService;
import com.example.BuyMart.service.OrderService;
import com.example.BuyMart.transformer.CartTransformer;
import com.example.BuyMart.transformer.ItemTransformer;
import com.example.BuyMart.transformer.OrderTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Override
    public CartResponseDto addToCart(Item item, ItemRequestDto itemRequestDto) {

        Customer customer = customerRepository.findByEmailId(itemRequestDto.getCustomerEmailId());
        Product product = productRepository.findById(itemRequestDto.getProductId()).get();

        Cart cart = customer.getCart();

        cart.setCartTotal(cart.getCartTotal()+ item.getRequiredQuantity()*product.getPrice());
        cart.getItems().add(item);
        item.setCart(cart);
        item.setProduct(product);

        Cart savedCart = cartRepository.save(cart);  // saves both cart and item
        Item savedItem = cart.getItems().get(cart.getItems().size()-1);

        product.getItems().add(savedItem);

        //prepare the response dto
        return CartTransformer.CartToCartResponseDto(savedCart);
    }

    @Override
    public OrderResponseDto checkOutCart(CheckOutCartRequestDto checkOutCartRequestDto) throws CustomerNotFoundException, InvalidCardException, EmptyCartException, InsufficientQuantityException {

        // validate customer
        Customer customer = customerRepository.findByEmailId(checkOutCartRequestDto.getEmailId());
        if(customer == null){
            throw new CustomerNotFoundException("Customer does not exist!!!");
        }

        // validate Card
        Card card = cardRepository.findByCardNo(checkOutCartRequestDto.getCardNo());
        Date date = new Date();
        if(card==null || card.getCvv() != checkOutCartRequestDto.getCvv() || date.after(card.getValidTill())){
            throw new InvalidCardException("Card is invalid!!");
        }

        Cart cart = customer.getCart();
        if(cart.getItems().size() == 0){
            throw new EmptyCartException("Cart is Empty!!");
        }

        try {
            OrderEntity order = orderService.placeOrder(cart, card);
            resetCart(cart);

            OrderEntity savedOrder = orderRepository.save(order);
            customer.getOrders().add(savedOrder);
            return OrderTransformer.OrderToOrderResponseDto(savedOrder);
        }
        catch (InsufficientQuantityException e){
            throw e;
        }

    }

    private void resetCart(Cart cart){

        cart.setCartTotal(0);
        for(Item item : cart.getItems()){
            item.setCart(null);
        }
        cart.setItems(new ArrayList<>());
    }


}
