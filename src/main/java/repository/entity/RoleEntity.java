package repository.entity;

import annotation.Column;
import annotation.Entity;
import annotation.Table;

@Entity
@Table(name="role")
public class RoleEntity extends BaseEntity {

	@Column(name="name")
	private String name;
	
	@Column(name="code")
	private String code;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	
}
