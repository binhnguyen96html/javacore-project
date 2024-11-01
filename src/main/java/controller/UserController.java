package controller;

import java.util.List;

import model.response.AssignmentStaffResponseDTO;
import service.UserService;
import service.impl.UserServiceImpl;

public class UserController {
	
	private UserService userService = new UserServiceImpl();

	
	public List<AssignmentStaffResponseDTO> findAllStaff(){
		return userService.findAllStaff();
	}

}
