package com.attendanceCheck.service;

import com.attendanceCheck.user.Staff;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcManagement implements Management {


    // DBMS와 연결을 위한 Connection 객체
    Connection con = null;
    // 통신하기 위한 PreparedStatement 객체,sql 실행을 위한 객체
    PreparedStatement psmt = null;
    ResultSet rs = null;

    public List<Staff> getEmployeeLog() {
        return employeeLog;
    }

//    public void setEmployeeLog(List<Staff> employeeLog) {
//        this.employeeLog = employeeLog;
//    }

    List<Staff> employeeLog = new ArrayList<>(); // 정상 퇴근 직원 정보를 넣기 위한 일지


          //String JDBC_DRIVER = "com.mysql.jdbc.Driver"; //드라이버
            String DB_url = "jdbc:mysql://localhost:3306/attendance_check"; // 접속할 DB 서버
            String User_Name = "root";
            String PassWord = "1234";



    public void closeConnection() {
        try {
            if (con != null) {
                con.close();
            }
            if (psmt != null) {
                psmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createByStaff(int staffNumber,  String name, int ssn,String position, String staffState) {
        try {
            con = DriverManager.getConnection(DB_url, User_Name, PassWord);//계정 연결
            String sql = "insert into employee(staff_number,name,ssn,position,staff_state)" + "values(?,?,?,?,?)";

            psmt = con.prepareStatement(sql);
            psmt.setInt(1, staffNumber);
            psmt.setString(2,name);
            psmt.setInt(3, ssn);
            psmt.setString(4, position);
            psmt.setString(5, staffState);

            psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
    }

    @Override
    public String findByStaffNumber(int staffNumber) {

        Staff staff = new Staff();
        try {
            con = DriverManager.getConnection(DB_url, User_Name, PassWord);//계정 연결
            String sql = "select * from employee where staff_number = ?";

            psmt = con.prepareStatement(sql);
            psmt.setInt(1,staffNumber);
            rs = psmt.executeQuery();
            staff.setSsn(rs.getInt("ssn"));
            if(rs.next()){
                staff.setStaffNumber(rs.getInt("staff_number"));
                staff.setName(rs.getString("name"));
                staff.setPosition(rs.getString("position"));
                staff.setStaffState(rs.getString("staff_state"));
                staff.setAttendanceTime(rs.getDate("attendancetime"));
                staff.setLeaveWorkTime(rs.getDate("leaveworktime"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return staff.toString();
    }
    public List<Staff> findAll() throws SQLException { //모든 사원 출력
        List<Staff> employee = new ArrayList<>();

        try {
            con = DriverManager.getConnection(DB_url, User_Name, PassWord);//계정 연결

            String sql = "select * from employee";
            psmt = con.prepareStatement(sql);
            rs = psmt.executeQuery();
            while (rs.next()){
            Staff staff = new Staff();
            staff.setStaffNumber(rs.getInt("staff_number"));
            staff.setName(rs.getString("name"));
            staff.setSsn(rs.getInt("ssn"));
            staff.setPosition(rs.getString("position"));
            staff.setStaffState(rs.getString("staff_state"));
            staff.setAttendanceTime(rs.getDate("attendancetime"));
            staff.setLeaveWorkTime(rs.getDate("leaveworktime"));

            employee.add(staff);
            }
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return employee;
    }
    @Override
    public void deleteByStaffNumber(int staffNumber) {
        try {

            con = DriverManager.getConnection(DB_url, User_Name, PassWord);//계정 연결
            String sql = "select * from employee where staff_number = ?";
            psmt = con.prepareStatement(sql);
            psmt.setInt(1,staffNumber);
            rs = psmt.executeQuery();
            while(rs.next()){
                String sql1 = "delete from employee where staff_number = ?";
                psmt = con.prepareStatement(sql1);
                psmt.setInt(1,staffNumber);
                psmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void staffLog() { // 정상 퇴근 인원 arrayList에 정보 입력
        try {
            con = DriverManager.getConnection(DB_url, User_Name, PassWord);//계정 연결
            String sql = "select * from employee where leaveworktime is not null";

                psmt = con.prepareStatement(sql);
                rs = psmt.executeQuery();
                while(rs.next()){
                Staff staff = new Staff();
                staff.setStaffNumber(rs.getInt("staff_number"));
                staff.setName(rs.getString("name"));
                staff.setSsn(rs.getInt("ssn"));
                staff.setPosition(rs.getString("position"));
                staff.setStaffState(rs.getString("staff_state"));
                staff.setAttendanceTime(rs.getDate("attendancetime"));
                staff.setLeaveWorkTime(rs.getDate("leaveworktime"));

                employeeLog.add(staff);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
    }
    public void staffLogDB() { // DB에 정상 퇴근 인원 정보 입력
        try {
            con = DriverManager.getConnection(DB_url, User_Name, PassWord);//계정 연결
            String sql = "insert into employeelog(employeeNumber,name,ssn,position,employee_state,attendancetime,leaveworktime)" + "values(?,?,?,?,?,?,?)";
            psmt = con.prepareStatement(sql);
            for(Staff employee : employeeLog) {
                psmt.setInt(1, employee.getStaffNumber());
                psmt.setString(2, employee.getName());
                psmt.setInt(3, employee.getSsn());
                psmt.setString(4, employee.getPosition());
                psmt.setString(5, employee.getStaffState());
                psmt.setDate(6, (Date) employee.getAttendanceTime());
                psmt.setDate(7, (Date) employee.getLeaveWorkTime());

                psmt.executeUpdate();
            }
            } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
    }
        public void attendance(int staffNumber){ //DB에 출근 시간 넣기 위한 메소드,동시에 직원 상태도 근무중으로 변경해준다
        try {
            con = DriverManager.getConnection(DB_url,User_Name,PassWord);
            String sql = "Update employee Set attendancetime = CURRENT_TIMESTAMP(),staff_state = '근무중' where staff_number = ?";
            psmt = con.prepareStatement(sql);
            psmt.setInt(1,staffNumber);
            psmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
    }
    public void leavework(int staffNumber){//DB에 퇴근 시간 넣기 위한 메소드,동시에 직원 상태도 퇴근으로 변경해준다
        try {
            con = DriverManager.getConnection(DB_url,User_Name,PassWord);
            String sql = "Update employee Set leaveworktime = CURRENT_TIMESTAMP(), staff_state = '퇴근' where staff_number = ?";
            psmt = con.prepareStatement(sql);
            psmt.setInt(1,staffNumber);
            psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
    }
    public void leaveTime(int staffNumber){// DB에 퇴근 시간만 넣기 위한 메소드
        try {
            con = DriverManager.getConnection(DB_url,User_Name,PassWord);
            String sql = "Update employee Set leaveworktime = CURRENT_TIMESTAMP where staff_number = ?";
            psmt = con.prepareStatement(sql);
            psmt.setInt(1,staffNumber);
            psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
    }
    public void vacation(int staffNumber){// DB에 휴가를 한 직원  근무 상태  수정
        try {
            con = DriverManager.getConnection(DB_url,User_Name,PassWord);
            String sql = "Update employee Set staff_state = '휴가' where staff_number = ?";
            psmt = con.prepareStatement(sql);
            psmt.setInt(1,staffNumber);
            psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
    }
    public void medicalLeave(int staffNumber){// DB에 병가를 한 직원  근무 상태 수정
        try {
            con = DriverManager.getConnection(DB_url,User_Name,PassWord);
            String sql = "Update employee Set staff_state = '병가' where staff_number = ?";
            psmt = con.prepareStatement(sql);
            psmt.setInt(1,staffNumber);
            psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
    }
}

