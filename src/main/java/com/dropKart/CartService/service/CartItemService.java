package com.dropKart.CartService.service;

import java.util.List;

import com.dropKart.commonDB.dto.CartItemDto;
import com.dropKart.commonDB.dto.CartItemProductDto;


public interface CartItemService {

	CartItemDto addProductItem(CartItemDto cartItemDto);
	CartItemDto updateProductItemQuantity(CartItemDto cartitemDto,int newquantity);
	List<CartItemProductDto> findProductByCartId(String cartId);

}
