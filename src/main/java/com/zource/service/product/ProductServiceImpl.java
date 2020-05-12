package com.zource.service.product;

import com.zource.exceptions.ProductNotFoundException;
import com.zource.model.Product;
import com.zource.model.ProductImage;
import com.zource.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product getById(Long id) throws ProductNotFoundException {
        return this.productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("No product found for product Id = " + id));

    }

    @Override
    public Set<ProductImage> getProductImages() {
        Set<ProductImage> result = new HashSet<>();


        return result;
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findAll(Long categoryID, Long brandId, String name) {
        List<Product>  products = this.findAllProducts();

        if (name.length() > 0)
            products = products.stream().filter(p -> p.getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
        if (brandId != 0) {
            System.out.println("brandID: " + brandId);
            products = products.stream().filter(p -> p.getBrand().getId().equals(brandId)).collect(Collectors.toList());
        }
        return products;
    }

    @Override
    @Secured("ROLE_ADMIN")
    public Product updateProduct(Product product) {
        Optional< Product > p = this.productRepository.findById(product.getId());

        if (p.isPresent()) {
            Product productUpdate = p.get();
            productUpdate.setId(product.getId());
            productUpdate.setName(product.getName());
            productUpdate.setSku(product.getSku());
            productUpdate.setDescription(product.getDescription());
            productUpdate.setPrice(product.getPrice());
            productUpdate.setEnabled(product.isEnabled());
            productUpdate.setBrand(product.getBrand());
            productRepository.save(productUpdate);
            return productUpdate;
        } else {
            throw new ResourceNotFoundException("Record not found with id : " + product.getId());
        }
    }

    @Secured("ROLE_ADMIN")
    public void deleteProduct(Long id) {
        productRepository.findById(id).ifPresent(p -> productRepository.delete(p));
    }

}
