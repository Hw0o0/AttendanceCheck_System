package com.attendanceCheck.staffDetails;

public enum Positionkind {
    FORE_MAN("주임"), ASST_MANAGER("대리"), EXAGGERATION("과장");
    private final String position;

    Positionkind(String position) {
        this.position = position;
    }
    @Override
    public String toString() {
        return position;
    }

}
