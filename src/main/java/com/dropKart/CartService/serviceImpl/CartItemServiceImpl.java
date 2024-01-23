package com.dropKart.CartService.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dropKart.CartService.mapper.CartItemMapper;
import com.dropKart.CartService.mapper.ProductMapper;
import com.dropKart.CartService.service.CartItemService;
import com.dropKart.commonDB.dto.CartItemDto;
import com.dropKart.commonDB.dto.CartItemProductDto;
import com.dropKart.commonDB.dto.ProductDto;
import com.dropKart.commonDB.model.CartItem;
import com.dropKart.commonDB.model.Product;
import com.dropKart.commonDB.repo.CartItemRepo;
import com.dropKart.commonDB.repo.ProductRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

	private final CartItemRepo cartItemRepo;
	private final CartItemMapper cartItemapper;
	private final ProductRepo productRepo;
    private final ProductMapper productMapper;

	@Override
	public CartItemDto addProductItem(CartItemDto cartItemDto) {
		CartItemDto cartItemdto = new CartItemDto();
		CartItem alreadycartItem = new CartItem();
		CartItem newCartItem = new CartItem();
		try {
			alreadycartItem = cartItemRepo.findCartItemByCartIdAndProductIdAndSize(cartItemDto.getCartId(),
					cartItemDto.getProductId(), cartItemDto.getSize());
			if (alreadycartItem != null) {
				cartItemDto.setCartItemId(alreadycartItem.getCartItemId());
				int oldquantity = alreadycartItem.getQuantity();
				int newquantity = oldquantity + 1;
				cartItemdto = updateProductItemQuantity(cartItemDto, newquantity);
				if (cartItemdto != null) {
					return cartItemdto;
				} else {
					return null;
				}
			} else {

				newCartItem = cartItemapper.toCartItem(cartItemDto);
				newCartItem = cartItemRepo.save(newCartItem);
				if (newCartItem != null) {
					cartItemdto = cartItemapper.toCartItemDto(newCartItem);
					return cartItemdto;
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public CartItemDto updateProductItemQuantity(CartItemDto cartItemDto, int quantity) {
		CartItemDto cartItemdto = new CartItemDto();
		CartItem cartItem = new CartItem();
		int updatequantitystatus = 0;
		try {
			updatequantitystatus = cartItemRepo.updateProductItemQuantity(quantity, cartItemDto.getCartId(),
					cartItemDto.getCartItemId(), cartItemDto.getProductId());
			if (updatequantitystatus == 1) {
				cartItem = cartItemRepo.findById(cartItemDto.getCartItemId()).get();
				cartItemdto = cartItemapper.toCartItemDto(cartItem);
				return cartItemdto;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<CartItemProductDto> findProductByCartId(String cartId) {
		List<CartItem>cartItemList=new ArrayList<>();
		ProductDto productDto=new ProductDto();
		Product product=new Product();
		CartItemProductDto cartItemProductDto=new CartItemProductDto();
		List<CartItemProductDto> cartItemProductDtoList=new ArrayList<>();
		CartItemDto cartItemDto=new CartItemDto();
		
		try {
			cartItemList=cartItemRepo.findByCartId(cartId);
			if(cartItemList.size()>0) {
				for (CartItem cartItem : cartItemList) {
					cartItemDto = new CartItemDto();
					cartItemProductDto = new CartItemProductDto();
					cartItemDto = cartItemapper.toCartItemDto(cartItem);
					 product = productRepo.findByProductId(Long.valueOf(cartItemDto.getProductId()));
					if (product != null) {
						productDto=new ProductDto();
						productDto = productMapper.toProductDto(product);
						cartItemProductDto.setCartId(cartItemDto.getCartId());
						cartItemProductDto.setCartItemId(cartItemDto.getCartItemId());
						cartItemProductDto.setProductDto(productDto);
						cartItemProductDto.setSize(cartItemDto.getSize());
						cartItemProductDto.setQuantity(cartItemDto.getQuantity());
						
						cartItemProductDtoList.add(cartItemProductDto);
					}
					else {
						return null;
					}

				}
				
				return cartItemProductDtoList;
			}
			else {
				return null;
			}
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
