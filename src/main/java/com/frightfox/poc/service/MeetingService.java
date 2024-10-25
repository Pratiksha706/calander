package com.frightfox.poc.service;


import com.frightfox.poc.model.Employee;
import com.frightfox.poc.model.Meeting;
import com.frightfox.poc.model.TimeSlot;
import com.frightfox.poc.repository.MeetingRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MeetingService {
    private final MeetingRepository meetingRepository;

    public MeetingService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public void bookMeeting(Meeting meeting) {
        meetingRepository.save(meeting);
    }

    public List<TimeSlot> findFreeSlots(String employeeId, Duration duration) {
    	List<Meeting> meetings = meetingRepository.findByEmployeeId(employeeId);
        List<TimeSlot> freeSlots = new ArrayList<>();

        meetings.sort((m1, m2) -> m1.getStartTime().compareTo(m2.getStartTime()));

        LocalDateTime currentTime = LocalDateTime.now(); // Assuming free slots start from now
        LocalDateTime endOfDay = currentTime.plusDays(1).withHour(23).withMinute(59); // Assuming end of the day is the cutoff

       
        if (!meetings.isEmpty() && meetings.get(0).getStartTime().isAfter(currentTime.plus(duration))) {
            freeSlots.add(new TimeSlot(currentTime, meetings.get(0).getStartTime()));
        }

      
        for (int i = 0; i < meetings.size() - 1; i++) {
            Meeting currentMeeting = meetings.get(i);
            Meeting nextMeeting = meetings.get(i + 1);

      
            if (nextMeeting.getStartTime().isAfter(currentMeeting.getEndTime().plus(duration))) {
                freeSlots.add(new TimeSlot(currentMeeting.getEndTime(), nextMeeting.getStartTime()));
            }
        }

       
        if (!meetings.isEmpty() && meetings.get(meetings.size() - 1).getEndTime().isBefore(endOfDay.minus(duration))) {
            freeSlots.add(new TimeSlot(meetings.get(meetings.size() - 1).getEndTime(), endOfDay));
        }

        return freeSlots;
    }

    public List<Employee> findConflictingParticipants(Meeting newMeeting) {
        List<Employee> conflictingParticipants = new ArrayList<>();
        
    
        List<Employee> participants = newMeeting.getParticipants();

        for (Employee participant : participants) {
            // Fetch existing meetings for the participant
            List<Meeting> existingMeetings = meetingRepository.findByEmployeeId(participant.getId());

            for (Meeting existingMeeting : existingMeetings) {
                // Check if the new meeting overlaps with the existing meeting
                if (isOverlapping(newMeeting, existingMeeting)) {
                    conflictingParticipants.add(participant);
                    break; // No need to check other meetings for this participant
                }
            }
        }

        return conflictingParticipants;
    }

    private boolean isOverlapping(Meeting newMeeting, Meeting existingMeeting) {
        return newMeeting.getStartTime().isBefore(existingMeeting.getEndTime()) && 
               newMeeting.getEndTime().isAfter(existingMeeting.getStartTime());
    }
}