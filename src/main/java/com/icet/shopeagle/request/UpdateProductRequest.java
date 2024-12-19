package com.icet.shopeagle.request;

import com.icet.shopeagle.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductRequest {
    private Long id;
    private String name;
    private String brand;

    private BigDecimal price;
    private int quantity;
    private String description;
    private Category category;
}
