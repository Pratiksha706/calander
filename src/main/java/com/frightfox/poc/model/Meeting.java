package com.frightfox.poc.model;

import java.time.LocalDateTime;
import java.util.List;

public class Meeting {
    private String id;
    private Employee owner;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<Employee> participants;

    // Getters and setters
    public String getId() {
        return id;
    }

    public List<Employee> getParticipants() {
		return participants;
	}

	public void setParticipants(List<Employee> participants) {
		this.participants = participants;
	}

	public void setId(String id) {
        this.id = id;
    }

    public Employee getOwner() {
        return owner;
    }

    public void setOwner(Employee owner) {
        this.owner = owner;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}