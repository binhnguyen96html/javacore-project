package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Scanner;

import controller.BuildingController;
import model.response.BuildingResponse;
import model.response.UserWithAssignmentStatus;
import utils.StringUtils;

public class BuildingView {
	private static final Scanner scanner = new Scanner(System.in);
	private static final BuildingController buildingController = new BuildingController();
	private static final Map<String, String> searchParams = initializeSearchParams();
	private static List<String> buildingTypesList = new ArrayList<>();
	private static final Map<String, String> emptySearchParams = emptySearchParams();
	private static List<String> emptyBuildingTypesList = new ArrayList<>();

	public static void main(String[] args) {

		int option;

		do {
			System.out.println("----- MENU -----");
			System.out.println("1. Tìm kiếm tòa nhà");
			System.out.println("2. Giao tòa nhà");
			System.out.println("3. Thoát");
			System.out.println("--------------------------");
			System.out.print("Nhập lựa chọn của bạn: ");

			option = scanner.nextInt();
			scanner.nextLine();

			if (option == 1) {
				searchBuilding();
			} // option1

			if (option == 2) {

				int input;

				do {
					displayManagementMenu();
					input = scanner.nextInt();
					scanner.nextLine();

					switch (input) {
						case 1:
							displaySearchResults(convertToSearchParams(emptySearchParams), emptyBuildingTypesList);
							break;
	
						case 2:
							System.out.print("Nhập buildingId: ");
							Long buildingId = scanner.nextLong();
							scanner.nextLine();
							System.out.println("Danh sách nhân viên quản lý tòa nhà " + buildingId);
							List<UserWithAssignmentStatus> users = buildingController
									.getAllStaffWithAssingmentStatus(buildingId);
							displayUserList(users);
							break;
	
						case 3:
							System.out.print("Nhập buildingId: ");
							Long buildingId2 = scanner.nextLong();
							scanner.nextLine();
	
							System.out.println(
									"Trường hợp tòa nhà không được giao cho bất kì nhân viên (nhập khoảng trắng hoặc ENTER xuống hàng)");
							System.out.print("Nhập danh sách staffIds (phân cách bởi dấu phẩy): ");
							String staffIds = scanner.nextLine();
	
							System.out.println("-------------------------------------");
							System.out.println("Trước khi giao tòa nhà cho ID = " + buildingId2
									+ " cho danh sách nhân viên có ID=[" + staffIds + "]");
							List<UserWithAssignmentStatus> users2 = buildingController
									.getAllStaffWithAssingmentStatus(buildingId2);
							displayUserList(users2);
	
							List<Long> staffIdsList = Arrays.asList();
							if (!staffIds.trim().equals("")) {
								staffIdsList = Arrays.stream(staffIds.split(",")).map(Long::parseLong)
										.collect(Collectors.toList());
							}
							Set<Long> staffIds_input = new HashSet<>(staffIdsList);
							buildingController.assignBuilding(buildingId2, staffIds_input);
	
							System.out.println("-------------------------------------");
							System.out.println("Sau khi giao tòa nhà cho ID = " + buildingId2
									+ " cho danh sách nhân viên có ID=[" + staffIds + "]");
							List<UserWithAssignmentStatus> users3 = buildingController
									.getAllStaffWithAssingmentStatus(buildingId2);
							displayUserList(users3);
	
							break;
	
						default:
							break;
					}// switch

				} while (input != 4);
			} // option2

		} while (option != 3);

		System.out.println("Chương trình kết thúc. Tạm biệt!");
		scanner.close();
	}

	private static void displayUserList(List<UserWithAssignmentStatus> users) {
		for (UserWithAssignmentStatus item : users) {
			System.out.println("-------------------------------------");
			System.out.println("id=" + item.getId() + ", " + "fullname='" + item.getFullName() + "', " + "checked="
					+ item.isChecked());
		}
	}

	private static void displaySearchResults(Map<String, Object> buildingSearchParams, List<String> buildingTypes) {

		System.out.println("----- DANH SÁCH TÒA NHÀ -----");

		List<BuildingResponse> results = buildingController.findBuilding(buildingSearchParams, buildingTypesList);

		for (BuildingResponse item : results) {
			System.out.println("-------------------------------------");
			System.out.println("id=" + item.getId() + ", " + "name='" + item.getName() + "', " + "numberOfBasement="
					+ item.getNumberOfBasement() + ", " + "address='" + item.getAddress() + "', " + "mangerName='"
					+ item.getManagerName() + "', " + "managerPhone='" + item.getManagerPhoneNumber() + "', "
					+ "floorArea=" + item.getFloorArea() + ", " + "rentPrice=" + item.getRentPrice() + ", "
					+ "serviceFee=" + item.getServiceFee() + ", " + "rentalArea='" + item.getRentalArea() + "', "
					+ "rentTypes='" + item.getRentTypes() + "'");
		} // for

	}

	private static void displayManagementMenu() {
		System.out.println("\n----- QUẢN LÝ TÒA NHÀ -----");
		System.out.println("----- MENU -----");
		System.out.println("1. Hiển thị tất cả tòa nhà");
		System.out.println("2. Hiển thị danh sách nhân viên quản lý theo tòa nhà");
		System.out.println("3. Giao tòa nhà");
		System.out.println("4. Thoát");
		System.out.print("Nhập lựa chọn của bạn: ");
	}

