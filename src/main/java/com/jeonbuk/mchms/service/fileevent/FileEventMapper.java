package com.jeonbuk.mchms.service.fileevent;

import com.jeonbuk.mchms.domain.FileEventDomain;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FileEventMapper {
    @Select("SELECT FILES as files, COUNT as count FROM FILEEVENT WHERE DATA_ID = #{id}")
    FileEventDomain getFilesNameFromDataID(int id);

    @Delete("Delete From FILEEVENT WHERE Data_ID = #{id}")
    void deleteFileData(String id);
}