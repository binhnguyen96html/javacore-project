package model.request;

import java.util.List;

public class AssignmentBuildingRequestDTO {

	private Long buildingId;
	private List<Long> staffIds;
	

	public AssignmentBuildingRequestDTO(Long buildingId, List<Long> staffIds) {
		super();
		this.buildingId = buildingId;
		this.staffIds = staffIds;
	}

	public Long getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(Long buildingId) {
		this.buildingId = buildingId;
	}

	public List<Long> getStaffIds() {
		return staffIds;
	}

	public void setStaffIds(List<Long> staffIds) {
		this.staffIds = staffIds;
	}
	
	
	
	
}