	private static void searchBuilding() {

		Integer condition;

		do {
			displaySearchMenu();
			condition = scanner.nextInt();
			scanner.nextLine();

			switch (condition) {
			case 1:
				System.out.print("Nhập tên tòa nhà: ");
				String name = scanner.nextLine();
				searchParams.put("name", name);
				break;
			case 2:
				System.out.print("Nhập tên đường: ");
				String street = scanner.nextLine();
				searchParams.put("street", street);
				break;
			case 3:
				System.out.print("Nhập tên phường: ");
				String ward = scanner.nextLine();
				searchParams.put("ward", ward);
				break;
			case 4:
				System.out.print("Nhập tên quận: ");
				String district = scanner.nextLine();
				searchParams.put("district", district);
				break;
			case 5:
				System.out.print("Nhập Diện tích sàn: ");
				String floorarea = scanner.nextLine();
				searchParams.put("floorarea", floorarea);
				break;
			case 6:
				System.out.print("Nhập Diện tích thuê từ: ");
				String arearentfrom = scanner.nextLine();
				searchParams.put("arearentfrom", arearentfrom);
				break;
			case 7:
				System.out.print("Nhập Diện tích thuê đén: ");
				String arearentto = scanner.nextLine();
				searchParams.put("arearentto", arearentto);
				break;
			case 8:
				System.out.print("Nhập Giá thuê từ: ");
				String costrentfrom = scanner.nextLine();
				searchParams.put("costrentfrom", costrentfrom);
				break;
			case 9:
				System.out.print("Nhập Giá thuê đến: ");
				String costrentto = scanner.nextLine();
				searchParams.put("costrentto", costrentto);
				break;
			case 10:
				System.out.print("Nhập Loại tòa nhà: ");
				String buildingTypes = scanner.nextLine();
				buildingTypesList = Arrays.stream(buildingTypes.split(",")).map(type -> type.trim())
						.collect(Collectors.toList());
				break;
//			case 11:
//				System.out.print("Nhập Nhân viên phụ trách: ");
//				String assignedstaffid = scanner.nextLine();
//				searchParams.put("assignedstaffid", assignedstaffid);
//				break;
			case 12:
				System.out.print("Nhập Tên quản lý: ");
				String managername = scanner.nextLine();
				searchParams.put("managername", managername);
				break;
			case 13:
				System.out.print("Nhập SDT quản lý: ");
				String managerphone = scanner.nextLine();
				searchParams.put("managerphone", managerphone);
				break;
			default:
				break;
			}

		} while (condition != 14);

		displaySearchResults(convertToSearchParams(searchParams), buildingTypesList);
	}

	
	private static void displaySearchMenu() {
		System.out.println("\n----- TÌM KIẾM TÒA NHÀ -----");
		System.out.println("----- Các điều kiện tìm kiếm tòa nhà");
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
		//System.out.println("11. Nhân viên phụ trách");
		System.out.println("12. Tên quản lý");
		System.out.println("13. SDT quản lý");
		System.out.println("14. Nhập xong!");
		System.out.println("----------------------------");
		System.out.print("Nhập lựa chọn của bạn: ");
	}

	
	private static Map<String, String> initializeSearchParams() {
		Map<String, String> params = new HashMap<>();

		params.put("name", "");
		params.put("floorarea", "");
		params.put("district", "");
		params.put("ward", "");
		params.put("street", "");
		params.put("numberofbasement", "");
		params.put("direction", "");
		params.put("level", "");
		params.put("arearentfrom", "");
		params.put("arearentto", "");
		params.put("costrentfrom", "");
		params.put("costrentto", "");
		params.put("managername", "");
		params.put("managerphone", "");
		params.put("assignedstaffid", "");

		return params;
	}

	private static Map<String, String> emptySearchParams() {
		Map<String, String> params = new HashMap<>();

		params.put("name", "");
		params.put("floorarea", "");
		params.put("district", "");
		params.put("ward", "");
		params.put("street", "");
		params.put("numberofbasement", "");
		params.put("direction", "");
		params.put("level", "");
		params.put("arearentfrom", "");
		params.put("arearentto", "");
		params.put("costrentfrom", "");
		params.put("costrentto", "");
		params.put("managername", "");
		params.put("managerphone", "");
		params.put("assignedstaffid", "");

		return params;
	}

	private static Map<String, Object> convertToSearchParams(Map<String, String> params) {
		Map<String, Object> buildingSearchParams = new HashMap<>();

		buildingSearchParams.put("name", params.get("name"));
		buildingSearchParams.put("floorarea", getParseInteger(params.get("floorarea")));
		buildingSearchParams.put("district", params.get("district"));
		buildingSearchParams.put("ward", params.get("ward"));
		buildingSearchParams.put("street", params.get("street"));
		buildingSearchParams.put("numberofbasement", getParseInteger(params.get("numberofbasement")));
		buildingSearchParams.put("direction", params.get("direction"));
		buildingSearchParams.put("level", params.get("level"));
		buildingSearchParams.put("arearentfrom", getParseInteger(params.get("arearentfrom")));
		buildingSearchParams.put("arearentto", getParseInteger(params.get("arearentto")));
		buildingSearchParams.put("costrentfrom", getParseInteger(params.get("costrentfrom")));
		buildingSearchParams.put("costrentto", getParseInteger(params.get("costrentto")));
		buildingSearchParams.put("managername", params.get("managername"));
		buildingSearchParams.put("managerphone", params.get("managerphone"));
		buildingSearchParams.put("assignedstaffid", getParseLong(params.get("assignedstaffid")));

		return buildingSearchParams;
	}

	private static Integer getParseInteger(String value) {
		return StringUtils.isNullOrEmpty(value) ? null : Integer.parseInt(value);
	}

	private static Long getParseLong(String value) {
		return StringUtils.isNullOrEmpty(value) ? null : Long.parseLong(value);
	}
}
