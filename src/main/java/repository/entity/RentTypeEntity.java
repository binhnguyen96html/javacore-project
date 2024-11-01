package repository.entity;

import annotation.Column;
import annotation.Entity;
import annotation.Table;

@Entity
@Table(name="renttype")
public class RentTypeEntity extends BaseEntity {
	

	@Column(name="code")
	private String code;
	
	@Column(name="name")
	private String name;

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
