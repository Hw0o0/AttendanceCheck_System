package com.attendanceCheck.service;

import java.util.ArrayList;
import java.util.List;

import com.attendanceCheck.user.Staff;
import com.attendanceCheck.view.Ui;

import static com.attendanceCheck.service.Client.adminManagement;

public class StaffManagement implements Management{
    private final List<Staff> employee = new ArrayList<>();
    private final List<Staff> staffLog = new ArrayList<>(); //정상 출퇴근 직원 일지
    private final Ui ui = new Ui();

    public List<Staff> getEmployee() {
        return employee;
    }

    @Override
    public void createByStaff(int staffNumber, String name, int ssn, String position, String staffState) { // 직원 추가
        Staff staff = new Staff(name, ssn, position, staffNumber, staffState);
        employee.add(staff);
    }


    @Override
    public String findByStaffNumber(int staffNumber) { //검색한 직원 return하기
        for (Staff staff : employee) {
            if (staff.getStaffNumber() == staffNumber) {
                return staff.toString();
            }
        }
        return null;
    }
    @Override
    public void deleteByStaffNumber(int staffNumber) { // 검색 직원 staffList에서 삭제
        int i = 0;
        while (true) {
            try {
                if (employee.get(i).getStaffNumber() == staffNumber) {
                    employee.remove(i);
                    ui.managementOpposition(4);
                    adminManagement();
                }
                i++;
            } catch (IndexOutOfBoundsException e) {
                ui.noexist();
                adminManagement();
            }
        }
    }
    public List<Staff> staffLog() {
        staffLog.clear();
        for (Staff staff : employee) { // 정상 퇴근 사원들
            if (staff.getAttendanceTime() != null && staff.getLeaveWorkTime() != null) {
                staffLog.add(staff);
            } else if (staff.getStaffState().length() == 2) { // 휴가, 병가 처리 된 사원들
                staffLog.add(staff);
            }
        }
        try {
            if (staffLog.get(0) == null) {
            }
        } catch (IndexOutOfBoundsException e) {
            ui.attendance(5);
        }
        return staffLog;
    }

    @Override
    public String toString() {
        return "직원 출.퇴근 일지\n" + staffLog;
    }
}

