package it.ecommerce.dto;

import java.sql.Date;
import java.util.Objects;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;

import it.ecommerce.constraint.ProductConstraintId;
import it.ecommerce.constraint.PromotionConstraintProductId;
import it.ecommerce.constraint.PromotionConstraintUserId;
import it.ecommerce.validation.group.OnCreate;
import it.ecommerce.validation.group.OnUpdate;

public class PromotionDto {

	@Null(groups = OnCreate.class, message = "id must be null on POST")
	@NotNull(groups = OnUpdate.class, message = "id must be not null on PATCH")
	@ProductConstraintId(groups = OnUpdate.class)
	private Integer id;

	@Positive
	@Max(value = 80, message = "discount must be lower than 80%")
	@NotNull(message = "provide discount")
	private Integer discount;

	@Null(message = "do not provide discount type already calculated by insertion of user")
	private Boolean discountType;

	@FutureOrPresent(message = "start date must be in present or future")
	@NotNull(message = "provide start date")
	private Date startDate;

	@Future(message = " end date must be in future")
	@NotNull(message = "provide end date")
	private Date endDate;

	@NotNull(message = "provide productId")
	@PromotionConstraintProductId
	private Integer productId;

	@PromotionConstraintUserId
	private Integer userId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public Boolean getDiscountType() {
		return discountType;
	}

	public void setDiscountType(Boolean discountType) {
		this.discountType = discountType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(discount, discountType, endDate, id, productId, startDate, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PromotionDto other = (PromotionDto) obj;
		return Objects.equals(discount, other.discount) && Objects.equals(discountType, other.discountType)
				&& Objects.equals(endDate, other.endDate) && Objects.equals(id, other.id)
				&& Objects.equals(productId, other.productId) && Objects.equals(startDate, other.startDate)
				&& Objects.equals(userId, other.userId);
	}

	@Override
	public String toString() {
		return "PromotionDto [id=" + id + ", discount=" + discount + ", discountType=" + discountType + ", startDate="
				+ startDate + ", endDate=" + endDate + ", productId=" + productId + ", userId=" + userId + "]";
	}

	

}
