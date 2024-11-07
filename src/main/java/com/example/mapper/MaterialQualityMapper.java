package com.example.mapper;

import com.example.pojo.MaterialQuality;
import com.example.pojo.Value;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MaterialQualityMapper {
    List<MaterialQuality> list(String name, String spec);

    List<Value> search(String name, String spec, String field);

    @Insert("insert into material_quality(name, spec, batch, surface, cutFace, length, width, thickness, flatness, derust, rAngle, crossSection, inspector) " +
            "VALUES (#{name}, #{spec}, #{batch}, #{surface}, #{cutFace}, #{length}, #{width}, #{thickness}, #{flatness}, #{derust}, #{rAngle}, #{crossSection}, #{inspector})")
    void insert(MaterialQuality materialQuality);
}
