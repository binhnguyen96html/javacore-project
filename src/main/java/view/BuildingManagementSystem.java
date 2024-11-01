package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import constant.SystemConstant;
import controller.AssignmentBuildingController;
import controller.BuildingController;
import controller.UserController;
import enums.BuildingTypeEnums;
import enums.DistrictEnums;
import model.request.AssignmentBuildingRequestDTO;
import model.response.AssignmentStaffResponseDTO;
import model.response.BuildingSearchResponseDTO;




public class BuildingManagementSystem {
	BuildingController buildingController = new BuildingController();
	UserController userController = new UserController();

	private AssignmentBuildingController assignmentBuildingController = new AssignmentBuildingController();

	public static void main(String[] args) {
		BuildingManagementSystem system = new BuildingManagementSystem();
		system.processBuildingManagement();
		System.exit(0);
	}

	private void processBuildingManagement() {
		Scanner scanner = new Scanner(System.in);
		int choice;

		do {
			printMenu();
			System.out.print("Nhập lựa chọn của bạn: ");
			choice = scanner.nextInt();
			scanner.nextLine(); // Consume the newline character

			switch (choice) {
			case 1 -> searchBuilding();
			case 2 -> manageBuilding();
			case 0 -> System.out.println("Chương trình kết thúc. Tạm biệt!");
			default -> System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
			}
		} while (choice != 0);
	}

	private static void printMenu() {
		System.out.println("----- MENU -----");
		System.out.println("1. Tìm kiếm tòa nhà");
		System.out.println("2. Giao tòa nhà");
		System.out.println("0. Thoát");
		System.out.println("----------------");
	}

	public void showMenuInput() {
		System.out.println("-----Các điều kiện tìm kiếm tòa nhà");
		System.out.println("1. Tên tòa nhà");
		System.out.println("2. Tên đường");
		System.out.println("3. Tên phường");
		System.out.println("4. Tên quận");
		System.out.println("5. Diện tích sàn");
		System.out.println("6. Diện tích thuê từ");
		System.out.println("7. Diện tích thuê đến");
		System.out.println("8. Giá thuê từ");
		System.out.println("9. Giá thuê đến");
		System.out.println("10. Loại tòa nhà");
		System.out.println("11. Nhân viên phụ trách");
		System.out.println("12. Tên quản lý");
		System.out.println("13. SĐT quản lý");
		System.out.println("14. Nhập xong!");
		System.out.println("-------------------------------");
	}

	private void searchBuilding() {
		Map<String, Object> params = new HashMap<>();
		List<String> buildingTypes = new ArrayList<>();

		System.out.println("----- TÌM KIẾM TÒA NHÀ -----");
		initSearchBuilding(params, buildingTypes);
		System.out.println("----- DANH SÁCH TOÀ NHÀ ----");

		List<BuildingSearchResponseDTO> responses = buildingController.findBuildingBy(params, buildingTypes);

		if (CollectionUtils.isEmpty(responses)) {
			System.out.println("Dữ liệu trống!");
			System.out.println("------------------------------------");
			return;
		}
		for (BuildingSearchResponseDTO response : responses) {
			System.out.println("------------------------------------");
			//System.out.println(response.toString());
			System.out.println("id=" + response.getId() + ", " + "name='" + response.getName() + "', " + "numberOfBasement="
					+ response.getNumberOfBasement() + ", " + "address='" + response.getAddress() + "', " + "mangerName='"
					+ response.getManagerName() + "', " + "managerPhone='" + response.getManagerPhone() + "', " + "floorArea="
					+ response.getFloorArea() + ", " + "rentPrice=" + response.getRentPrice() + ", " + "serviceFee="
					+ response.getServiceFee() + ", " 
					+ "rentTypes='"+ response.getRentTypes() + "', "
					+ "rentAreaDescription='" + response.getRentAreaDescription()+"' ");

		}
	}

