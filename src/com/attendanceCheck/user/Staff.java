package com.attendanceCheck.user;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Staff {
    private String name;
    private  int ssn;
    private String position;
    private int staffNumber;
    private Date attendanceTime;
    private Date leaveWorkTime;
    private String staffState;

    public Staff(String name, int ssn, String position, int staffNumber, String staffState) {
        this.name = name;
        this.ssn = ssn;
        this.position = position;
        this.staffNumber = staffNumber;
        this.staffState = staffState;
    }

    public Staff() {

    }

    public int getSsn() {
        return ssn;
    }
    public void setSsn(int ssn) {
        this.ssn = ssn;
    }


    public int getStaffNumber() {
        return staffNumber;
    }


    public Date getAttendanceTime() {
        return attendanceTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setStaffNumber(int staffNumber) {
        this.staffNumber = staffNumber;
    }

    public void setAttendanceTime(Date attendanceTime) {
        this.attendanceTime = attendanceTime;
    }

    public void setLeaveWorkTime(Date leaveWorkTime) {
        this.leaveWorkTime = leaveWorkTime;
    }

    public void setAttendanceTime() {
        attendanceTime = new Date();
    }

    public Date getLeaveWorkTime() {
        return leaveWorkTime;
    }

    public void setLeaveWorkTime() {
        leaveWorkTime = new Date();
    }


    public String getStaffState() {
        return staffState;
    }

    public void setStaffState(String staffState) {
        this.staffState = staffState;
    }

    public String checkAttendanceTime() {
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH시간 mm분");
        return sFormat.format(getAttendanceTime());
    }

    public String checkLeaveWorkTime() {
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH시간 mm분");
        return sFormat.format(getLeaveWorkTime());
    }


    @Override
    public String toString() {
        if (attendanceTime != null && leaveWorkTime == null) { //출근 했을때
            return "\n 직원 정보 " +
                    "\n 직위     = " + position +
                    "\n 사원 번호 = " + staffNumber +
                    "\n 이름 = " + name +
                    "\n 출근 시간 = " + checkAttendanceTime() +
                    "\n 퇴근 시간 = " + leaveWorkTime +
                    "\n 직원 상태 = " + staffState +
                    '\n';
        } else if (attendanceTime == null && leaveWorkTime == null) { // 출근을 하지않았을때
            if (staffState.length() == 2) {
                return "\n 직원 정보 " +
                        "\n 직위     = " + position +
                        "\n 사원 번호 = " + staffNumber +
                        "\n 이름 = " + name +
                        "\n 직원 상태 = " + staffState +
                        '\n';
            } else {
                return "\n 직원 정보 " +
                        "\n 직위     = " + position +
                        "\n 사원 번호 = " + staffNumber +
                        "\n 이름 = " + name +
                        '\n';
            }
        } else {       // 정상 퇴근 처리
            return "\n 직원 정보 " +
                    "\n 직위     = " + position +
                    "\n 사원 번호 = " + staffNumber +
                    "\n 이름 = " + name +
                    "\n 출근 시간 = " + checkAttendanceTime() +
                    "\n 퇴근 시간 = " + checkLeaveWorkTime() +
                    "\n 직원 상태 = " + staffState +
                    '\n';
        }
    }
}

