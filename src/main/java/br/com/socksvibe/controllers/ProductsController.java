package br.com.socksvibe.controllers;

import br.com.socksvibe.dtos.ProductsDto;
import br.com.socksvibe.exceptions.ResponseProductsException;
import br.com.socksvibe.models.ProductsModel;
import br.com.socksvibe.services.ProductsService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/products")
public class ProductsController {

    final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @PostMapping(value = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> saveProduct(@RequestBody @Valid ProductsDto productsDto) throws RuntimeException {
        ProductsModel productsModel = new ProductsModel();
        if(productsService.existsProductBySku(productsDto.getSku())) {
            throw new ResponseProductsException("This SKU already exists in database!");
        }

        if(productsService.existsProductByProductName(productsDto.getProductName())) {
            throw new ResponseProductsException("This ProductName already exists in database!");
        }

        BeanUtils.copyProperties(productsDto, productsModel);
        productsModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(productsService.save(productsModel));
    }

}
