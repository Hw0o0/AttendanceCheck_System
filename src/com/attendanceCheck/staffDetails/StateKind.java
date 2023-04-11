package com.attendanceCheck.staffDetails;

public enum StateKind {
    VACATION("휴가"), MEDICALLEAVE("병가");
    private final String state;

    StateKind(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return state;
    }
}
