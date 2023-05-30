package com.example.BuyMart.service.impl;

import com.example.BuyMart.Enum.ProductStatus;
import com.example.BuyMart.dto.RequestDto.OrderRequestDto;
import com.example.BuyMart.dto.ResponseDto.OrderResponseDto;
import com.example.BuyMart.exception.CustomerNotFoundException;
import com.example.BuyMart.exception.InsufficientQuantityException;
import com.example.BuyMart.exception.InvalidCardException;
import com.example.BuyMart.exception.ProductNotFoundException;
import com.example.BuyMart.model.*;
import com.example.BuyMart.repository.CardRepository;
import com.example.BuyMart.repository.CustomerRepository;
import com.example.BuyMart.repository.OrderRepository;
import com.example.BuyMart.repository.ProductRepository;
import com.example.BuyMart.service.OrderService;
import com.example.BuyMart.transformer.ItemTransformer;
import com.example.BuyMart.transformer.OrderTransformer;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    OrderRepository orderRepository;

    @Override
    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) throws CustomerNotFoundException, ProductNotFoundException, InsufficientQuantityException, InvalidCardException {

        Customer customer = customerRepository.findByEmailId(orderRequestDto.getEmailId());
        if (customer == null) {
            throw new CustomerNotFoundException("Customer does not exist!!");
        }

        Optional<Product> optionalProduct = productRepository.findById(orderRequestDto.getProductId());
        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException("Sorry!! Product does not exist.");
        }

        Product product = optionalProduct.get();

        // check quantity
        if(product.getQuantity() < orderRequestDto.getRequiredQuantity()){
            throw new InsufficientQuantityException("Required quantity not available.");
        }

        //card
        Card card = cardRepository.findByCardNo(orderRequestDto.getCardNo());
        Date date = new Date();
        if(card==null || card.getCvv() != orderRequestDto.getCvv() || date.after(card.getValidTill())){
            throw new InvalidCardException("Card is invalid!!");
        }

        // update the product quantity
        int newQuantity = product.getQuantity() - orderRequestDto.getRequiredQuantity();
        product.setQuantity(newQuantity);

        //update the product status
        if(newQuantity == 0){
            product.setProductStatus(ProductStatus.OUT_OF_STOCK);
        }

        // create the item
        Item item = ItemTransformer.ItemRequestDtoToItem(orderRequestDto.getRequiredQuantity());
        item.setProduct(product);

        OrderEntity orderEntity = OrderTransformer.OrderRequestDtoToOrder(item, customer);
        String maskedCard = generateMaskedCardNo(card);
        orderEntity.setCardUsed(maskedCard);
        orderEntity.getItems().add(item);
        item.setOrderEntity(orderEntity);

        OrderEntity savedOrder = orderRepository.save(orderEntity); // save both order and item

        customer.getOrders().add(savedOrder);
        product.getItems().add(savedOrder.getItems().get(0)); // only one item that's why get(0)

        // prepare response dto
        return OrderTransformer.OrderToOrderResponseDto(savedOrder);
    }

    public OrderEntity placeOrder(Cart cart, Card card) throws InsufficientQuantityException {

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderNo(String.valueOf(UUID.randomUUID()));
        orderEntity.setCardUsed(generateMaskedCardNo(card));

        int totalValue = 0;
        for(Item item : cart.getItems()){
            Product product = item.getProduct();
            if(item.getRequiredQuantity() > product.getQuantity()){
                throw new InsufficientQuantityException("Required quantity is not available.");
            }
            totalValue += item.getRequiredQuantity()*product.getPrice();
            int newQuantity = product.getQuantity() - item.getRequiredQuantity();
            product.setQuantity(newQuantity);
            if(newQuantity == 0){
                product.setProductStatus(ProductStatus.OUT_OF_STOCK);
            }

            // set order entity to item
            item.setOrderEntity(orderEntity);
        }

        orderEntity.setTotalValue(totalValue);
        orderEntity.setItems(cart.getItems());
        orderEntity.setCustomer(cart.getCustomer());

        return orderEntity;
    }

    @Override
    public List<OrderResponseDto> top5OrderWithHighestValue() {

        List<OrderEntity> orders = orderRepository.orderWithHighestValue();
        List<OrderResponseDto> orderResponseDtos = new ArrayList<>();

        for(OrderEntity order : orders){
            orderResponseDtos.add(OrderTransformer.OrderToOrderResponseDto(order));
            if(orderResponseDtos.size() == 5){
                break;
            }
        }

        return orderResponseDtos;
    }

    @Override
    public List<OrderResponseDto> allOrdersOfACustomer(String emailId) throws CustomerNotFoundException {

        Customer customer = customerRepository.findByEmailId(emailId);
        if(customer == null){
            throw new CustomerNotFoundException("Customer does not exist!!!");
        }

        List<OrderEntity> orders = orderRepository.findByCustomer(customer);
        List<OrderResponseDto> orderResponseDtos = new ArrayList<>();

        for(OrderEntity order : orders){
            orderResponseDtos.add(OrderTransformer.OrderToOrderResponseDto(order));
        }
        return orderResponseDtos;
    }

    @Override
    public List<OrderResponseDto> top5HighestValueOrdersOfACustomer(String emailId) throws CustomerNotFoundException {

        Customer customer = customerRepository.findByEmailId(emailId);
        if(customer == null){
            throw new CustomerNotFoundException("Customer does not exist!!!");
        }

        List<OrderEntity> orders = orderRepository.HighestValueOrder(customer.getId());
        List<OrderResponseDto> orderResponseDtos = new ArrayList<>();

        for(OrderEntity order : orders){
            orderResponseDtos.add(OrderTransformer.OrderToOrderResponseDto(order));
            if(orderResponseDtos.size() == 5){
                break;
            }
        }
        return orderResponseDtos;
    }

    @Override
    public List<OrderResponseDto> top5RecentOrdersOfACustomer(String emailId) throws CustomerNotFoundException {

        Customer customer = customerRepository.findByEmailId(emailId);
        if(customer == null){
            throw new CustomerNotFoundException("Customer does not exist!!!");
        }

        List<OrderEntity> orders = orderRepository.RecentOrder(customer.getId());
        List<OrderResponseDto> orderResponseDtos = new ArrayList<>();

        for(OrderEntity order : orders){
            orderResponseDtos.add(OrderTransformer.OrderToOrderResponseDto(order));
            if(orderResponseDtos.size() == 5){
                break;
            }
        }
        return orderResponseDtos;
    }

    private String generateMaskedCardNo(Card card){
        String cardNo = "";
        String originalCardNo = card.getCardNo();

        for(int i=0;i<originalCardNo.length()-4;i++){
            cardNo += "*";
        }

        cardNo += card.getCardNo().substring(originalCardNo.length()-4);
        return cardNo;
    }
}
