package com.example.demo.helpdesk.controller;

import com.example.demo.helpdesk.dto.TicketResponse;
import com.example.demo.helpdesk.dto.UpdateTicketStatusRequest;
import com.example.demo.helpdesk.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agent/tickets")
public class AgentTicketController {

    private final TicketService ticketService;

    public AgentTicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/open")
    public List<TicketResponse> getOpenTickets() {
        return ticketService.getOpenTickets();
    }

    @PostMapping("/{id}/assign")
    public TicketResponse assignTicket(
            @PathVariable Long id,
            Authentication authentication) {

        return ticketService.assignTicket(
                id,
                authentication.getName()
        );
    }

    @GetMapping("/assigned")
    public List<TicketResponse> getAssignedTickets(
            Authentication authentication) {

        return ticketService.getAssignedTickets(
                authentication.getName()
        );
    }

    @PatchMapping("/{id}/status")
    public TicketResponse updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTicketStatusRequest request,
            Authentication authentication) {

        return ticketService.updateTicketStatus(
                id,
                authentication.getName(),
                request.getStatus()
        );
    }
}