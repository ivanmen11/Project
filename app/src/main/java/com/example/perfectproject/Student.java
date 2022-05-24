package com.example.perfectproject;

public class Student {
    String fullName;
    int className;
    String mainTeacher;
    int height;
    int weight;
    String birthday;
    String sex;
    int age;
    String bloodType;
    String someInfo;

    public Student(String fullName, int className, String mainTeacher, int height, int weight, String birthday, String sex, int age, String bloodType, String someInfo) {
        this.fullName = fullName;
        this.className = className;
        this.mainTeacher = mainTeacher;
        this.height = height;
        this.weight = weight;
        this.birthday = birthday;
        this.sex = sex;
        this.age = age;
        this.bloodType = bloodType;
        this.someInfo = someInfo;
    }

    @Override
    public String toString() {
        return fullName;
    }
}
