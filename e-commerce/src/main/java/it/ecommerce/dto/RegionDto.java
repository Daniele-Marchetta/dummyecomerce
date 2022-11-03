package it.ecommerce.dto;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import it.ecommerce.constraint.RegionConstraintId;
import it.ecommerce.constraint.RegionConstraintName;
import it.ecommerce.validation.group.OnCreate;
import it.ecommerce.validation.group.OnUpdate;


public class RegionDto {

	@Null(groups = OnCreate.class , message = "id must be null")
	@RegionConstraintId(groups = OnUpdate.class)
	private Integer id;

	@NotEmpty(message = "provide region name")
	@RegionConstraintName(message = "Region name already exist!")
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

	@Override
	public String toString() {
		return "RegionDto [id=" + id + ", name=" + name + "]";
	}
	
	
	

}
