package com.example.perfectproject;

public class Student {
    String fullName;
    String className;
    String mainTeacher;
    int height;
    int weight;
    String birthday;
    String sex;
    int age;
    String bloodType;

    public Student(String fullName, String className, String mainTeacher, int height, int weight, String birthday, String sex, int age, String bloodType) {
        this.fullName = fullName;
        this.className = className;
        this.mainTeacher = mainTeacher;
        this.height = height;
        this.weight = weight;
        this.birthday = birthday;
        this.sex = sex;
        this.age = age;
        this.bloodType = bloodType;
    }

    @Override
    public String toString() {
        return fullName;
    }
}
