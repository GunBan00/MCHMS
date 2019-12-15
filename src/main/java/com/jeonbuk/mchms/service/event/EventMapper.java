package com.jeonbuk.mchms.service.event;

import com.jeonbuk.mchms.domain.EventDomain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EventMapper {

    @Select("SELECT Event_File1 as eventFile1, Event_File2 as eventFile2, Event_File3 as eventFile3, Event_File4 as eventFile4, Event_File5 as eventFile5 FROM Event WHERE Data_ID = #{id}")
    List<EventDomain> getEventInfo(String id);

}
