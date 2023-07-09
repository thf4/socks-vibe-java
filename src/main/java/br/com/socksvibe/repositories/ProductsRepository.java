package br.com.socksvibe.repositories;

import br.com.socksvibe.models.ProductsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductsRepository extends JpaRepository<ProductsModel, UUID> {
    boolean existsProductBySku(int sku);
    boolean existsProductByProductName(String productName);
}
