package com.frightfox.poc.controller;


import com.frightfox.poc.model.Employee;
import com.frightfox.poc.model.Meeting;
import com.frightfox.poc.model.TimeSlot;
import com.frightfox.poc.service.MeetingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController {
    private final MeetingService meetingService;

    @Autowired
    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @PostMapping
    public ResponseEntity<Void> bookMeeting(@RequestBody Meeting meeting) {
        meetingService.bookMeeting(meeting);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/free-slots")
    public ResponseEntity<List<TimeSlot>> getFreeSlots(@RequestParam String employeeId, @RequestParam int durationMinutes) {
        Duration duration = Duration.ofMinutes(durationMinutes);
        List<TimeSlot> freeSlots = meetingService.findFreeSlots(employeeId, duration);
        return ResponseEntity.ok(freeSlots);
    }

    @PostMapping("/conflicts")
    public ResponseEntity<List<Employee>> getConflictingParticipants(@RequestBody Meeting meeting) {
        List<Employee> conflicts = meetingService.findConflictingParticipants(meeting);
        return ResponseEntity.ok(conflicts);
    }
}