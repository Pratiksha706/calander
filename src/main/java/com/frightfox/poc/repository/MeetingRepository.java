package com.frightfox.poc.repository;


import org.springframework.stereotype.Repository;

import com.frightfox.poc.model.Meeting;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MeetingRepository {
    private final List<Meeting> meetings = new ArrayList<>();

    public void save(Meeting meeting) {
        meetings.add(meeting);
    }

    public List<Meeting> findByEmployeeId(String employeeId) {
    	 List<Meeting> filteredMeetings = new ArrayList<>();
    	    for (Meeting meeting : meetings) {
    	        if (meeting.getOwner() != null && meeting.getOwner().getId().equals(employeeId)) {
    	            filteredMeetings.add(meeting);
    	        }
    	    }
    	    return filteredMeetings;
    }
}