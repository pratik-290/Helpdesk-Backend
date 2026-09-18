package com.example.demo.helpdesk.repository;

import com.example.demo.helpdesk.entity.Ticket;
import com.example.demo.helpdesk.entity.TicketStatus;
import com.example.demo.helpdesk.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByCustomer(User customer);

    Optional<Ticket> findByIdAndCustomer(Long id, User customer);

    List<Ticket> findByStatus(TicketStatus status);

    List<Ticket> findByAgent(User agent);

    Optional<Ticket> findByIdAndAgent(Long id, User agent);

    Page<Ticket> findByStatus(TicketStatus status, Pageable pageable);
}