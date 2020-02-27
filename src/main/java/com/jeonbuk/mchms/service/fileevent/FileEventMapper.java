package com.jeonbuk.mchms.service.fileevent;

import com.jeonbuk.mchms.domain.FileEventDomain;
import org.apache.ibatis.annotations.Select;

public interface FileEventMapper {
    @Select("SELECT FILES WHERE FILEEVENT_ID = #{id}")
    FileEventDomain getFilesNameFromDataID(int id);
}
