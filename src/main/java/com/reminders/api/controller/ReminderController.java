package com.reminders.api.controller;

import com.reminders.api.dto.CreateReminderRequest;
import com.reminders.api.dto.ReminderResponse;
import com.reminders.api.dto.UpdateReminderRequest;
import com.reminders.api.service.ReminderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reminders")
public class ReminderController {

    private final ReminderService reminderService;

    public ReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @PostMapping
    public ResponseEntity<ReminderResponse> createReminder(
            @Valid @RequestBody CreateReminderRequest request) {
        return new ResponseEntity<>(reminderService.createReminder(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReminderResponse> getReminder(@PathVariable Long id) {
        return ResponseEntity.ok(reminderService.getReminderById(id));
    }

    @GetMapping
    public ResponseEntity<List<ReminderResponse>> getAllReminders() {
        return ResponseEntity.ok(reminderService.getAllReminders());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReminderResponse> updateReminder(
            @PathVariable Long id,
            @Valid @RequestBody UpdateReminderRequest request) {
        return ResponseEntity.ok(reminderService.updateReminder(id, request));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Void> completeReminder(@PathVariable Long id) {
        reminderService.completeReminder(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReminder(@PathVariable Long id) {
        reminderService.deleteReminder(id);
        return ResponseEntity.noContent().build();
    }
}
