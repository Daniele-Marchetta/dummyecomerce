package it.ecommerce.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import it.ecommerce.constraint.CategoryConstraintId;
import it.ecommerce.constraint.CategoryConstraintName;
import it.ecommerce.validation.group.OnUpdate;

public class CategoryDto {

	@CategoryConstraintId(groups = OnUpdate.class,message = "This Id doesnt't exist")
	private Integer id;

	@NotBlank(message = "Provide a name for the category")
	@Size(min = 2, max = 30, message = "The name of the category must be at least 2 characters long and max 30")
	@CategoryConstraintName(message="Name not valid, this category already exists")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
