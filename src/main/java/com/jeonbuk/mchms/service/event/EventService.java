package com.jeonbuk.mchms.service.event;

import com.jeonbuk.mchms.domain.EventDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EventService {

    @Autowired
    private EventMapper eventMapper;

    public Map<String, String> getEventInfo(String id) {
        return eventMapper.getEventInfo(id);
    }

}
