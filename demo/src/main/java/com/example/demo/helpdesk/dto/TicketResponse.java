package com.example.demo.helpdesk.dto;

import com.example.demo.helpdesk.entity.Ticket;
import java.time.LocalDateTime;

public class TicketResponse {

    private Long id;
    private String title;
    private String description;
    private String category;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long customerId;
    private String customerName;
    private Long agentId;
    private String agentName;

    public TicketResponse(Ticket ticket) {
        this.id = ticket.getId();
        this.title = ticket.getTitle();
        this.description = ticket.getDescription();
        this.category = ticket.getCategory();
        this.status = ticket.getStatus().name();
        this.createdAt = ticket.getCreatedAt();
        this.updatedAt = ticket.getUpdatedAt();

        if (ticket.getCustomer() != null) {
            this.customerId = ticket.getCustomer().getId();
            this.customerName = ticket.getCustomer().getName();
        }

        if (ticket.getAgent() != null) {
            this.agentId = ticket.getAgent().getId();
            this.agentName = ticket.getAgent().getName();
        }
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Long getAgentId() {
        return agentId;
    }

    public String getAgentName() {
        return agentName;
    }
}