package com.example.demo.helpdesk.controller;

import com.example.demo.helpdesk.dto.TicketResponse;
import com.example.demo.helpdesk.dto.UpdateUserRoleRequest;
import com.example.demo.helpdesk.dto.UserResponse;
import com.example.demo.helpdesk.entity.TicketStatus;
import com.example.demo.helpdesk.service.TicketService;
import com.example.demo.helpdesk.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.example.demo.helpdesk.entity.TicketStatus;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;
    private final TicketService ticketService;

    public AdminController(
            UserService userService,
            TicketService ticketService) {
        this.userService = userService;
        this.ticketService = ticketService;
    }

    @GetMapping("/users")
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @PatchMapping("/users/{id}/role")
    public UserResponse updateUserRole(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRoleRequest request) {

        return userService.updateUserRole(
                id,
                request.getRole()
        );
    }



    @GetMapping("/tickets")
    public Page<TicketResponse> getAllTickets(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) TicketStatus status) {

        return ticketService.getAllTickets(
                page,
                size,
                status
        );
    }
}