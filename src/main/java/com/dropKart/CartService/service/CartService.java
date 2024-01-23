package com.dropKart.CartService.service;

import java.util.List;

import com.dropKart.commonDB.dto.CartDto;
import com.dropKart.commonDB.dto.CartItemProductDto;


public interface CartService {

	CartDto createCart(String customerId);
	CartDto findCartByCustomerId(String customerId );
	CartDto totalAmountOfCart(String cartId);
	CartDto totalAmountOfCart(List<CartItemProductDto> cartItemDtolist,String cartId);

}
