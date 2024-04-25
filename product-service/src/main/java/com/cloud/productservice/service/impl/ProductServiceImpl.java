package com.cloud.productservice.service.impl;

import com.cloud.productservice.dto.ProductRequest;
import com.cloud.productservice.dto.ProductResponse;
import com.cloud.productservice.entity.Product;
import com.cloud.productservice.exception.ResourceNotFoundException;
import com.cloud.productservice.repository.ProductRepository;
import com.cloud.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public void createProduct(ProductRequest productRequest) {
        log.info("Adding Product...");
        Product product = Product.builder()
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .description(productRequest.getDescription())
                .skuCode(productRequest.getSkuCode())
                .build();
        productRepository.save(product);
        log.info("Product Created for {}", product.getId());
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(this::mapToProductResponse).toList();
    }

    @Override
    public void reduceQuantity(String skuCode, Integer quantity) {
        Product product = productRepository.findBySkuCodeIgnoreCase(skuCode).orElseThrow(() ->
                new ResourceNotFoundException("Product not found", "PRODUCT_NOT_FOUND"));
        if (product.getQuantity() < quantity){
            throw new ResourceNotFoundException("Product does not have the given quantity", "INSUFFICIENT_QUANTITY");
        }
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        log.info("Product quantity updated...");
    }

    @Override
    public void deleteProduct(long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()){
            throw new ResourceNotFoundException("Product with id not found", "PRODUCT_NOT_FOUND");
        }
        productRepository.deleteById(id);
    }

    @Override
    public ProductResponse getProductBySkuCode(String  skuCode) {
        log.info("Getting product for {}", skuCode);
        Product product = productRepository.findBySkuCodeIgnoreCase(skuCode).orElseThrow(() ->
                new ResourceNotFoundException("product with skuCode (" + skuCode + ") not found", "PRODUCT_NOT_FOUND"));

        ProductResponse productResponse = new ProductResponse();
        BeanUtils.copyProperties(product, productResponse);
        return productResponse;
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .description(product.getDescription())
                .price(product.getPrice())
                .skuCode(product.getSkuCode())
                .quantity(product.getQuantity())
                .build();
    }
}
