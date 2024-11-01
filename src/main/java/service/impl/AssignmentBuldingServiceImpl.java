package service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import constant.SystemConstant;
import model.request.AssignmentBuildingRequestDTO;
import model.response.AssignmentStaffResponseDTO;
import repository.AssignmentBuildingRepository;
import repository.UserRepository;
import repository.entity.AssignmentBuildingEntity;
import repository.entity.UserEntity;
import repository.impl.AssignmentBuildingRepositoryImpl;
import repository.impl.UserRepositoryImpl;
import service.AssignmentBuildingService;
import converter.UserConverter;

public class AssignmentBuldingServiceImpl implements AssignmentBuildingService {

    private final AssignmentBuildingRepository assignmentBuildingRepository = new AssignmentBuildingRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();
    private final UserConverter userConverter = new UserConverter();

    @Override
    public List<AssignmentStaffResponseDTO> findStaffBy(Long buildingId) {
        List<AssignmentStaffResponseDTO> staffResponses = new ArrayList<>();
        List<AssignmentBuildingEntity> staffsByBuildingId = assignmentBuildingRepository.findByBuildingId(buildingId);
        List<UserEntity> staffs = userRepository.findByRole(SystemConstant.ROLE_STAFF);

        for (UserEntity staff : staffs){
            AssignmentStaffResponseDTO response = userConverter.toAssignmentStaffResponse(staff);

            for (AssignmentBuildingEntity assignedStaff : staffsByBuildingId){
                if (Objects.equals(staff.getId(), assignedStaff.getStaffId())) {
                    response.setChecked(true);
                    break;
                }
            }
            staffResponses.add(response);
        }
        return staffResponses;
    }

    @Override
    public void assignBuildingToStaff(AssignmentBuildingRequestDTO request) {
        Long buildingId = request.getBuildingId();
        List<Long> staffIds = request.getStaffIds();

        List<Long> assignedStaffIds = assignmentBuildingRepository.findByBuildingId(buildingId).stream()
                .map(AssignmentBuildingEntity::getStaffId)
                .collect(Collectors.toList());

        List<Long> deleteStaffIds = findItemOfSourceButNotInTarget(assignedStaffIds, staffIds);
        List<Long> addedStaffIds = findItemOfSourceButNotInTarget(staffIds, assignedStaffIds);

        assignmentBuildingRepository.assign(deleteStaffIds, addedStaffIds, buildingId);
    }

    private List<Long> findItemOfSourceButNotInTarget(List<Long> source, List<Long> target) {
        List<Long> result = new ArrayList<>();
        Long id = null;

        for (Long srcId : source) {
            for (Long targetId : target) {
                if (Objects.equals(targetId, srcId)) {
                    id = srcId;
                    break;
                }
                id = null;
            }
            if (null == id) {
                result.add(srcId);
            }
        }

        return result;
    }
}
