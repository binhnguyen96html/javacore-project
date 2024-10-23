package repository.enity;

import annotation.Column;
import annotation.Entity;
import annotation.Table;

@Entity
@Table(name="buldingrenttype")
public class BuildingRentTypeEntity extends BaseEntity {
	
	@Column(name="buildingid")
	private Long buildingid;
	
	@Column(name="renttypeid")
	private Long renttypeid;
	

	public Long getBuildingid() {
		return buildingid;
	}
	public void setBuildingid(Long buildingid) {
		this.buildingid = buildingid;
	}
	public Long getRenttypeid() {
		return renttypeid;
	}
	public void setRenttypeid(Long renttypeid) {
		this.renttypeid = renttypeid;
	}
}
