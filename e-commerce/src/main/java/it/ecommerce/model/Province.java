package it.ecommerce.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(schema = "ec", name = "Provincies")
public class Province {

	@Id
	@Column(name = "acronym")
	private String acronym;

	@Column(name = "province_name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "region_id")
	private Region region;
	
	@OneToMany(mappedBy = "province")
	private List<PersonalData> pdatas;
	
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


	public Region getRegion() {
		return region;
	}

	public void setRegion(Region regionId) {
		this.region = regionId;
	}

	@Override
	public String toString() {
		return "Province [acronym=" + acronym + ", name=" + name + ", regionId=" + region + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(acronym, name, pdatas, region);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Province other = (Province) obj;
		return Objects.equals(acronym, other.acronym) && Objects.equals(name, other.name)
				&& Objects.equals(pdatas, other.pdatas) && Objects.equals(region, other.region);
	}
	
	

}
