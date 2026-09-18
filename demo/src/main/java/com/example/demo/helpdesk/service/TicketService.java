package com.example.demo.helpdesk.service;

import com.example.demo.helpdesk.dto.CreateTicketRequest;
import com.example.demo.helpdesk.dto.TicketResponse;
import com.example.demo.helpdesk.entity.Ticket;
import com.example.demo.helpdesk.entity.TicketStatus;
import com.example.demo.helpdesk.entity.User;
import com.example.demo.helpdesk.exception.TicketNotFoundException;
import com.example.demo.helpdesk.repository.TicketRepository;
import com.example.demo.helpdesk.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public List<TicketResponse> getOpenTickets() {

        return ticketRepository.findByStatus(TicketStatus.OPEN)
                .stream()
                .map(TicketResponse::new)
                .toList();
    }

    public TicketResponse assignTicket(Long ticketId, String email) {

        User agent = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Agent not found"));

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found"));

        if (ticket.getStatus() != TicketStatus.OPEN) {
            throw new RuntimeException("Ticket is already assigned");
        }

        ticket.setAgent(agent);
        ticket.setStatus(TicketStatus.ASSIGNED);
        ticket.setUpdatedAt(LocalDateTime.now());

        Ticket savedTicket = ticketRepository.save(ticket);

        return new TicketResponse(savedTicket);
    }

    public List<TicketResponse> getAssignedTickets(String email) {

        User agent = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Agent not found"));

        return ticketRepository.findByAgent(agent)
                .stream()
                .map(TicketResponse::new)
                .toList();
    }

    public TicketResponse updateTicketStatus(
            Long ticketId,
            String email,
            TicketStatus newStatus) {

        User agent = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Agent not found"));

        Ticket ticket = ticketRepository.findByIdAndAgent(ticketId, agent)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found"));

        if (newStatus != TicketStatus.IN_PROGRESS &&
                newStatus != TicketStatus.RESOLVED) {
            throw new RuntimeException("Invalid ticket status");
        }

        if (ticket.getStatus() == TicketStatus.RESOLVED ||
                ticket.getStatus() == TicketStatus.CLOSED) {
            throw new RuntimeException("Ticket cannot be updated");
        }

        ticket.setStatus(newStatus);
        ticket.setUpdatedAt(LocalDateTime.now());

        return new TicketResponse(ticketRepository.save(ticket));
    }

    public TicketResponse closeTicket(Long ticketId, String email) {

        User customer = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Ticket ticket = ticketRepository.findByIdAndCustomer(ticketId, customer)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found"));

        if (ticket.getStatus() != TicketStatus.RESOLVED) {
            throw new RuntimeException("Only resolved tickets can be closed");
        }

        ticket.setStatus(TicketStatus.CLOSED);
        ticket.setUpdatedAt(LocalDateTime.now());

        return new TicketResponse(ticketRepository.save(ticket));
    }




    public Page<TicketResponse> getAllTickets(
            int page,
            int size,
            TicketStatus status) {

        PageRequest pageable = PageRequest.of(
                page,
                size,
                Sort.by("createdAt").descending()
        );

        if (status != null) {
            return ticketRepository
                    .findByStatus(status, pageable)
                    .map(TicketResponse::new);
        }

        return ticketRepository
                .findAll(pageable)
                .map(TicketResponse::new);
    }



}