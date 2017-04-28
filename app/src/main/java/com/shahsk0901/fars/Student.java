package com.shahsk0901.fars;

public class Student {
    public String netID;
    public String password;
    public String fullName;
    public String mobile;
    public String email;
    public String studentID;
    public String securityQuestion;
    public String securityAnswer;
    public String accountStatus;

    Student() {

    };
    Student(String netID,String password, String fullName, String mobile,String email, String studentID, String securityQuestion, String securityAnswer, String accountStatus) {
        this.netID = netID;
        this.password = password;
        this.fullName = fullName;
        this.mobile = mobile;
        this.email = email;
        this.studentID = studentID;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.accountStatus = accountStatus;
    }
}
