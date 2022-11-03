package it.ecommerce.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//import org.hibernate.annotations.LazyToOne;
//import org.hibernate.annotations.LazyToOneOption;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(schema = "ec", name = "Categories")
//@JsonIgnoreProperties("hibernateLazyInitializer")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	@Column(name = "insertion_date")
	private LocalDateTime insertionDate;

	@Column(name = "last_modified")
	private LocalDateTime lastModified;
	
//	@JsonBackReference
//	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
	@OneToMany(mappedBy = "category")
	private List<Product> products;

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getInsertionDate() {
		return insertionDate;
	}

	public void setInsertionDate(LocalDateTime insertionDate) {
		this.insertionDate = insertionDate;
	}

	public LocalDateTime getLastModified() {
		return lastModified;
	}

	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", insertionDate=" + insertionDate + ", lastModified="
				+ lastModified + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, insertionDate, lastModified, name, products);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		return Objects.equals(id, other.id) && Objects.equals(insertionDate, other.insertionDate)
				&& Objects.equals(lastModified, other.lastModified) && Objects.equals(name, other.name)
				&& Objects.equals(products, other.products);
	}

	
}