	private void initSearchBuilding(Map<String, Object> params, List<String> buildingTypes) {
		Scanner scanner = new Scanner(System.in);
		int choice;
		do {
			showMenuInput();
			System.out.print("Nhập lựa chọn của bạn: ");
			choice = scanner.nextInt();
			scanner.nextLine();
			switch (choice) {
			case 1:
				System.out.print("Nhập tên tòa nhà: ");
				params.put(SystemConstant.NAME, scanner.nextLine());
				break;
			case 2:
				System.out.print("Nhập tên đường: ");
				params.put(SystemConstant.STREET, scanner.nextLine());
				break;
			case 3:
				System.out.print("Nhập tên phường: ");
				params.put(SystemConstant.WARD, scanner.nextLine());
				break;
			case 4:
				initDataSearchDistrict(params, scanner);
				break;
			case 5:
				System.out.print("Nhập diện tích sàn: ");
				int floorArea = checkValidNumber(scanner.nextLine());
				if (floorArea <= 0) {
					System.out.print("Dữ liệu số không hợp lệ. Vui lòng thử lại: ");
					break;
				}
				params.put(SystemConstant.FLOOR_AREA, floorArea);
				break;
			case 6:
				System.out.print("Nhập diện tích thuê từ: ");
				int rentPriceFrom = checkValidNumber(scanner.nextLine());
				if (rentPriceFrom <= 0) {
					System.out.print("Dữ liệu số không hợp lệ. Vui lòng thử lại");
					break;
				}
				params.put(SystemConstant.RENT_PRICE_FROM, rentPriceFrom);
				break;
			case 7:
				System.out.print("Nhập diện tích thuê đến: ");
				int rentPriceTo = checkValidNumber(scanner.nextLine());
				if (rentPriceTo <= 0) {
					System.out.print("Dữ liệu số không hợp lệ. Vui lòng thử lại");
					break;
				}
				params.put(SystemConstant.RENT_PRICE_TO, rentPriceTo);
				break;
			case 8:
				System.out.print("Nhập giá thuê từ: ");
				int rentAreaFrom = checkValidNumber(scanner.nextLine());
				if (rentAreaFrom <= 0) {
					System.out.print("Dữ liệu số không hợp lệ. Vui lòng thử lại");
					break;
				}
				params.put(SystemConstant.RENT_AREA_FROM, rentAreaFrom);
				break;
			case 9:
				System.out.print("Nhập giá thuê đến: ");
				int rentAreaTo = checkValidNumber(scanner.nextLine());
				if (rentAreaTo <= 0) {
					System.out.print("Dữ liệu số không hợp lệ. Vui lòng thử lại");
					break;
				}
				params.put(SystemConstant.RENT_AREA_TO, rentAreaTo);
				break;
			case 10:
				initDataSearchBuildingTypes(buildingTypes, scanner);
				break;
			case 11:
				initDataSearchStaff(params, scanner);
				break;
			case 12:
				System.out.print("Nhập tên quản lí: ");
				params.put(SystemConstant.MANAGER_NAME, scanner.nextLine());
				break;
			case 13:
				System.out.print("Nhập số điện tên quản lí: ");
				params.put(SystemConstant.MANAGER_PHONE, scanner.nextLine());
				break;
			}
		} while (choice != 14);
	}

	private void initDataSearchStaff(Map<String, Object> params, Scanner scanner) {
		List<AssignmentStaffResponseDTO> staffResponses = userController.findAllStaff();
		System.out.println("Danh sách nhân viên: ");
		for (AssignmentStaffResponseDTO response : staffResponses) {
			System.out.println(response.toString());
		}
		System.out.print("Nhập ID nhân viên: ");
		int staffId = checkValidNumber(scanner.nextLine());
		if (staffId <= 0) {
			System.out.println("Dữ liệu không hợp lệ. Vui lòng thử lại");
			return;
		}
		params.put(SystemConstant.STAFF_ID, (long) staffId);
	}

	private void initDataSearchBuildingTypes(List<String> buildingTypes, Scanner scanner) {
		System.out.println("Danh sách loại tòa nhà hiện có: ");
		BuildingTypeEnums[] types = BuildingTypeEnums.values();
		String[] names = new String[types.length];
		for (int i = 0; i < types.length; i++) {
			System.out.println(String.format("%d. %s", (i + 1), types[i].getTypeBuildingName()));
			names[i] = types[i].getTypeBuildingCode();
		}
		System.out.print("Nhập các loại tòa nhà bằng cách nhập số thứ tự (cách nhau bởi dấu phẩy): ");
		String choices = scanner.nextLine();
		List<Integer> choices_list = Arrays
				.stream(choices.split(","))
				.map(c -> c.trim())
				.map(Integer::parseInt)
				.collect(Collectors.toList());
		 if (choices_list.isEmpty() || choices_list.stream().anyMatch(choice -> choice <= 0 || choice > types.length)) {
		        System.out.println("Dữ liệu loại tòa nhà không hợp lệ. Vui lòng thực hiện lại thao tác tìm kiếm");
		        return;
		    }
		for(Integer i: choices_list) {
			buildingTypes.add(names[i-1]);
		}
		//System.out.println(" buildingTypes: "+ buildingTypes);
	}

	private void initDataSearchDistrict(Map<String, Object> params, Scanner scanner) {
		System.out.println("Danh sách quận hiện có: ");
		DistrictEnums[] districts = DistrictEnums.values();
		for (int i = 0; i < districts.length; i++) {
			System.out.println(String.format("%d. %s", (i + 1), districts[i].getDistrictValue()));
		}
		System.out.print("Nhập số thứ tự quận: ");
		int choice = checkValidNumber(scanner.nextLine());
		if (choice <= 0 || choice > districts.length) {
			System.out.println("Dữ liệu quận không hợp lệ. Vui lòng thực hiện lại thao tác tìm kiếm");
			return;
		}
		params.put(SystemConstant.DISTRICT_CODE, districts[choice - 1].toString());
	}

