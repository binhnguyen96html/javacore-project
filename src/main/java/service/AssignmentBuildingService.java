package service;

import java.util.List;

import model.request.AssignmentBuildingRequestDTO;
import model.response.AssignmentStaffResponseDTO;

public interface AssignmentBuildingService {
	 List<AssignmentStaffResponseDTO> findStaffBy(Long buildingId);
	 void assignBuildingToStaff(AssignmentBuildingRequestDTO request);
	 
}
