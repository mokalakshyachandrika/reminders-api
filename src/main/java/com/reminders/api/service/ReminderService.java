package com.reminders.api.service;

import com.reminders.api.dto.CreateReminderRequest;
import com.reminders.api.dto.ReminderResponse;
import com.reminders.api.dto.UpdateReminderRequest;

import java.util.List;

public interface ReminderService {

    ReminderResponse createReminder(CreateReminderRequest request);

    ReminderResponse getReminderById(Long id);

    List<ReminderResponse> getAllReminders();

    ReminderResponse updateReminder(Long id, UpdateReminderRequest request);

    void completeReminder(Long id);

    void deleteReminder(Long id);
}
