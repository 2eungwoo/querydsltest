package com.back2basics.querydsltest;

import static org.assertj.core.api.Assertions.assertThat;

import com.back2basics.querydsltest.entity.MemberEntity;
import com.back2basics.querydsltest.entity.QMemberEntity;
import com.back2basics.querydsltest.projections.bean.MemberDtoBean;
import com.back2basics.querydsltest.projections.constructor.MemberDtoConstructor;
import com.back2basics.querydsltest.projections.fields.MemberDtoFields;
import com.back2basics.querydsltest.projections.queryprojection.MemberDtoQueryProjection;
import com.back2basics.querydsltest.projections.queryprojection.QMemberDtoQueryProjection;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = QuerydsltestApplication.class)
@Transactional
public class MemberProjectionTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    JPAQueryFactory queryFactory;

    QMemberEntity member = QMemberEntity.memberEntity;

    @BeforeEach
    void setUp() {
        entityManager.persist(MemberEntity.create("bread", 20));
        entityManager.persist(MemberEntity.create("coffee", 30));
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName(" constructor 방식 projection ")
    void testConstructorProjection() {
        List<MemberDtoConstructor> result = queryFactory
            .select(Projections.constructor(MemberDtoConstructor.class,
                member.username,
                member.age))
            .from(member)
            .fetch();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getUsername()).isEqualTo("bread");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.get(1).getUsername()).isEqualTo("coffee");
        assertThat(result.get(1).getAge()).isEqualTo(30);
    }

    @Test
    @DisplayName(" fields 방식 projection ")
    void testFieldsProjection() {
        List<MemberDtoFields> result = queryFactory
            .select(Projections.fields(MemberDtoFields.class,
                member.username,
                member.age))
            .from(member)
            .fetch();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getUsername()).isEqualTo("bread");
        assertThat(result.get(0).age).isEqualTo(20);
        assertThat(result.get(1).getUsername()).isEqualTo("coffee");
        assertThat(result.get(1).age).isEqualTo(30);
    }

    @Test
    @DisplayName(" bean 방식 projection ")
    void testBeanProjection() {
        List<MemberDtoBean> result = queryFactory
            .select(Projections.bean(MemberDtoBean.class,
                member.username,
                member.age))
            .from(member)
            .fetch();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getUsername()).isEqualTo("bread");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.get(1).getUsername()).isEqualTo("coffee");
        assertThat(result.get(1).getAge()).isEqualTo(30);
    }

    @Test
    @DisplayName(" @QueryProjection 방식 projection ")
    void testQueryProjection() {
        List<MemberDtoQueryProjection> result = queryFactory
            .select(new QMemberDtoQueryProjection(
                member.username,
                member.age))
            .from(member)
            .fetch();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getUsername()).isEqualTo("bread");
        assertThat(result.get(0).getAge()).isEqualTo(20);
        assertThat(result.get(1).getUsername()).isEqualTo("coffee");
        assertThat(result.get(1).getAge()).isEqualTo(30);
    }
}
