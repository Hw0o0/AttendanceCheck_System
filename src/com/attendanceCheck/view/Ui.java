package com.attendanceCheck.view;

import java.util.Scanner;

public class Ui {
    Scanner sc = new Scanner(System.in);

    public int loginSelect() {
        System.out.println("1.관리자 로그인 2.직원 로그인");
        System.out.println("1~2의 로그인 방식을 선택하세요.");
        return Integer.parseInt(sc.nextLine());
    }

    public int adminManagementMenu() {
        System.out.println("1.신입 사원 정보 입력 2.전체 사원 정보 출력 3.사원 정보 검색 4.사원 정보 삭제 5.사원 출근 일지 출력 6.로그아웃");
        System.out.println("1~6의 메뉴를 선택하세요.");
        return Integer.parseInt(sc.nextLine());
    }

    public int attendanceMenu() {
        System.out.println("1.출근 처리 2.퇴근 처리 3.직원 상태 변경 4.로그아웃");
        System.out.println("1~4의 메뉴를 선택하세요.");
        return Integer.parseInt(sc.nextLine());
    }

    public int staffPosition() {
        System.out.println("1. 주임 2. 대리 3. 과장");
        System.out.println("직급을 선택해주세요.");
        return Integer.parseInt(sc.nextLine());

    }

    public void loginDistinction(int num) {
        if (num == 1) {
            System.out.println("관리자 ID를 입력하십시오.");
        } else if (num == 2) {
            System.out.println("사원 ID를 입력하십시오.");
        }
    }

    public int staffState() {
        System.out.println("1. 휴가 2. 병가 3. 돌아가기");
        System.out.println("결근명을 선택해주세요.");
        return Integer.parseInt(sc.nextLine());
    }

    public void Distinction(int num) {
        if (num == 1) {
            System.out.println("\n관리자 로그인되었습니다.\n");
        } else if (num == 2) {
            System.out.println("\n직원 로그인되었습니다.\n");
        } else if (num == 3) {
            System.out.println("로그아웃 하였습니다\n");
        }
    }

    public void attendance(int num) {
        if (num == 1) {
            System.out.println("출근이 정상적으로 되었습니다.");
        } else if (num == 2) {
            System.out.println("퇴근이 정상적으로 되었습니다.");
        } else if (num == 3) {
            System.out.println("이미 출근 처리가 되어있습니다..");
        } else if (num == 4) {
            System.out.println("이미 퇴근 처리가 되어있습니다.");
        } else if (num == 5) {
            System.out.println("오늘 출.퇴근 일지에 저장된 내용이 없습니다.\n");
        } else if (num == 6) {
            System.out.println("이미 병가 처리가 되어있습니다.");
        } else if (num == 7) {
            System.out.println("출력할 사원 정보가 없습니다.\n");
        }
    }

    public void noexist() {
        System.out.println("사원ID가 존재하지 않습니다.");
    }

    public void ok() {
        System.out.println("완료되었습니다.\n");
    }

    public void invalidInput() {
        System.out.println("잘못된 접근입니다.\n");
    }

    public void noattendance() {
        System.out.println("출근이 되어있지 않습니다.");
        System.out.println("출근 재확인 부탁드립니다.\n");
    }

    public int management(int num) {
        while (true) {
            switch (num) {
                case 1:
                    System.out.println("사원 ID를 입력하세요 :");
                    break;
                case 2:
                    System.out.println("사원 이름을 입력하세요 :");
                    break;
                case 3:
                    System.out.println("사원 주민등록번호를 입력하세요 :");
                    break;
                case 4:
                    System.out.println("검색할 사원 ID를 입력 해주세요 :");
                    break;
                case 5:
                    System.out.println("삭제할 사원 ID를 입력 해주세요 :");
                    break;
                default:
                    invalidInput();
            }
            return num;
        }
    }

    public int managementOpposition(int num) {
        while (true) {
            switch (num) {
                case 1:
                    System.out.println("중복된 사원 ID입니다.");
                    break;
                case 2:
                    System.out.println("잘못된 주민등록번호입니다. 다시 입력해주세요.");
                    break;
                case 3:
                    System.out.println("입력하신 사원 ID 정보를 검색했습니다.");
                    break;
                case 4:
                    System.out.println("입력하신 사원 ID 정보가 삭제되었습니다.\n");
                    break;
                case 5:
                    System.out.println("잘못된 이름입니다. 다시 입력해주세요.");
                    break;
                default:
                    invalidInput();
            }
            return num;
        }
    }
}