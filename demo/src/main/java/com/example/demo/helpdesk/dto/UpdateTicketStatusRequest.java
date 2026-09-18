package com.example.demo.helpdesk.dto;

import com.example.demo.helpdesk.entity.TicketStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateTicketStatusRequest {

    @NotNull
    private TicketStatus status;

    public UpdateTicketStatusRequest() {
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }
}