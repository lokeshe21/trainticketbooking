package com.trainticketbooking.application.service;


import com.trainticketbooking.application.common.ApiResponseMessages;
import com.trainticketbooking.application.common.CustomApiResponse;
import com.trainticketbooking.application.domain.TicketBooking;
import com.trainticketbooking.application.dto.TicketBookingDto;
import com.trainticketbooking.application.util.TicketBookingMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Service class handling various ticket booking operations.
 */
@Service
public class TicketBookingService {


    private final TicketBookingMapper ticketBookingMapper;
    private final Map<Integer, TicketBooking> ticketBookingMap = new ConcurrentHashMap<>();
    private final Set<Integer> sectionASeats = new HashSet<>();
    private final Set<Integer> sectionBSeats = new HashSet<>();
    private static final int MAX_SEATS_PER_SECTION = 20;

    public TicketBookingService( TicketBookingMapper ticketBookingMapper) {
        this.ticketBookingMapper = ticketBookingMapper;
    }

    /**
     * Purchase a ticket and allocate a seat based on the provided TicketBookingDto.
     *
     * @param ticketBooking The Record containing ticket booking information.
     * @return CustomApiResponse with information about the ticket booking operation.
     */
    public CustomApiResponse<TicketBookingDto> purchaseTicket(TicketBookingDto ticketBooking) {
        Integer ticketId = generateTicketId();
        String section = allocateSection();
        Integer seatNumber = allocateSeat(section);
        TicketBooking newTicket = new TicketBooking(ticketId,
                ticketBooking.from(),
                ticketBooking.to(),
                ticketBooking.userName(),
                ticketBooking.userEmail(),
                ticketBooking.pricePaid(),
                section,
                seatNumber);
        ticketBookingMap.put(ticketId, newTicket);
        return new CustomApiResponse<>(HttpStatus.OK.value(),
                ApiResponseMessages.TICKET_BOOKING_SUCCESSFULLY,
                convertToDto(newTicket));
    }

    /**
     * Generate a new ticket ID.
     *
     * @return The generated ticket ID.
     */
    public Integer generateTicketId() {
        return ticketBookingMap.size() + 1;
    }

    /**
     * Allocate a seat based on the provided section.
     *
     * @param section The section for which a seat needs to be allocated.
     * @return The allocated seat number.
     * @throws IllegalArgumentException if all seats in the section are occupied.
     */
    private int allocateSeat(String section) {
        Set<Integer> sectionSeats = section.equalsIgnoreCase("A") ? sectionASeats : sectionBSeats;
        int startSeatNumber = section.equalsIgnoreCase("A") ? 1 : 21;
        int endSeatNumber = section.equalsIgnoreCase("A") ? MAX_SEATS_PER_SECTION : 40;
        for (int i = startSeatNumber; i <= endSeatNumber; i++) {
            if (!sectionSeats.contains(i)) {
                sectionSeats.add(i);
                return i;
            }
        }
        throw new IllegalArgumentException("No available seats in section " + section);
    }
    /**
     * Allocate a section based on availability.
     *
     * @return The allocated section.
     * @throws IllegalArgumentException if all seats in both sections are occupied.
     */
    private String allocateSection() {
        if (sectionASeats.size() < MAX_SEATS_PER_SECTION) {
            return "A";
        } else if (sectionBSeats.size() < MAX_SEATS_PER_SECTION) {
            return "B";
        } else {
            throw new IllegalArgumentException("No available seats in either section");
        }
    }

    /**
     * Get user receipt details based on the provided ticket ID.
     *
     * @param ticketId The ID of the ticket for which receipt details are requested.
     * @return CustomApiResponse with information about the user receipt details.
     */
    public CustomApiResponse<TicketBookingDto> getUserReceiptDetails(Integer ticketId) {
        TicketBooking ticketBooking = ticketBookingMap.get(ticketId);
        if (ticketBooking != null) {
            return new CustomApiResponse<>(HttpStatus.OK.value(),
                    ApiResponseMessages.USER_RECEIPT_FETCHED_SUCCESSFUL, convertToDto(ticketBooking));
        } else {
            return new CustomApiResponse<>(HttpStatus.OK.value(),
                    ApiResponseMessages.TICKET_NOT_FOUND, null);
        }
    }

    /**
     * Get all tickets associated with a user's email.
     *
     * @param email The email of the user for whom tickets are requested.
     * @return CustomApiResponse with information about the ticket list.
     */
    public CustomApiResponse<List<TicketBookingDto>> getAllTickets(String email) {
        List<TicketBookingDto> userTickets = ticketBookingMap.values().stream()
                .filter(ticket -> ticket.getUserEmail().equalsIgnoreCase(email))
                .map(this::convertToDto)
                .toList();

        if (!userTickets.isEmpty()) {
            return new CustomApiResponse<>(HttpStatus.OK.value(),
                    ApiResponseMessages.TICKET_LIST_FOUND, userTickets);
        } else {
            return new CustomApiResponse<>(HttpStatus.OK.value(),
                    ApiResponseMessages.TICKET_LIST_NOT_FOUND, null);
        }
    }


