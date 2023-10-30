package com.example.mapper;

import com.example.pojo.Facility;
import com.example.pojo.Value;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FacilityMapper {
    @Select("select distinct ${field} as value from facility order by value")
    List<Value> searchField(String field);

    /**
     * 获得当前设备类型中最大的流水号以生成新的规格的流水号
     * @param name
     * @return
     */
    @Select("SELECT Max(batch) FROM facility where name = #{name} and spec != #{spec}")
    Integer getMaxBatch(String name);

    /**
     * 获得相同设备类型中最大的流水号以生成新的规格的流水号
     * @param name
     * @return
     */
    @Select("SELECT Max(batchSame) FROM facility where name = #{name} and spec = #{spec}")
    Integer getMaxBatchSame(String name, String spec);

    @Insert("insert into facility(name, spec, station ,section,serialNo,purchaseTime,supplier,contact,contactNo,status,warranty,dailyMaintenance,firstLevelMaintenance,secondLevelMaintenance,attachment, batch,batchSame) values " +
            "(#{name}, #{spec}, #{station} ,#{section},#{serialNo},#{purchaseTime},#{supplier},#{contact},#{contactNo},#{status},#{warranty},#{dailyMaintenance},#{firstLevelMaintenance},#{secondLevelMaintenance},#{attachment},#{batch},#{batchSame})")
    void insert(Facility facility);


    List<Facility> list(String name, String spec, String section, String status, String supplier);

    @Delete("delete from facility where id = #{facility_id}")
    void deleteById(Integer facility_id);

    @Select("select * from facility where id = #{facility_id}")
    Facility getById(Integer facility_id);

    void update(Facility facility);

    @Update("update facility set status = #{status} where id = #{facility_id}")
    void updateStatus(Integer facility_id, String status);
}
