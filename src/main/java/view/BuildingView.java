//package view;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//import constant.SystemConstant;
//
//import java.util.Scanner;
//
//import controller.BuildingController;
//import model.response.BuildingSearchResponseDTO;
//import model.response.UserWithAssignmentStatus;
//import utils.StringUtils;
//
//public class BuildingView {
//	private static final Scanner scanner = new Scanner(System.in);
//	private static final BuildingController buildingController = new BuildingController();
//	private static final Map<String, String> searchParams = initializeSearchParams();
//	private static List<String> buildingTypesList = new ArrayList<>();
//	private static final Map<String, String> emptySearchParams = emptySearchParams();
//	private static List<String> emptyBuildingTypesList = new ArrayList<>();
//
//	public static void main(String[] args) {
//
//		int option;
//
//		do {
//			System.out.println("----- MENU -----");
//			System.out.println("1. Tìm kiếm tòa nhà");
//			System.out.println("2. Giao tòa nhà");
//			System.out.println("3. Thoát");
//			System.out.println("--------------------------");
//			System.out.print("Nhập lựa chọn của bạn: ");
//
//			option = scanner.nextInt();
//			scanner.nextLine();
//
//			if (option == 1) {
//				searchBuilding();
//			} // option1
//
//			if (option == 2) {
//
//				int input;
//
//				do {
//					displayManagementMenu();
//					input = scanner.nextInt();
//					scanner.nextLine();
//
//					switch (input) {
//					case 1:
//						displaySearchResults(convertToSearchParams(emptySearchParams), emptyBuildingTypesList);
//						break;
//
//					case 2:
//						System.out.print("Nhập buildingId: ");
//						Long buildingId = scanner.nextLong();
//						scanner.nextLine();
//						System.out.println("Danh sách nhân viên quản lý tòa nhà " + buildingId);
//						List<UserWithAssignmentStatus> users = buildingController
//								.getAllStaffWithAssingmentStatus(buildingId);
//						displayUserList(users);
//						break;
//
//					case 3:
//						System.out.print("Nhập buildingId: ");
//						Long buildingId2 = scanner.nextLong();
//						scanner.nextLine();
//
//						System.out.println(
//								"Trường hợp tòa nhà không được giao cho bất kì nhân viên (nhập khoảng trắng hoặc ENTER xuống hàng)");
//						System.out.print("Nhập danh sách staffIds (phân cách bởi dấu phẩy): ");
//						String staffIds = scanner.nextLine();
//
//						System.out.println("-------------------------------------");
//						System.out.println("Trước khi giao tòa nhà cho ID = " + buildingId2
//								+ " cho danh sách nhân viên có ID=[" + staffIds + "]");
//						List<UserWithAssignmentStatus> users2 = buildingController
//								.getAllStaffWithAssingmentStatus(buildingId2);
//						displayUserList(users2);
//
//						List<Long> staffIdsList = Arrays.asList();
//						if (!staffIds.trim().equals("")) {
//							staffIdsList = Arrays.stream(staffIds.split(",")).map(Long::parseLong)
//									.collect(Collectors.toList());
//						}
//						Set<Long> staffIds_input = new HashSet<>(staffIdsList);
//						buildingController.assignBuilding(buildingId2, staffIds_input);
//
//						System.out.println("-------------------------------------");
//						System.out.println("Sau khi giao tòa nhà cho ID = " + buildingId2
//								+ " cho danh sách nhân viên có ID=[" + staffIds + "]");
//						List<UserWithAssignmentStatus> users3 = buildingController
//								.getAllStaffWithAssingmentStatus(buildingId2);
//						displayUserList(users3);
//
//						break;
//
//					default:
//						break;
//					}// switch
//
//				} while (input != 4);
//			} // option2
//
//		} while (option != 3);
//
//		System.out.println("Chương trình kết thúc. Tạm biệt!");
//		scanner.close();
//	}
//
//	private static void displayUserList(List<UserWithAssignmentStatus> users) {
//		for (UserWithAssignmentStatus item : users) {
//			System.out.println("-------------------------------------");
//			System.out.println("id=" + item.getId() + ", " + "fullname='" + item.getFullName() + "', " + "checked="
//					+ item.isChecked());
//		}
//	}
//
//	private static void displaySearchResults(Map<String, Object> buildingSearchParams, List<String> buildingTypes) {
//
//		System.out.println("----- DANH SÁCH TÒA NHÀ -----");
//
//		List<BuildingSearchResponseDTO> results = buildingController.findBuilding(buildingSearchParams,
//				buildingTypesList);
//
//		for (BuildingSearchResponseDTO item : results) {
//			System.out.println("-------------------------------------");
//			System.out.println("id=" + item.getId() + ", " + "name='" + item.getName() + "', " + "numberOfBasement="
//					+ item.getNumberOfBasement() + ", " + "address='" + item.getAddress() + "', " + "mangerName='"
//					+ item.getManagerName() + "', " + "managerPhone='" + item.getManagerPhone() + "', " + "floorArea="
//					+ item.getFloorArea() + ", " + "rentPrice=" + item.getRentPrice() + ", " + "serviceFee="
//					+ item.getServiceFee() + ", " 
//					//+ "rentalArea='" + item.getRentalArea() + "', " 
//					+ "rentTypes='"+ item.getRentTypes() + "', "
//					+ "rentAreaDescription='" + item.getRentAreaDescription()+"' ");
//		} // for
//
//	}
//
//	private static void displayManagementMenu() {
//		System.out.println("\n----- QUẢN LÝ TÒA NHÀ -----");
//		System.out.println("----- MENU -----");
//		System.out.println("1. Hiển thị tất cả tòa nhà");
//		System.out.println("2. Hiển thị danh sách nhân viên quản lý theo tòa nhà");
//		System.out.println("3. Giao tòa nhà");
//		System.out.println("4. Thoát");
//		System.out.print("Nhập lựa chọn của bạn: ");
//	}
//
//	private static void searchBuilding() {
//
//		Integer condition;
//
//		do {
//			displaySearchMenu();
//			condition = scanner.nextInt();
//			scanner.nextLine();
//
//			switch (condition) {
//			case 1:
//				System.out.print("Nhập tên tòa nhà: ");
//				String name = scanner.nextLine();
//				searchParams.put(SystemConstant.NAME, name);
//				break;
//			case 2:
//				System.out.print("Nhập tên đường: ");
//				String street = scanner.nextLine();
//				searchParams.put(SystemConstant.STREET, street);
//				break;
//			case 3:
//				System.out.print("Nhập tên phường: ");
//				String ward = scanner.nextLine();
//				searchParams.put(SystemConstant.WARD, ward);
//				break;
//			case 4:
//				System.out.print("Nhập tên quận: ");
//				String district_code = scanner.nextLine();
//				searchParams.put(SystemConstant.DISTRICT_CODE, district_code);
//				break;
//			case 5:
//				System.out.print("Nhập Diện tích sàn: ");
//				String floor_area = scanner.nextLine();
//				searchParams.put(SystemConstant.FLOOR_AREA, floor_area);
//				break;
//			case 6:
//				System.out.print("Nhập Diện tích thuê từ: ");
//				String arearentfrom = scanner.nextLine();
//				searchParams.put(SystemConstant.RENT_AREA_FROM, arearentfrom);
//				break;
//			case 7:
//				System.out.print("Nhập Diện tích thuê đén: ");
//				String arearentto = scanner.nextLine();
//				searchParams.put(SystemConstant.RENT_AREA_TO, arearentto);
//				break;
//			case 8:
//				System.out.print("Nhập Giá thuê từ: ");
//				String rentpricefrom = scanner.nextLine();
//				searchParams.put(SystemConstant.RENT_PRICE_FROM, rentpricefrom);
//				break;
//			case 9:
//				System.out.print("Nhập Giá thuê đến: ");
//				String rentpriceto = scanner.nextLine();
//				searchParams.put(SystemConstant.RENT_PRICE_TO, rentpriceto);
//				break;
//			case 10:
//				System.out.print("Nhập Loại tòa nhà: ");
//				String buildingTypes = scanner.nextLine();
//				buildingTypesList = Arrays.stream(buildingTypes.split(",")).map(type -> type.trim())
//						.collect(Collectors.toList());
//				break;
//			case 11:
//				System.out.print("Nhập Nhân viên phụ trách: ");
//				String assignedstaffid = scanner.nextLine();
//				searchParams.put(SystemConstant.STAFF_ID, assignedstaffid);
//				break;
//			case 12:
//				System.out.print("Nhập Tên quản lý: ");
//				String managername = scanner.nextLine();
//				searchParams.put(SystemConstant.MANAGER_NAME, managername);
//				break;
//			case 13:
//				System.out.print("Nhập SDT quản lý: ");
//				String managerphone = scanner.nextLine();
//				searchParams.put(SystemConstant.MANAGER_PHONE, managerphone);
//				break;
//			default:
//				break;
//			}
//
//		} while (condition != 14);
//
//		displaySearchResults(convertToSearchParams(searchParams), buildingTypesList);
//	}
//
//	private static void displaySearchMenu() {
//		System.out.println("\n----- TÌM KIẾM TÒA NHÀ -----");
//		System.out.println("----- Các điều kiện tìm kiếm tòa nhà");
//		System.out.println("1. Tên tòa nhà");
//		System.out.println("2. Tên đường");
//		System.out.println("3. Tên phường");
//		System.out.println("4. Tên quận");
//		System.out.println("5. Diện tích sàn");
//		System.out.println("6. Diện tích thuê từ");
//		System.out.println("7. Diện tích thuê đến");
//		System.out.println("8. Giá thuê từ");
//		System.out.println("9. Giá thuê đến");
//		System.out.println("10. Loại tòa nhà");
//		System.out.println("11. Nhân viên phụ trách");
//		System.out.println("12. Tên quản lý");
//		System.out.println("13. SDT quản lý");
//		System.out.println("14. Nhập xong!");
//		System.out.println("----------------------------");
//		System.out.print("Nhập lựa chọn của bạn: ");
//	}
//
//	private static Map<String, String> initializeSearchParams() {
//		Map<String, String> params = new HashMap<>();
//
//		params.put(SystemConstant.NAME, "");
//		params.put(SystemConstant.FLOOR_AREA, "");
//		params.put(SystemConstant.DISTRICT_CODE, "");
//		params.put(SystemConstant.WARD, "");
//		params.put(SystemConstant.STREET, "");
//		// params.put("numberofbasement", "");
//		// params.put("direction", "");
//		// params.put("level", "");
//		params.put(SystemConstant.RENT_AREA_FROM, "");
//		params.put(SystemConstant.RENT_AREA_TO, "");
//		params.put(SystemConstant.RENT_PRICE_FROM, "");
//		params.put(SystemConstant.RENT_PRICE_TO, "");
//		params.put(SystemConstant.MANAGER_NAME, "");
//		params.put(SystemConstant.MANAGER_PHONE, "");
//		params.put(SystemConstant.STAFF_ID, "");
//
//		return params;
//	}
//
//	private static Map<String, String> emptySearchParams() {
//		Map<String, String> params = new HashMap<>();
//
//		params.put(SystemConstant.NAME, "");
//		params.put(SystemConstant.FLOOR_AREA, "");
//		params.put(SystemConstant.DISTRICT_CODE, "");
//		params.put(SystemConstant.WARD, "");
//		params.put(SystemConstant.STREET, "");
//		// params.put("numberofbasement", "");
//		// params.put("direction", "");
//		// params.put("level", "");
//		params.put(SystemConstant.RENT_AREA_FROM, "");
//		params.put(SystemConstant.RENT_AREA_TO, "");
//		params.put(SystemConstant.RENT_PRICE_FROM, "");
//		params.put(SystemConstant.RENT_PRICE_TO, "");
//		params.put(SystemConstant.MANAGER_NAME, "");
//		params.put(SystemConstant.MANAGER_PHONE, "");
//		params.put(SystemConstant.STAFF_ID, "");
//
//		return params;
//	}
//
//	private static Map<String, Object> convertToSearchParams(Map<String, String> params) {
//		Map<String, Object> buildingSearchParams = new HashMap<>();
//
//		buildingSearchParams.put(SystemConstant.NAME, params.get(SystemConstant.NAME));
//		buildingSearchParams.put(SystemConstant.FLOOR_AREA, getParseInteger(params.get(SystemConstant.FLOOR_AREA)));
//		buildingSearchParams.put(SystemConstant.DISTRICT_CODE, params.get(SystemConstant.DISTRICT_CODE));
//		buildingSearchParams.put(SystemConstant.WARD, params.get(SystemConstant.WARD));
//		buildingSearchParams.put(SystemConstant.STREET, params.get(SystemConstant.STREET));
//		// buildingSearchParams.put("numberofbasement",
//		// getParseInteger(params.get("numberofbasement")));
//		// buildingSearchParams.put("direction", params.get("direction"));
//		// buildingSearchParams.put("level", params.get("level"));
//		buildingSearchParams.put(SystemConstant.RENT_AREA_FROM,
//				getParseInteger(params.get(SystemConstant.RENT_AREA_FROM)));
//		buildingSearchParams.put(SystemConstant.RENT_AREA_TO, getParseInteger(params.get(SystemConstant.RENT_AREA_TO)));
//		buildingSearchParams.put(SystemConstant.RENT_PRICE_FROM,
//				getParseInteger(params.get(SystemConstant.RENT_PRICE_FROM)));
//		buildingSearchParams.put(SystemConstant.RENT_PRICE_TO,
//				getParseInteger(params.get(SystemConstant.RENT_PRICE_TO)));
//		buildingSearchParams.put(SystemConstant.MANAGER_NAME, params.get(SystemConstant.MANAGER_NAME));
//		buildingSearchParams.put(SystemConstant.MANAGER_PHONE, params.get(SystemConstant.MANAGER_PHONE));
//		buildingSearchParams.put(SystemConstant.STAFF_ID, getParseLong(params.get(SystemConstant.STAFF_ID)));
//
//		return buildingSearchParams;
//	}
//
//	private static Integer getParseInteger(String value) {
//		return StringUtils.isNullOrEmpty(value) ? null : Integer.parseInt(value);
//	}
//
//	private static Long getParseLong(String value) {
//		return StringUtils.isNullOrEmpty(value) ? null : Long.parseLong(value);
//	}
//}
