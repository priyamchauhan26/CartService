package com.dropKart.CartService.serviceImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.dropKart.CartService.mapper.CartItemMapper;
import com.dropKart.CartService.mapper.CartMapper;

import com.dropKart.CartService.service.CartService;
import com.dropKart.commonDB.dto.CartDto;
import com.dropKart.commonDB.dto.CartItemProductDto;
import com.dropKart.commonDB.dto.ProductDto;
import com.dropKart.commonDB.model.CartStatus;
import com.dropKart.commonDB.model.ShoppingCart;
import com.dropKart.commonDB.repo.CartItemRepo;
import com.dropKart.commonDB.repo.CartRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

	private final CartRepo cartRepo;
	private final CartMapper cartMapper;
	
	private final CartItemRepo cartItemRepo;
	private final CartItemMapper cartItemMapper;
	
	
	
	@Override
	public CartDto createCart(String customerId) {
		CartDto cartdto=new CartDto();
		ShoppingCart cart=new ShoppingCart();
		try {
			cartdto=findCartByCustomerId(customerId);
			if(cartdto==null) {
				ShoppingCart newcart=new ShoppingCart(0, 0.0,customerId, 0.0,customerId, LocalDateTime.now(),null, customerId,CartStatus.ACTIVE,0.0,0.0,0.0);
				cart=cartRepo.save(newcart);
				cartdto=cartMapper.toCartDto(newcart);
				return cartdto;
				
			}
			else {
				return cartdto;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	

	@Override
	public CartDto findCartByCustomerId(String customerId) {
		CartDto cartdto=new CartDto();
		ShoppingCart cart=new ShoppingCart();
		try {
			if(customerId==null) {
				return null;
			}
			else {
				cart=cartRepo.findByCustomerId(customerId);
				if(cart==null) {
					return null;
				}
				else {
					cartdto=cartMapper.toCartDto(cart);
					return cartdto;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}




	@Override
	public CartDto totalAmountOfCart(String cartId) {
		
		return null;
	}




	@Override
	public CartDto totalAmountOfCart(List<CartItemProductDto> cartItemDtolist,String cartId) {
		List<ProductDto> productDtolist=new ArrayList<>();
		ProductDto productDto =new ProductDto();
		CartDto cartdto=new CartDto();
		double totalAmount=0.0;
		ShoppingCart cart=new ShoppingCart();
		
		for(CartItemProductDto cartItemProductDto:cartItemDtolist) {
			productDto =new ProductDto();
			productDto=cartItemProductDto.getProductDto();
			if(productDto.getPrice()!=null) {
				totalAmount=totalAmount+(Double.valueOf(productDto.getPrice())*cartItemProductDto.getQuantity());
				}
			productDtolist.add(productDto);
			}
		
		if(totalAmount>0) {
			 BigDecimal roundedValue = new BigDecimal(totalAmount).setScale(2,RoundingMode.HALF_UP);
		        double result = roundedValue.doubleValue();
			
			
			int i=cartRepo.savecartd(String.valueOf(result),cartId);
			if(i==1) {
				cart=cartRepo.findByCartId(Long.valueOf(cartId));
			cartdto=cartMapper.toCartDto(cart);
			return cartdto;
			}
			return null;
		}
		
		
		return null;
	}

}
