package com.icet.shopeagle.service.product.impl;

import com.icet.shopeagle.model.Category;
import com.icet.shopeagle.model.Product;
import com.icet.shopeagle.repository.CategoryRepository;
import com.icet.shopeagle.repository.ProductRepository;
import com.icet.shopeagle.request.AddProductRequest;
import com.icet.shopeagle.request.UpdateProductRequest;
import com.icet.shopeagle.service.product.ProductService;
import com.icet.shopeagle.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    final ProductRepository repository;
    final CategoryRepository categoryRepository;
    @Override
    public Product addProduct(AddProductRequest product) {
        Category category= Optional.ofNullable(categoryRepository.findByName(product.getCategory().getName())).orElseGet(()->{
            Category newCategory=new Category(product.getCategory().getName());
            return categoryRepository.save(newCategory);

        });
        product.setCategory(category);
        return repository.save(createProduct(product,category));

    }

    private Product createProduct(AddProductRequest product, Category category){
        return new Product(
                product.getName(), product.getBrand(), product.getPrice(), product.getQuantity(), product.getDescription(), category
        );
    }
    @Override
    public Product updateProduct(UpdateProductRequest product) {
        return repository.findById(product.getId())
                .map(existingProduct->updateExistingProduct(existingProduct,product))
                .map(repository::save)
                .orElseThrow(()->new ProductNotFoundException("Product Not Found"));

    }
    private Product updateExistingProduct(Product existingProduct, UpdateProductRequest request){
        existingProduct.setBrand(request.getBrand());
        existingProduct.setName(request.getName());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setQuantity(request.getQuantity());
        existingProduct.setDescription(request.getDescription());
        Category category=categoryRepository.findByName(request.getCategory().getName());

        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public List<Product> getAll() {
        return repository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product Not Found Exception"));
    }

    @Override
    public void deleteProductById(Long id) {
        repository.findById(id).ifPresentOrElse(repository::delete, () -> {
            throw new ProductNotFoundException("Product Not Found");
        });

    }

    @Override
    public List<Product> getProductByCategory(String category) {
        return repository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return repository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return repository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return repository.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return repository.countByBrandAndName(brand,name);
    }
}
