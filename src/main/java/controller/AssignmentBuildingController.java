package controller;

import java.util.List;

import model.request.AssignmentBuildingRequestDTO;
import model.response.AssignmentStaffResponseDTO;
import service.AssignmentBuildingService;
import service.UserService;
import service.impl.AssignmentBuldingServiceImpl;
import service.impl.UserServiceImpl;

public class AssignmentBuildingController {

	private AssignmentBuildingService assignmentBuildingService = new AssignmentBuldingServiceImpl();
	private UserService userService = new UserServiceImpl();
	
	
	public void assignBuildingToStaff(AssignmentBuildingRequestDTO request) {
		assignmentBuildingService.assignBuildingToStaff(request);
	}
	
	public List<AssignmentStaffResponseDTO> findStaffBy(Long buildingId){
		return userService.findStaffBy(buildingId);
	}
}
