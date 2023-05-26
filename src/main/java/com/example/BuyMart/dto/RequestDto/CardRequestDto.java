package com.example.BuyMart.dto.RequestDto;

import com.example.BuyMart.Enum.CardType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CardRequestDto {

    String emailId;

    String cardNo;

    int cvv;

    CardType cardType;

    Date validTill;

}
