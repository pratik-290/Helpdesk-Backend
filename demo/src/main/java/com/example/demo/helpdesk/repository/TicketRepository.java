package com.example.demo.helpdesk.repository;

import com.example.demo.helpdesk.entity.Ticket;
import com.example.demo.helpdesk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByCustomer(User customer);
    Optional<Ticket> findByIdAndCustomer(Long id, User customer);
}