package com.dropKart.CartService.mapper;

import org.mapstruct.Mapper;

import com.dropKart.commonDB.dto.CartDto;
import com.dropKart.commonDB.model.ShoppingCart;


@Mapper(componentModel="spring")
public interface CartMapper {
	
	CartDto toCartDto(ShoppingCart cart);
	ShoppingCart toCart(CartDto cartDto);
	

}
