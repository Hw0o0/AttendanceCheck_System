package com.attendanceCheck.service;

import com.attendanceCheck.user.Admin;
import com.attendanceCheck.user.Staff;
import com.attendanceCheck.view.Ui;

import java.sql.SQLException;
import java.util.Scanner;

import static com.attendanceCheck.service.Client.*;
import static com.attendanceCheck.service.Client.adminManagement;
import static com.attendanceCheck.staffDetails.Positionkind.*;
import static com.attendanceCheck.staffDetails.StateKind.MEDICALLEAVE;
import static com.attendanceCheck.staffDetails.StateKind.VACATION;

public class JdbcService implements Service{
    private final JdbcManagement management = new JdbcManagement();
    private final Admin admin = new Admin();
    private final Ui ui = new Ui();
    private final Scanner sc = new Scanner(System.in);

    @Override
    public void adminLogin(int inputStaffId) { // 관리자 로그인
        if (admin.getAdminNumber() == inputStaffId) {
            ui.Distinction(1);
            adminManagement();
        } else {
            ui.invalidInput();
            start();
        }
    }
    @Override
    public void staffLogin() { // 사원 로그인
        int i = 0;
        int inputStaffId = Integer.parseInt(sc.nextLine());
        while (true) {
            try {
                if (management.findAll().get(i).getStaffNumber() == inputStaffId) {
                    ui.Distinction(2);
                    staffManagement(inputStaffId);
                } else {
                    i++;
                }
            } catch (IndexOutOfBoundsException e) {
                ui.noexist();
                start();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void staffCreate() {   // 사원직급 선택
        /*int staffId;*/
        management.createByStaff(/*staffId =*/inputStaffNumber(),  inputStaffName(), inputStaffSsn(),intputPosition(/*staffId*/), "-");
        adminManagement();
    }
    @Override
    public void findAll() { //모든 사원 출력
        try {
            if (management.findAll().get(0) == null) {
                ui.noexist();
            }
        } catch (IndexOutOfBoundsException e) {
            ui.attendance(7);
            adminManagement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(management.findAll().toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
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
    @Override
    public void deleteBystaff() { // 검색 사원 정보 삭제
        ui.management(5);
        management.deleteByStaffNumber(Integer.parseInt(sc.nextLine()));
    }

    @Override
    public void staffLog() { //정상 출퇴근 사원 정보 일지
        management.staffLog();
        management.staffLogDB();
        System.out.println(management.getEmployeeLog().toString());
    }

    @Override
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
                for (Staff staff : management.findAll()) {
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return staffNumber;
    }
    @Override
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
    @Override
    public int inputStaffSsn() {// 주민등록번호 만들기
        int ssn;
        while (true) {
            boolean Check = false;
            ui.management(3);
            try {
                ssn = Integer.parseInt(sc.nextLine());
                for (Staff staff : management.findAll()) {
                    if (staff.getSsn() == ssn) {
                        ui.managementOpposition(2);
                        Check = true;
                        break;
                    }
                }
                if (!Check) {
                    break;
                }
            } catch (NumberFormatException e) {
                ui.invalidInput();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ssn;
    }

    public String intputPosition(/*int staffId*/) { //직원 직급 선택
        String position;
        int num;
        while (true) {
            try {
                num = ui.staffPosition();
                /* management.staffstate(num,staffId);*/
                switch (num) {
                    case 1:
                        position = String.valueOf(FORE_MAN);
                        ui.ok();
                        return position;
                    case 2:
                        position = String.valueOf(ASST_MANAGER);
                        ui.ok();
                        return position;
                    case 3:
                        position = String.valueOf(EXAGGERATION);
                        ui.ok();
                        return position;
                }
            } catch (NumberFormatException e) {
                ui.invalidInput();
            }
        }
    }
    @Override
    public void attendance(int staffId) { //사원 출근 처리
        try {
            for (Staff staff : management.findAll()) {
                if (staff.getStaffNumber() == staffId) {
                    if (staff.getAttendanceTime() != null) {
                        ui.attendance(3);
                        break;
                    }
                    staff.setAttendanceTime();
                    management.attendance(staffId);
                    staff.checkAttendanceTime();
                    staff.setStaffState("근무중");
                    ui.attendance(1);
                    break;
                }
            }
        } catch (NullPointerException e) {
            ui.noexist();
        } catch (NumberFormatException e) {
            ui.invalidInput();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void leaveTime(int staffId) { //사원 퇴근 처리
        try {
            for (Staff staff : management.findAll()) {
                if (staff.getStaffNumber() == staffId) {
                    if (staff.getAttendanceTime() == null) {
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
                    management.leavework(staffId);
                    staff.checkLeaveWorkTime();
                    staff.setStaffState("퇴근");
                    ui.attendance(2);
                    break;
                }
            }
        } catch (NullPointerException e) {
            ui.noexist();
        } catch (NumberFormatException e) {
            ui.invalidInput();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void statechoose(int staffId) { //사원 상태 수정
        try {
            switch(ui.staffState()){
                case 1:
                    for (Staff staff : management.findAll()) {
                        if (staff.getStaffNumber() == staffId) {
                            if (staff.getAttendanceTime() == null && staff.getLeaveWorkTime() == null) {
                                management.vacation(staffId);
                                staff.setStaffState(String.valueOf(VACATION));                             //휴가
                                ui.ok();
                                break;
                            } else {
                                ui.invalidInput();
                                break;
                            }
                        }
                    }
                    break;
                case 2:
                    for (Staff staff : management.findAll()) {
                        if (staff.getStaffNumber() == staffId) {
                            if (staff.getAttendanceTime() != null && staff.getLeaveWorkTime() == null) {// 일하는 도중에 병가.
                                staff.setLeaveWorkTime();
                                staff.checkLeaveWorkTime();
                                management.leaveTime(staffId);
                                management.medicalLeave(staffId);
                                staff.setStaffState(String.valueOf(MEDICALLEAVE));
                                ui.ok();
                                break;
                            } else if (staff.getAttendanceTime() == null && staff.getLeaveWorkTime() == null) {
                                management.medicalLeave(staffId);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
