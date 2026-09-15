package com.example.demo.helpdesk.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.helpdesk.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
