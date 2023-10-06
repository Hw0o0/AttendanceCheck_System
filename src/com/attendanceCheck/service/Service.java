package com.attendanceCheck.service;

public interface Service {

    void adminLogin(int inputStaffId);

    void staffLogin();

    void staffCreate();

    void findAll();

    void findByStaff();

    void deleteBystaff();

    void staffLog();

    int inputStaffNumber();

    String inputStaffName();

    int inputStaffSsn();

    void attendance(int staffId);

    void leaveTime(int staffId);

    void statechoose(int staffId);
}
