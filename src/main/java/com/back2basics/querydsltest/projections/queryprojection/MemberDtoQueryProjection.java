package com.back2basics.querydsltest.projections.queryprojection;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class MemberDtoQueryProjection {
    private final String username;
    private final int age;

    @QueryProjection
    public MemberDtoQueryProjection(String username, int age) {
        this.username = username;
        this.age = age;
    }
}