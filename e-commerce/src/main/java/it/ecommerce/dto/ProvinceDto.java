package it.ecommerce.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import it.ecommerce.constraint.ProvinceConstraint;
import it.ecommerce.constraint.ProvinceConstraintAcronymCreate;
import it.ecommerce.constraint.ProvinceConstraintAcronymUpdate;
import it.ecommerce.constraint.ProvinceConstraintName;
import it.ecommerce.validation.group.OnCreate;
import it.ecommerce.validation.group.OnUpdate;


public class ProvinceDto {

	@NotEmpty(message = "provide province acronym")
	@Size(min = 2, max = 2 ,message = "acronym lenght must be 2 char")
	@ProvinceConstraintAcronymCreate(groups = OnCreate.class)
	@ProvinceConstraintAcronymUpdate(groups = OnUpdate.class)
	private String acronym;

	@NotEmpty(message = "provide province name")
	@Size(min = 3, max = 20 , message = "province name lenght must be min 3 char and max 20")
	@ProvinceConstraintName
	private String name;
	
	@NotNull(message = "provide regionId")
	@ProvinceConstraint(message = "Region id not present in database (i wrote this)")
	private Integer regionId;

	public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	@Override
	public String toString() {
		return "ProvinceDto [acronym=" + acronym + ", name=" + name + ", regionId=" + regionId + "]";
	}
	
	
	
	
}
