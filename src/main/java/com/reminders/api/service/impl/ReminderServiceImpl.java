package com.reminders.api.service.impl;

import com.reminders.api.dto.CreateReminderRequest;
import com.reminders.api.dto.ReminderResponse;
import com.reminders.api.dto.UpdateReminderRequest;
import com.reminders.api.exception.ReminderNotFoundException;
import com.reminders.api.model.entity.Reminder;
import com.reminders.api.model.enums.ReminderStatus;
import com.reminders.api.repository.ReminderRepository;
import com.reminders.api.service.ReminderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReminderServiceImpl implements ReminderService {

    private final ReminderRepository reminderRepository;

    public ReminderServiceImpl(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    @Override
    public ReminderResponse createReminder(CreateReminderRequest request) {
        Reminder reminder = new Reminder();
        reminder.setTitle(request.getTitle());
        reminder.setDescription(request.getDescription());
        reminder.setDueAt(request.getDueAt());
        reminder.setPriority(request.getPriority());
        reminder.setStatus(ReminderStatus.PENDING);
        reminder.setCreatedAt(LocalDateTime.now());
        reminder.setDeleted(false);

        return mapToResponse(reminderRepository.save(reminder));
    }

    @Override
    public ReminderResponse getReminderById(Long id) {
        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(() -> new ReminderNotFoundException("Reminder not found with id: " + id));
        return mapToResponse(reminder);
    }

    @Override
    public List<ReminderResponse> getAllReminders() {
        return reminderRepository.findAll()
                .stream()
                .filter(r -> !r.isDeleted())
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ReminderResponse updateReminder(Long id, UpdateReminderRequest request) {
        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(() -> new ReminderNotFoundException("Reminder not found with id: " + id));

        reminder.setTitle(request.getTitle());
        reminder.setDescription(request.getDescription());
        reminder.setDueAt(request.getDueAt());
        reminder.setPriority(request.getPriority());
        reminder.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(reminderRepository.save(reminder));
    }

    @Override
    public void completeReminder(Long id) {
        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(() -> new ReminderNotFoundException("Reminder not found with id: " + id));

        reminder.setStatus(ReminderStatus.COMPLETED);
        reminder.setUpdatedAt(LocalDateTime.now());
        reminderRepository.save(reminder);
    }

    @Override
    public void deleteReminder(Long id) {
        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(() -> new ReminderNotFoundException("Reminder not found with id: " + id));

        reminder.setDeleted(true);
        reminder.setUpdatedAt(LocalDateTime.now());
        reminderRepository.save(reminder);
    }

    private ReminderResponse mapToResponse(Reminder reminder) {
        ReminderResponse response = new ReminderResponse();
        response.setId(reminder.getId());
        response.setTitle(reminder.getTitle());
        response.setDescription(reminder.getDescription());
        response.setDueAt(reminder.getDueAt());
        response.setStatus(reminder.getStatus());
        response.setPriority(reminder.getPriority());
        response.setCreatedAt(reminder.getCreatedAt());
        response.setUpdatedAt(reminder.getUpdatedAt());
        return response;
    }
}
