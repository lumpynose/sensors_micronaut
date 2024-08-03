package com.objecteffects.persistence.mapper;

import java.util.List;

import com.objecteffects.persistence.domain.Sensor;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public interface SensorMapper {
    @Select("select * from sensors where id=#{id}")
    Sensor findById(long id);

    @Insert("insert into sensors(name) values(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(Sensor genre);

    @Delete("delete from sensors where id=#{id}")
    void deleteById(long id);

    @Update("update sensors set name=#{name} where id=#{id}")
    void update(@Param("id") long id, @Param("name") String name);

    @Select("select * from sensors")
    List<Sensor> findAll();

    @Select("select * from sensors order by ${sort} ${order}")
    List<Sensor> findAllBySortAndOrder(
        @NotNull @Pattern(regexp = "id|name") String sort,
        @NotNull @Pattern(regexp = "asc|ASC|desc|DESC") String order);

    @Select("select * from sensors order by ${sort} ${order} limit ${offset}, ${max}")
    List<Sensor> findAllByOffsetAndMaxAndSortAndOrder(
        @PositiveOrZero int offset,
        @Positive int max,
        @NotNull @Pattern(regexp = "id|name") String sort,
        @NotNull @Pattern(regexp = "asc|ASC|desc|DESC") String order);

    @Select("select * from sensors limit ${offset}, ${max}")
    List<Sensor> findAllByOffsetAndMax(@PositiveOrZero int offset,
        @Positive int max);

    @Select("select * from sensors where hidden=true")
    List<Sensor> findByHidden();
}
