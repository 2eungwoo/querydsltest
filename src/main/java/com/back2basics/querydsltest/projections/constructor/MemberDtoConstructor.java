package com.back2basics.querydsltest.projections.constructor;

public class MemberDtoConstructor {
    private final String username;
    private final int age;

    public MemberDtoConstructor(String username, int age) {
        this.username = username;
        this.age = age;
    }
}