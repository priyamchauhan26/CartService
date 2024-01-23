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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.dropKart.CartService.service.CartItemService;
import com.dropKart.CartService.service.CartService;
import com.dropKart.commonDB.dto.CartDto;
import com.dropKart.commonDB.dto.CartItemProductDto;
import com.dropKart.commonDB.dto.MessageDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cart")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CartController {
	
	private final CartService cartService;
	private final CartItemService cartItemSevice;
	
	@PostMapping("createCartforCustomer")
	public ResponseEntity<MessageDto> createCartforCustomer(@RequestParam("customerId") String CustomerId) {
		MessageDto messagedto=new MessageDto();
		CartDto cartdto=new CartDto();
		if(CustomerId==null) {
			messagedto.setHttpstatus(HttpStatus.BAD_REQUEST);
			messagedto.setStatus(404);
			messagedto.setMessage("Input Filed is Null");
			return ResponseEntity.status(messagedto.getHttpstatus()).body(messagedto);
		}
		else {
			cartdto=cartService.createCart(CustomerId);
			if(cartdto !=null) {
				messagedto.setHttpstatus(HttpStatus.OK);
				messagedto.setStatus(200);
				messagedto.setMessage("Cart Successfully Created");
				messagedto.setData(cartdto);
				return ResponseEntity.status(messagedto.getHttpstatus()).body(messagedto);
			}
			else {
				messagedto.setHttpstatus(HttpStatus.BAD_REQUEST);
				messagedto.setStatus(404);
				messagedto.setMessage("SomeThing Went Worng");
				return ResponseEntity.status(messagedto.getHttpstatus()).body(messagedto);
			}
			
		}
		
	}
	
	@PostMapping("updateCart")
	public ResponseEntity <MessageDto> updateCart(@RequestBody CartDto cartDto){
		return null;
	}
	
	@PostMapping("totalAmountOfCart")
	public ResponseEntity <MessageDto> totalAmount(@RequestParam ("cartId") String cartId){
		
		MessageDto messagedto=new MessageDto();
		List<CartItemProductDto> cartItemDtolist = new ArrayList<>();
		CartDto cartdto=new CartDto();
		if(cartId==null) {
			messagedto.setHttpstatus(HttpStatus.BAD_REQUEST);
			messagedto.setStatus(404);
			messagedto.setMessage("Input Filed is Null");
			return ResponseEntity.status(messagedto.getHttpstatus()).body(messagedto);
		}
		else {
			cartItemDtolist = cartItemSevice.findProductByCartId(cartId);
			if(cartItemDtolist.size()>0) {
				cartdto=cartService.totalAmountOfCart(cartItemDtolist,cartId);
			}
			
			if(cartdto !=null) {
				messagedto.setHttpstatus(HttpStatus.OK);
				messagedto.setStatus(200);
				messagedto.setMessage("Total Amount of Cart ");
				messagedto.setData(cartdto);
				return ResponseEntity.status(messagedto.getHttpstatus()).body(messagedto);
			}
			else {
				messagedto.setHttpstatus(HttpStatus.BAD_REQUEST);
				messagedto.setStatus(404);
				messagedto.setMessage("SomeThing Went Worng");
				return ResponseEntity.status(messagedto.getHttpstatus()).body(messagedto);
			}
			
		}
		
	}

}
