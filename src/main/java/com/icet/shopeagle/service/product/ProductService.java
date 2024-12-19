package com.icet.shopeagle.service.product;

import com.icet.shopeagle.model.Product;
import com.icet.shopeagle.request.AddProductRequest;
import com.icet.shopeagle.request.UpdateProductRequest;

import java.util.List;

public interface ProductService {
    Product addProduct(AddProductRequest product);
    Product updateProduct(UpdateProductRequest product);
    List<Product> getAll();
    Product getProductById(Long id);
    void deleteProductById(Long id);
    List<Product> getProductByCategory(String  category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category,String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand,String name);
    Long countProductsByBrandAndName(String brand,String name);
}
