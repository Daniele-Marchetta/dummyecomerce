package it.ecommerce.dto;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import it.ecommerce.constraint.RoleConstraintId;
import it.ecommerce.constraint.RoleConstraintRole;
import it.ecommerce.validation.group.OnCreate;
import it.ecommerce.validation.group.OnUpdate;


public class RoleDto {

	@Null(groups = OnCreate.class , message = "id must be null on POST")
	@NotNull(groups = OnUpdate.class , message = "id must be not null on PATCH")
	@RoleConstraintId(groups = OnUpdate.class)
	private Integer id;
	
	@NotEmpty(message = "provide role")
	@RoleConstraintRole
	private String role;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
