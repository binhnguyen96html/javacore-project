package service;

import java.util.List;

import model.response.AssignmentStaffResponseDTO;

public interface UserService {

	List<AssignmentStaffResponseDTO> findAllStaff();
	
	List<AssignmentStaffResponseDTO> findStaffBy(Long buildingId);
}
