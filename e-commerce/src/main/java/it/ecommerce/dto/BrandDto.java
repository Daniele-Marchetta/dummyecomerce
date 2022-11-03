package it.ecommerce.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import it.ecommerce.constraint.BrandConstraintId;
import it.ecommerce.constraint.BrandConstraintName;
import it.ecommerce.validation.group.OnUpdate;

public class BrandDto {
	
	@BrandConstraintId(groups = OnUpdate.class,message = "This Id doesnt't exist")
	private Integer id;

	@NotBlank(message = "Provide a name for the brand")
	@Size(min = 1, max = 20, message = "The brand name must be at least one character long and max 20")
	@BrandConstraintName(message="Already existing brand name")
	private String name;

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

}
