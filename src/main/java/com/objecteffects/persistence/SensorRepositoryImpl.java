package com.objecteffects.persistence;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Singleton
public class SensorRepositoryImpl implements SensorRepository {
    final static Logger log = LoggerFactory.getLogger(SensorRepositoryImpl.class);

    @Inject
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public Sensor findById(long id) {

        try (SqlSession sqlSession = this.sqlSessionFactory.openSession()) {
            return getSensorMapper(sqlSession).findById(id);
        }
    }

    @Override
    public void save(Sensor Sensor) {

        try (SqlSession sqlSession = this.sqlSessionFactory.openSession()) {
            getSensorMapper(sqlSession).save(Sensor);
            sqlSession.commit();
        }
    }

    @Override
    public void deleteById(long id) {

        try (SqlSession sqlSession = this.sqlSessionFactory.openSession()) {
            getSensorMapper(sqlSession).deleteById(id);
            sqlSession.commit();
        }
    }

    @Override
    public void update(long id, String name) {

        try (SqlSession sqlSession = this.sqlSessionFactory.openSession()) {
            getSensorMapper(sqlSession).update(id, name);
            sqlSession.commit();
        }
    }

    @Override
    public List<Sensor> findAll() {

        try (SqlSession sqlSession = this.sqlSessionFactory.openSession()) {
            return getSensorMapper(sqlSession).findAll();
        }
    }

    @Override
    public List<Sensor> findAllBySortAndOrder(
        @NotNull @Pattern(regexp = "id|name") String sort,
        @NotNull @Pattern(regexp = "asc|ASC|desc|DESC") String order) {

        try (SqlSession sqlSession = this.sqlSessionFactory.openSession()) {
            return getSensorMapper(sqlSession).findAllBySortAndOrder(sort,
                order);
        }
    }

    @Override
    public List<Sensor> findAllByOffsetAndMaxAndSortAndOrder(
        @PositiveOrZero int offset,
        @Positive int max,
        @NotNull @Pattern(regexp = "id|name") String sort,
        @NotNull @Pattern(regexp = "asc|ASC|desc|DESC") String order) {

        try (SqlSession sqlSession = this.sqlSessionFactory.openSession()) {
            return getSensorMapper(sqlSession)
                .findAllByOffsetAndMaxAndSortAndOrder(offset, max, sort, order);
        }
    }

    @Override
    public List<Sensor> findAllByOffsetAndMax(@PositiveOrZero int offset,
        @Positive int max) {

        try (SqlSession sqlSession = this.sqlSessionFactory.openSession()) {
            return getSensorMapper(sqlSession).findAllByOffsetAndMax(offset,
                max);
        }
    }

    @SuppressWarnings("static-method")
    private SensorRepository getSensorMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(SensorRepository.class);
    }
}