	private int checkValidNumber(String number) {
		try {
			return Integer.parseInt(number.trim());
		} catch (Exception e) {
			return -1;
		}
	}

	private void showMenuManageBuilding() {
		System.out.println("----- MENU -----");
		System.out.println("1. Hiển thị tất cả tòa nhà");
		System.out.println("2. Hiển thị danh sách nhân viên quản lí theo tòa nhà");
		System.out.println("3. Giao tòa nhà");
		System.out.println("4. Thoát ");
	}

	private void manageBuilding() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("----- QUẢN LÝ TÒA NHÀ -----");
		int choice;
		do {
			showMenuManageBuilding();
			System.out.print("Nhập lựa chọn của bạn: ");
			choice = checkValidNumber(scanner.nextLine());
			switch (choice) {
			case 1:
				showAllBuildings();
				break;
			case 2:
				showStaffByBuildingId();
				break;
			case 3:
				assignBuildingToStaff();
				break;
			}
		} while (choice != 4);
	}

	private void showAllBuildings() {
		System.out.println("----- DANH SÁCH TÒA NHÀ -----");
		
		List<BuildingSearchResponseDTO> responses = buildingController.findBuildingBy(new HashMap<>(),
				new ArrayList<>());

		if (CollectionUtils.isEmpty(responses)) {
			System.out.println("Dữ liệu trống!");
			System.out.println("------------------------------------");
			return;
		}
		for (BuildingSearchResponseDTO response : responses) {
			System.out.println("------------------------------------");
			//System.out.println(response.toString());
			System.out.println("id=" + response.getId() + ", " + "name='" + response.getName() + "', " + "numberOfBasement="
					+ response.getNumberOfBasement() + ", " + "address='" + response.getAddress() + "', " + "mangerName='"
					+ response.getManagerName() + "', " + "managerPhone='" + response.getManagerPhone() + "', " + "floorArea="
					+ response.getFloorArea() + ", " + "rentPrice=" + response.getRentPrice() + ", " + "serviceFee="
					+ response.getServiceFee() + ", " 
					+ "rentTypes='"+ response.getRentTypes() + "', "
					+ "rentAreaDescription='" + response.getRentAreaDescription()+"' ");
		}
	}

	private void showStaffByBuildingId() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Nhập buildingId: ");
		int buildingId = checkValidNumber(scanner.nextLine());
		if (buildingId <= 0) {
			System.out.println("Dữ liệu không hợp lệ. Vui lòng thử lại");
			return;
		}
		System.out.println(String.format("Danh sách nhân viên quản lí tòa nhà %d: ", buildingId));
		List<AssignmentStaffResponseDTO> responses = assignmentBuildingController.findStaffBy((long) buildingId);
		for (AssignmentStaffResponseDTO response : responses) {
			System.out.println("------------------------------------");
			System.out.println(response.toString());
		}
	}

	private void assignBuildingToStaff() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Nhập buildingId: ");
		long buildingId = checkValidNumber(scanner.nextLine());

		if (buildingId <= 0) {
			System.out.println("Dữ liệu không hợp lệ. Vui lòng thử lại");
			return;
		}

		System.out.println(
				"Trường hợp tòa nhà không được giao cho bất kì nhân viên (nhập khoảng trắng hoặc ENTER xuống hàng)");
		System.out.print("Nhập danh sách staffIds (phân cách bởi dấu phẩy): ");

		String staffIdsInput = scanner.nextLine();
		String[] staffIdsArray = staffIdsInput.split(",");
		List<Long> staffIds = new ArrayList<>();

		for (String staffId : staffIdsArray) {
			if (StringUtils.isBlank(staffId)) {
				continue;
			}
			long id = checkValidNumber(staffId.trim());
			if (id <= 0) {
				System.out.println("Nhân viên [" + staffId + "] không hợp lệ");
				return;
			}
			staffIds.add(id);
		}

		AssignmentBuildingRequestDTO request = new AssignmentBuildingRequestDTO(buildingId, staffIds);

		System.out.println("--------------------------");
		System.out.println("Trước khi giao tòa nhà có ID = " + buildingId + " cho danh sách nhân viên có ID = ["
				+ staffIdsInput + "]");

		for (AssignmentStaffResponseDTO item : assignmentBuildingController.findStaffBy(buildingId)) {
			System.out.println(item.toString());
		}

		System.out.println("--------------------------");

		assignmentBuildingController.assignBuildingToStaff(request);

		System.out.println("Sau khi giao tòa nhà có ID = " + buildingId + " cho danh sách nhân viên có ID = ["
				+ staffIdsInput + "]");
		for (AssignmentStaffResponseDTO item : assignmentBuildingController.findStaffBy(buildingId)) {
			System.out.println(item.toString());
		}
	}
}
