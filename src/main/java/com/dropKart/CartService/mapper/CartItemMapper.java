package com.dropKart.CartService.mapper;

import org.mapstruct.Mapper;

import com.dropKart.commonDB.dto.CartItemDto;
import com.dropKart.commonDB.model.CartItem;



@Mapper(componentModel="spring")
public interface CartItemMapper {

		CartItemDto toCartItemDto(CartItem CartItem);
		CartItem toCartItem(CartItemDto cartItemDto);
}
