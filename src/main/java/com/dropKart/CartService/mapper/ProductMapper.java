package com.dropKart.CartService.mapper;

import java.util.Optional;

import org.mapstruct.Mapper;

import com.dropKart.commonDB.dto.ProductDto;
import com.dropKart.commonDB.model.Product;



@Mapper(componentModel = "spring")
public interface ProductMapper {

	ProductDto toProductDto(Product product);

	Product toProduct(ProductDto productDto);


}
