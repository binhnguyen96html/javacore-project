package converter;

import model.response.AssignmentStaffResponseDTO;
import repository.entity.UserEntity;

public class UserConverter {

	public AssignmentStaffResponseDTO toAssignmentStaffResponse(UserEntity staff) {
		
		AssignmentStaffResponseDTO assignmentStaffResponse = new AssignmentStaffResponseDTO();
		
		assignmentStaffResponse.setId(staff.getId());
		assignmentStaffResponse.setFullName(staff.getFullName());
		assignmentStaffResponse.setChecked(true);
		
		return assignmentStaffResponse;
	}
}
