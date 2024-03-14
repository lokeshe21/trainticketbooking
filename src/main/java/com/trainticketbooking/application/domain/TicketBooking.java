package com.trainticketbooking.application.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Represents a ticket booking in the system.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketBooking {

    /**
     * Unique identifier for the ticket.
     */
    private Integer ticketId;

    /**
     * The source location of the journey.
     */
    private String from;

    /**
     * The destination location of the journey.
     */
    private String to;

    /**
     * The username associated with the ticket booking.
     */
    private String userName;

    /**
     * The email address of the user who booked the ticket.
     */
    private String userEmail;

    /**
     * The amount paid for the ticket.
     */
    private BigDecimal pricePaid;

    /**
     * The section of the seating area (e.g., A, B, etc.).
     */
    private String section;

    /**
     * The seat number allocated for the ticket within the specified section.
     */
    private Integer seatNumber;

}
