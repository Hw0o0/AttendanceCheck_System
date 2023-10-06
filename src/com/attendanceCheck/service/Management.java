package com.attendanceCheck.service;

public interface Management {
    void createByStaff(int staffNumber, String name, int ssn, String position, String staffState);

    String findByStaffNumber(int staffNumber);

    void deleteByStaffNumber(int staffNumber);

    String toString();
}
