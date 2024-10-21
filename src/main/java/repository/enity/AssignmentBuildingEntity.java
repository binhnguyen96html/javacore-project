package repository.enity;

import annotation.Column;
import annotation.Entity;
import annotation.Table;

@Entity
@Table(name="assignmentbuilding")
public class AssignmentBuildingEntity extends BaseEntity {

	@Column(name="staffid")
	private Long staffId;
	
	@Column(name="buildingid")
	private Long buildingId;

	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public Long getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(Long buildingId) {
		this.buildingId = buildingId;
	}
}