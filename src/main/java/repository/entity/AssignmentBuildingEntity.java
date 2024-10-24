package repository.entity;

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

	@Override
	public String toString() {
		return "AssignmentBuildingEntity [staffId=" + staffId + ", buildingId=" + buildingId + ", getStaffId()="
				+ getStaffId() + ", getBuildingId()=" + getBuildingId() + ", getId()=" + getId() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	
}
