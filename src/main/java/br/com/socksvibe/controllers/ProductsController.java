package br.com.socksvibe.controllers;

import br.com.socksvibe.dtos.ProductsDto;
import br.com.socksvibe.exceptions.ResponseBadRequestException;
import br.com.socksvibe.exceptions.ResponseNotFoundException;
import br.com.socksvibe.models.ProductsModel;
import br.com.socksvibe.services.ProductsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

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
            throw new ResponseBadRequestException("This SKU already exists in database!");
        }

        if(productsService.existsProductByProductName(productsDto.getProductName())) {
            throw new ResponseBadRequestException("This ProductName already exists in database!");
        }

        BeanUtils.copyProperties(productsDto, productsModel);
        productsModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(productsService.save(productsModel));
    }

    @GetMapping(value = "find", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> findAllProduct() throws RuntimeException {
        return ResponseEntity.status(HttpStatus.OK).body(productsService.findAll());
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getProduct(@PathVariable(value = "id") UUID id) throws RuntimeException {
        return ResponseEntity.status(HttpStatus.OK).body(productsService.getProduct(id));
    }
    @DeleteMapping(value = "delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id) throws RuntimeException {
        Optional<ProductsModel> product = productsService.getProduct(id);
        if (product.isEmpty()) {
            throw new ResponseNotFoundException("Product doesn't exist in database!");
        }
        productsService.delete(product.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product has been deleted");
    }
}
