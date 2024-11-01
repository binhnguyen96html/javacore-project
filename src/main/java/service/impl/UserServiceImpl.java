package service.impl;

import java.util.ArrayList;
import java.util.List;

import converter.UserConverter;
import model.response.AssignmentStaffResponseDTO;
import repository.UserRepository;
import repository.entity.UserEntity;
import repository.impl.UserRepositoryImpl;
import service.UserService;

public class UserServiceImpl implements UserService {

	private final UserConverter userConverter = new UserConverter();

	private UserRepository userRepository = new UserRepositoryImpl();

	@Override
	public List<AssignmentStaffResponseDTO> findAllStaff() {

		List<UserEntity> userEntities = userRepository.findAllStaff();

		List<AssignmentStaffResponseDTO> assignmentStaffResponses = new ArrayList<>();

		for (UserEntity staff : userEntities) {
			assignmentStaffResponses.add(userConverter.toAssignmentStaffResponse(staff));
		}

		return assignmentStaffResponses;
	}

	@Override
	public List<AssignmentStaffResponseDTO> findStaffBy(Long buildingId) {
		
		List<UserEntity> userEntities = userRepository.findStaffBy(buildingId);

		List<AssignmentStaffResponseDTO> assignmentStaffResponses = new ArrayList<>();

		for (UserEntity staff : userEntities) {
			assignmentStaffResponses.add(userConverter.toAssignmentStaffResponse(staff));
		}

		return assignmentStaffResponses;
	}

}