    /**
     * Update the seat allocation for a user's ticket based on the provided ticket ID and seat number.
     *
     * @param ticketId   The ID of the ticket to be updated.
     * @param seatNumber The new seat number to be assigned.
     * @return CustomApiResponse with information about the seat update operation.
     */
    public CustomApiResponse<TicketBookingDto> updateUserSeatAllocation(Integer ticketId, Integer seatNumber) {
        TicketBooking ticketBooking = ticketBookingMap.get(ticketId);
        if (ticketBooking != null) {
            // Check if the seat number is within the valid range
            if (seatNumber < 1 || seatNumber > (MAX_SEATS_PER_SECTION*2)) {
                return new CustomApiResponse<>(HttpStatus.BAD_REQUEST.value(),
                        "Invalid seat number. Seat number must be between 1 and " + MAX_SEATS_PER_SECTION*2, null);
            }

            // Check if the seat is already occupied
            if (!isSeatOccupied(seatNumber)) {
                ticketBooking.setSeatNumber(seatNumber);
                return new CustomApiResponse<>(HttpStatus.OK.value(),
                        ApiResponseMessages.USER_DETAIL_UPDATED_SUCCESSFUL, convertToDto(ticketBooking));
            } else {
                return new CustomApiResponse<>(HttpStatus.BAD_REQUEST.value(),
                        ApiResponseMessages.SEAT_ALREADY_OCCUPIED, null);
            }
        } else {
            return new CustomApiResponse<>(HttpStatus.OK.value(),
                    ApiResponseMessages.FAILED_TO_UPDATE_USER_DETAIL, null);
        }
    }

    /**
     * Check if the seat is already occupied.
     *
     * @param seatNumber The seat number to check.
     * @return True if the seat is already occupied, false otherwise.
     */
    public boolean isSeatOccupied(int seatNumber) {
        return sectionASeats.contains(seatNumber) || sectionBSeats.contains(seatNumber);
    }

    /**
     * Delete user details based on the provided username.
     *
     * @param userName The username of the user to be deleted.
     * @return CustomApiResponse with information about the user deletion operation.
     */
    public CustomApiResponse<TicketBookingDto> deleteUserDetail(String userName) {
        Iterator<Map.Entry<Integer, TicketBooking>> iterator = ticketBookingMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, TicketBooking> entry = iterator.next();
            if (entry.getValue().getUserName().equalsIgnoreCase(userName)) {
                Boolean seatUnAllocated = this.unAllocateSeat(entry);
                if(seatUnAllocated.equals(Boolean.TRUE)) {
                    iterator.remove();
                }

                return new CustomApiResponse<>(HttpStatus.OK.value(),
                        ApiResponseMessages.USER_DELETED_SUCCESSFULLY, convertToDto(entry.getValue()));
            }
        }
        return new CustomApiResponse<>(HttpStatus.OK.value(),
                ApiResponseMessages.USER_NOT_FOUND, null);
    }

    public Boolean unAllocateSeat(Map.Entry<Integer, TicketBooking> entry) {

        return entry.getValue().getSection().equalsIgnoreCase("A") ?
                sectionASeats.remove(entry.getValue().getSeatNumber()) :
                sectionBSeats.remove(entry.getValue().getSeatNumber());
    }

    /**
     * Get all users and their seat allocations based on the specified section.
     *
     * @param section The section for which users are requested (e.g., "A", "B").
     * @return CustomApiResponse with information about the users and their seat allocations.
     */
    public CustomApiResponse<List<TicketBookingDto>> getUsersBySection(String section) {
        List<TicketBookingDto> usersInSection = ticketBookingMap.values().stream()
                .filter(ticket -> ticket.getSection().equalsIgnoreCase(section))
                .map(this::convertToDto)
                .toList();

        if (!usersInSection.isEmpty()) {
            return new CustomApiResponse<>(HttpStatus.OK.value(),
                    ApiResponseMessages.USERS_IN_SECTION_FOUND, usersInSection);
        } else {
            return new CustomApiResponse<>(HttpStatus.OK.value(),
                    ApiResponseMessages.USERS_IN_SECTION_NOT_FOUND, null);
        }
    }

    private TicketBookingDto convertToDto(TicketBooking ticketBooking) {
        return ticketBookingMapper.toDto(ticketBooking);
    }
}