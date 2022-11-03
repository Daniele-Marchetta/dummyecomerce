package it.ecommerce.dto;

import it.ecommerce.constraint.CartConstraintId;
import it.ecommerce.constraint.CartConstraintUserId;
import it.ecommerce.validation.group.OnCreate;
import it.ecommerce.validation.group.OnUpdate;

public class CartDto {

	@CartConstraintId(groups = OnUpdate.class, message = "Invalid id")
	private Integer id;

	@CartConstraintUserId(message = "Invalid user id")
	private Integer userId;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
