package com.jeonbuk.mchms.service.fileevent;

import com.jeonbuk.mchms.domain.FileEventDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileEventService {

    @Autowired
    private FileEventMapper fileEventMapper;

    public FileEventDomain getFilesNameFromDataID(int id) {
        return fileEventMapper.getFilesNameFromDataID(id);
    }

    public void deleteFileData(String id){
        fileEventMapper.deleteFileData(id);
    }
}