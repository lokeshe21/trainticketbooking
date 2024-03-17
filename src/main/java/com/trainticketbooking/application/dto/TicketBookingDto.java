package com.trainticketbooking.application.dto;

import java.math.BigDecimal;

/**
 * Record representing a ticket booking for input/output operations.
 */
public record TicketBookingDto(Integer ticketId,
                               String from,
                               String to,
                               String userName,
                               String userEmail,
                               BigDecimal pricePaid,
                               String section,
                               Integer seatNumber,
                               String discount) {
}