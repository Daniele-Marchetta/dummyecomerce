package it.ecommerce.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import it.ecommerce.constraint.BrandConstraintId;
import it.ecommerce.constraint.CategoryConstraintId;
import it.ecommerce.constraint.ProductConstraintId;
import it.ecommerce.validation.group.OnCreate;
import it.ecommerce.validation.group.OnUpdate;

public class ProductDto {

	@NotNull(message = "id must be not null on UPDATE",groups = OnUpdate.class)
	@Null(message = "id must be null on POST " ,groups = OnCreate.class)
	@ProductConstraintId(groups = OnUpdate.class)
	private Integer id;

	@NotEmpty(message = "product name must not be empty")
	@Size(max = 150 , message = "name lenght must be < 150 char")
	private String name;

	@Positive(message = "category id must be positive")
	@NotNull(message = "category id must be not null")
	@CategoryConstraintId
	private Integer categoryId;

	@Size(min = 30, max = 20000 , message = "description lenght must be > 30 char and < 20000 char")
	@NotEmpty(message = "description must be not empty or null")
	private String description;

	@Size(max = 10 , message = "pricerange lenght must be < 10 char")
	@NotEmpty(message = "price range must be not empty or null")
	private String priceRange;

	@Positive(message = "price must be > 0")
	@Min(value = 1, message = "price low")
	private double price;

	@PositiveOrZero(message = "quantity in stock must be positive or 0 ")
	@NotNull(message = "instock must be not null")
	private Integer inStock;

	@Positive(message = "brand id must be positive")
	@NotNull(message = "brand id must be not null")
	@BrandConstraintId
	private Integer brandId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPriceRange() {
		return priceRange;
	}

	public void setPriceRange(String priceRange) {
		this.priceRange = priceRange;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Integer getInStock() {
		return inStock;
	}

	public void setInStock(Integer inStock) {
		this.inStock = inStock;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	
}
