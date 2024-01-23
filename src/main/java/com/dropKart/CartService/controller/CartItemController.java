package com.dropKart.CartService.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dropKart.CartService.service.CartItemService;
import com.dropKart.CartService.service.CartService;
import com.dropKart.commonDB.dto.CartItemDto;
import com.dropKart.commonDB.dto.CartItemProductDto;
import com.dropKart.commonDB.dto.MessageDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cartItem")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CartItemController {

	private final CartItemService cartItemSevice;
	private final CartService cartService;

	@PostMapping("addProductItem")
	public ResponseEntity<MessageDto> addItem(@RequestBody CartItemDto cartItemDto) {
		MessageDto messagedto = new MessageDto();
		CartItemDto cartItemdto = new CartItemDto();

		try {
			if (cartItemDto == null) {
				messagedto.setHttpstatus(HttpStatus.BAD_REQUEST);
				messagedto.setStatus(403);
				messagedto.setMessage("No Item Added in the Cart");
				messagedto.setData(null);
				return ResponseEntity.status(messagedto.getHttpstatus()).body(messagedto);
			} else {
				cartItemdto = cartItemSevice.addProductItem(cartItemDto);
				if (cartItemdto != null) {
					messagedto.setHttpstatus(HttpStatus.OK);
					messagedto.setStatus(200);
					messagedto.setMessage("Product Added to Cart Succesfully");
					messagedto.setData(cartItemdto);
					return ResponseEntity.status(messagedto.getHttpstatus()).body(messagedto);
				} else {
					messagedto.setHttpstatus(HttpStatus.BAD_REQUEST);
					messagedto.setStatus(403);
					messagedto.setMessage("Something went worng Please add After Sometimes");
					messagedto.setData(null);
					return ResponseEntity.status(messagedto.getHttpstatus()).body(messagedto);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@PostMapping("findProductByCartId")
	public ResponseEntity<MessageDto> findProductByCartId(@RequestParam String cartId) {
		try {
			MessageDto messagedto = new MessageDto();
			List<CartItemProductDto> cartItemDtolist = new ArrayList<>();

			if (cartId == null || cartId.equals("")) {
				messagedto.setHttpstatus(HttpStatus.BAD_REQUEST);
				messagedto.setStatus(403);
				messagedto.setMessage("No Item Added in the Cart");
				messagedto.setData(null);
				return ResponseEntity.status(messagedto.getHttpstatus()).body(messagedto);
			} else {
				cartItemDtolist = cartItemSevice.findProductByCartId(cartId);
				if (cartItemDtolist != null) {
					messagedto.setHttpstatus(HttpStatus.OK);
					messagedto.setStatus(200);
					messagedto.setMessage("Product List");
					messagedto.setData(cartItemDtolist);
					return ResponseEntity.status(messagedto.getHttpstatus()).body(messagedto);
				} else {
					messagedto.setHttpstatus(HttpStatus.BAD_REQUEST);
					messagedto.setStatus(403);
					messagedto.setMessage("Something went worng Please add After Sometimes");
					messagedto.setData(null);
					return ResponseEntity.status(messagedto.getHttpstatus()).body(messagedto);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
