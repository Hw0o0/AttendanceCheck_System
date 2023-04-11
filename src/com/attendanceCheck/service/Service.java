package com.attendanceCheck.service;

import com.attendanceCheck.user.Admin;
import com.attendanceCheck.user.Staff;
import com.attendanceCheck.view.Ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.attendanceCheck.service.Client.*;
import static com.attendanceCheck.staffDetails.Positionkind.*;
import static com.attendanceCheck.staffDetails.StateKind.MEDICALLEAVE;
import static com.attendanceCheck.staffDetails.StateKind.VACATION;

public class Service {
    private Management management = new Management();
    private List<Staff> staffList = new ArrayList<>(); //employee정보
    private Admin admin = new Admin();
    private Ui ui = new Ui();
    private Scanner sc = new Scanner(System.in);


    public void adminLogin(int inputStaffId) { // 관리자 로그인
        if (admin.getAdminNumber() == inputStaffId) {
            ui.Distinction(1);
            adminManagement();
        } else {
            ui.invalidInput();
            start();
        }
    }
    public void staffLogin() { // 사원 로그인
        int i = 0;
        int inputStaffId = Integer.parseInt(sc.nextLine());
        while (true) {
            try {
                if (staffList.get(i).getStaffNumber() == inputStaffId) {
                    ui.Distinction(2);
                    staffManagement(inputStaffId);
                } else {
                    i++;
                }
            } catch (IndexOutOfBoundsException e) {
                ui.noexist();
                start();
            }
        }
    }
    public void staffCreate() {   // 사원직급 선택
        management.createByStaff(inputStaffNumber(), intputPosition(), inputStaffName(), inputStaffSsn(), "-");
        staffList = management.getEmployee();
        adminManagement();
    }

    public void findAll() { //모든 사원 출력
            try {
                if (staffList.get(0) == null) {
                }
            } catch (IndexOutOfBoundsException e) {
                ui.attendance(7);
                adminManagement();
            }
        System.out.println(staffList.toString());
    }



    public void findByStaff() { // 검색 사원 출력
        ui.management(4);
        try {
            System.out.println(management.findByStaffNumber(Integer.parseInt(sc.nextLine())));
            ui.managementOpposition(3);
        } catch (NumberFormatException e) {
            ui.invalidInput();
        } catch (NullPointerException e) {
            ui.noexist();
        }
    }

    public void deleteBystaff() { // 검색 사원 정보 삭제
        ui.management(5);
        management.deleteByStaffNumber(Integer.parseInt(sc.nextLine()));
        staffList = management.getEmployee();
    }



    public void staffLog() { //정상 출퇴근 사원 정보 일지
        List<Staff> employeeLog = management.staffLog();
        System.out.println(employeeLog.toString());
    }

    public int inputStaffNumber() {// 사원ID 만들기
        int staffNumber;
        while (true) {
            ui.management(1);
            boolean Check = false;
            try {
                staffNumber = Integer.parseInt(sc.nextLine());
                if (staffNumber < 0) {
                    ui.invalidInput();
                    break;
                }
                for (Staff staff : staffList) {
                    if (staff.getStaffNumber() == staffNumber) {
                        ui.managementOpposition(1);
                        Check = true;
                        break;
                    }
                }
                if (!Check) { //true면 멈춰라
                    break;
                }
            } catch (NumberFormatException e) {
                ui.invalidInput();
            } catch (NullPointerException e) {
                ui.noexist();
            }
        }
        return staffNumber;
    }

    public String inputStaffName() {// 사원 이름 만들기
        String name;
        while (true) {
            ui.management(2);
            name = sc.nextLine();
            if (name.length() > 0) {
               break;
            }
            ui.invalidInput();
        }
        return name;
    }

    public int inputStaffSsn() {// 주민등록번호 만들기
        int ssn;
        while (true) {
            boolean Check = false;
            ui.management(3);
            try {
                ssn = Integer.parseInt(sc.nextLine());
                for (Staff staff : staffList) {
                    if (staff.getSsn() == ssn) {
                        ui.managementOpposition(2);
                        Check = true;
                        break;
                    }
                }
                if (!Check) {
                    ui.ok();
                    break;
                }
            } catch (NumberFormatException e) {
                ui.invalidInput();
            }
        }
        return ssn;
    }

    public String intputPosition() { //직원 직급 선택
        String position;
        int num;
        while(true) {
            try {
                num = ui.staffPosition();
                if (num == 1) {
                    position = String.valueOf(FORE_MAN);
                    return position;
                } else if (num == 2) {
                    position = String.valueOf(ASST_MANAGER);
                    return position;
                } else if (num == 3) {
                    position = String.valueOf(EXAGGERATION);
                    return position;
                }
            } catch (NumberFormatException e) {
                ui.invalidInput();
            }
        }
    }
    public void attendance(int staffId) { //사원 출근 처리
        try {
            for (Staff staff : staffList) {
                if (staff.getStaffNumber() == staffId) {
                    if (staff.getAttendanceTime() != null) {
                        ui.attendance(3);
                        break;
                    }
                    staff.setAttendanceTime();
                    staff.checkAttendanceTime();
                    staff.setStaffState("근무중");
                    System.out.println(staff.toString());
                    ui.attendance(1);
                    break;
                }
            }
        } catch (NullPointerException e) {
            ui.noexist();
        } catch (NumberFormatException e) {
            ui.invalidInput();
        }
    }

    public void leaveTime(int staffId) { //사원 퇴근 처리
        try {
            for (Staff staff : staffList) {
                if (staff.getStaffNumber() == staffId) {
                    if (staff.getAttendanceTime() == null) {
                        System.out.println(staff.toString());
                        ui.noattendance();
                        break;
                    } else if (staff.getLeaveWorkTime() != null) {
                        ui.attendance(4);
                        break;
                    } else if (staff.getStaffState().length() == 2) {
                        ui.attendance(6);
                        break;
                    }
                    staff.setLeaveWorkTime();
                    staff.checkLeaveWorkTime();
                    staff.setStaffState("퇴근");
                    System.out.println(staff.toString());
                    ui.attendance(2);
                    break;
                }
            }
        } catch (NullPointerException e) {
            ui.noexist();
        } catch (NumberFormatException e) {
            ui.invalidInput();
        }
    }

    public void statechoose(int staffId) { //사원 상태 수정
        Scanner sc = new Scanner(System.in);
        ui.staffState();
        try {
            int num = Integer.parseInt(sc.nextLine());
            if (num == 1) {
                for (Staff staff : staffList) {
                    if (staff.getStaffNumber() == staffId) {
                        if (staff.getAttendanceTime() == null && staff.getLeaveWorkTime() == null) {
                            staff.setStaffState(String.valueOf(VACATION));                             //휴가
                            ui.ok();
                            break;
                        } else {
                            ui.invalidInput();
                            break;
                        }
                    }
                }
            } else if (num == 2) {
                for (Staff staff : staffList) {
                    if (staff.getStaffNumber() == staffId) {
                        if (staff.getAttendanceTime() != null && staff.getLeaveWorkTime() == null) {// 일하는 도중에 병가.
                            staff.setLeaveWorkTime();
                            staff.checkLeaveWorkTime();
                            staff.setStaffState(String.valueOf(MEDICALLEAVE));
                            ui.ok();
                            break;
                        } else if (staff.getAttendanceTime() == null && staff.getLeaveWorkTime() == null) {
                            staff.setStaffState(String.valueOf(MEDICALLEAVE));
                            ui.ok();
                            break;
                        } else {
                            ui.invalidInput();
                            break;
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            ui.invalidInput();
        }
    }
}
