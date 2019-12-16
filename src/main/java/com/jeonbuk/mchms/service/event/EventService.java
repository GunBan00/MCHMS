package com.jeonbuk.mchms.service.event;

import com.jeonbuk.mchms.domain.EventDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventMapper eventMapper;

    public List<EventDomain> getEventInfo(String id) {
        return eventMapper.getEventInfo(id);
    }

}
