package br.com.socksvibe.services;

import br.com.socksvibe.models.ProductsModel;
import br.com.socksvibe.repositories.ProductsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductsService {

    final ProductsRepository productsRepository;
    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Transactional
    public ProductsModel save(ProductsModel productsModel) {
        return productsRepository.save(productsModel);
    }

    public List<ProductsModel> findAll() { return productsRepository.findAll(); }
    public Optional<ProductsModel> getProduct(UUID id) { return productsRepository.findById(id); }
    @Transactional
    public void delete(ProductsModel product) {
       productsRepository.delete(product);
    }

    public boolean existsProductBySku(int sku) {
        return this.productsRepository.existsProductBySku(sku);
    }

    public boolean existsProductByProductName(String productName) {
        return this.productsRepository.existsProductByProductName(productName);
    }
}
