package com.jeonbuk.mchms.service.calnum;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CalNumMapper {

    @Select("SELECT Date FROM Cal_Num WHERE USER_ID = #{id}")
    String selectDateById(String id);

    @Insert("INSERT INTO Cal_Num(`USER_ID`, `RELIC_NUM`, `DATE`) VALUES ('${id}', 1, '${today}')")
    void setCalNum(String id, String today);

    @Update("UPDATE Cal_Num SET RELIC_NUM = 1, DATE = #{today} WHERE USER_ID = #{id}")
    void changeCalNum(String id, String today);

    @Select("SELECT RELIC_NUM FROM Cal_Num WHERE USER_ID = #{id}")
    String selectRelicNumber(String id);

    @Update("UPDATE Cal_Num SET RELIC_NUM = #{Cal_Num}")
    void updateCalNum(String Cal_Num);
}
