package com.example.demo.helpdesk.service;

import com.example.demo.helpdesk.dto.CreateTicketRequest;
import com.example.demo.helpdesk.dto.TicketResponse;
import com.example.demo.helpdesk.entity.Ticket;
import com.example.demo.helpdesk.entity.TicketStatus;
import com.example.demo.helpdesk.entity.User;
import com.example.demo.helpdesk.exception.TicketNotFoundException;
import com.example.demo.helpdesk.repository.TicketRepository;
import com.example.demo.helpdesk.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TicketService(
            TicketRepository ticketRepository,
            UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    public TicketResponse createTicket(CreateTicketRequest request, String email) {

        User customer = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Ticket ticket = new Ticket();

        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setCategory(request.getCategory());
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setUpdatedAt(LocalDateTime.now());
        ticket.setCustomer(customer);

        Ticket savedTicket= ticketRepository.save(ticket);

        return new TicketResponse(savedTicket);
    }
    public List<TicketResponse> getMyTickets(String email) {

        User customer = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ticketRepository.findByCustomer(customer)
                .stream()
                .map(TicketResponse::new)
                .toList();
    }
    public TicketResponse getTicketById(Long id, String email) {

        User customer = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Ticket ticket = ticketRepository.findByIdAndCustomer(id, customer)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found"));
        return new TicketResponse(ticket);
    }
}