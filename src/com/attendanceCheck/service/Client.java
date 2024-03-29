package com.attendanceCheck.service;


import com.attendanceCheck.view.Ui;

import java.util.Scanner;

public class Client {

    private static final Ui ui = new Ui();

    //private static StaffService service = new StaffService();
    private static final JdbcService service = new JdbcService();
    private static final Scanner sc = new Scanner(System.in);

    public static void start() { // 로그인 페이지
        switch (ui.loginSelect()) {
            case 1:
                ui.loginDistinction(1);
                service.adminLogin(Integer.parseInt(sc.nextLine()));
                break;
            case 2:// 사원 로그인
                ui.loginDistinction(2);
                service.staffLogin();
                break;
            default:
                ui.invalidInput();
                start();
                break;
        }
    }

    public static void adminManagement() { // 관리자 기능
        while (true) {
            try {
                switch (ui.adminManagementMenu()) {
                    case 1://신입 사원 정보 입력
                        service.staffCreate();
                        break;
                    case 2://전체 사원 정보 출력
                        service.findAll();
                        break;
                    case 3://사원 정보 검색
                        service.findByStaff();
                        break;
                    case 4://사원 퇴직 처리
                        service.deleteBystaff();
                        break;
                    case 5: // 사원 출근 일지
                        service.staffLog();
                        break;
                    case 6:
                        ui.Distinction(3);
                        start();
                    default:
                        ui.invalidInput();
                }
            } catch (NumberFormatException e) {
                ui.invalidInput();
                adminManagement();
            }
        }
    }

    public static void staffManagement(int staffId) { //직원 사용 기능
        while (true) {
            switch (ui.attendanceMenu()) {
                case 1:// 출근
                    service.attendance(staffId);
                    break;
                case 2:// 퇴근
                    service.leaveTime(staffId);
                    break;
                case 3: // 직원 결근명 변경
                    service.statechoose(staffId);
                    break;
                case 4: //로그아웃
                    ui.Distinction(3);
                    start();
                    break;
                default:
                    ui.invalidInput();
            }
        }
    }
}


