package repository.entity;

import annotation.Column;
import annotation.Entity;
import annotation.Table;


@Entity
@Table(name="district")
public class DistrictEntity {

	@Column(name="id")
	private Long id;
	
	@Column(name="code")
	private String code;
	
	@Column(name="name")
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
