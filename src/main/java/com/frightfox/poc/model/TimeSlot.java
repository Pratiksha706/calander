package com.frightfox.poc.model;

import java.time.LocalDateTime;

public class TimeSlot {
    private LocalDateTime start;
    private LocalDateTime end;

    // Getters and setters
    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

	public TimeSlot(LocalDateTime start, LocalDateTime end) {
		super();
		this.start = start;
		this.end = end;
	}
    
    
}