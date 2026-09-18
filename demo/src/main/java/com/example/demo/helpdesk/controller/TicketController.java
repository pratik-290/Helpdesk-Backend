package com.example.demo.helpdesk.controller;

import com.example.demo.helpdesk.dto.CreateTicketRequest;
import com.example.demo.helpdesk.entity.Ticket;
import com.example.demo.helpdesk.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.example.demo.helpdesk.dto.TicketResponse;

import java.util.List;

@RestController
@RequestMapping("/api/customer/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public TicketResponse createTicket(
            @Valid @RequestBody CreateTicketRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        return ticketService.createTicket(request, email);
    }
    @GetMapping
    public List<TicketResponse> getMyTickets(Authentication authentication) {

        String email = authentication.getName();

        return ticketService.getMyTickets(email);
    }

    @GetMapping("/{id}")
    public TicketResponse getTicketById(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        return ticketService.getTicketById(id, email);
    }

    @PatchMapping("/{id}/close")
    public TicketResponse closeTicket(
            @PathVariable Long id,
            Authentication authentication) {

        return ticketService.closeTicket(
                id,
                authentication.getName()
        );
    }

}