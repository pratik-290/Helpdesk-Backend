package com.example.demo.helpdesk.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateTicketRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String category;

    public CreateTicketRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}