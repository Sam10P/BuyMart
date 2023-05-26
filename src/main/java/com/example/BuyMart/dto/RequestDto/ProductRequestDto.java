package com.example.BuyMart.dto.RequestDto;

import com.example.BuyMart.Enum.Category;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ProductRequestDto {

    String sellerEmailId;

    String name;

    Integer price;

    Category category;

    Integer quantity;

}
