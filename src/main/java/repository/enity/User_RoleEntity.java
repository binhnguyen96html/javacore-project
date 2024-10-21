package repository.enity;

import annotation.Column;
import annotation.Entity;
import annotation.Table;

@Entity
@Table(name="user_role")
public class User_RoleEntity extends BaseEntity {

	@Column(name="roleid")
	private Long roleid;
	
	@Column(name="userid")
	private Long userid;

	public Long getRoleid() {
		return roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}
}
