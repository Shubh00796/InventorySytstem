package com.inventory.management.service;

public interface EmailService {
    void sendEmail(String to, String message,String channel, String subject);
}