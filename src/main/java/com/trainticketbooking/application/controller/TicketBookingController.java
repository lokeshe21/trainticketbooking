package com.trainticketbooking.application.controller;


import com.trainticketbooking.application.common.ApiDocumentationTags;
import com.trainticketbooking.application.common.ApiResponseMessages;
import com.trainticketbooking.application.common.CustomApiResponse;
import com.trainticketbooking.application.dto.TicketBookingDto;
import com.trainticketbooking.application.service.TicketBookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class handling various endpoints related to ticket booking operations.
 * Provides RESTful APIs for creating, retrieving, updating, and deleting ticket and user-related information.
 */
@RestController
@RequestMapping("/ticket-booking")
public class TicketBookingController {

    private final TicketBookingService ticketBookingService;

    public TicketBookingController(TicketBookingService ticketBookingService) {
        this.ticketBookingService = ticketBookingService;
    }


    /**
     * Endpoint for purchasing a ticket.
     *
     * @param ticketBookingDto The DTO containing ticket booking information.
     * @return ResponseEntity containing the API response for the ticket booking operation.
     */
    @PostMapping("/")
    @Operation(summary = ApiDocumentationTags.CREATE_TICKET_BOOKING_REQUEST,
            description = ApiDocumentationTags.TICKET_BOOKING_DESCRIPTION,
            tags = ApiDocumentationTags.TICKET_BOOKING)
    @ApiResponse(responseCode = "200", description = ApiResponseMessages.TICKET_BOOKING_SUCCESSFULLY)
    @ApiResponse(responseCode = "500", description = ApiResponseMessages.TICKET_BOOKING_FAILED)
    public ResponseEntity<CustomApiResponse<TicketBookingDto>> purchaseTicket(
            @RequestBody TicketBookingDto ticketBookingDto) {
        try {
            CustomApiResponse<TicketBookingDto> response = ticketBookingService.purchaseTicket(ticketBookingDto);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            e.getMessage(), null));
        }
    }


    /**
     * Endpoint for retrieving user receipt details.
     *
     * @param ticketId The ID of the ticket for which the receipt details are requested.
     * @return ResponseEntity containing the API response for fetching user receipt details.
     */
    @GetMapping("/user-receipt-detail")
    @Operation(summary = ApiDocumentationTags.FETCH_USER_RECEIPT_DETAIL,
            description = ApiDocumentationTags.FETCH_USER_RECEIPT_DESCRIPTION,
            tags = ApiDocumentationTags.TICKET_BOOKING)
    @ApiResponse(responseCode = "200", description = ApiResponseMessages.USER_RECEIPT_FETCHED_SUCCESSFUL)
    @ApiResponse(responseCode = "500", description = ApiResponseMessages.TICKET_NOT_FOUND)
    public ResponseEntity<CustomApiResponse<TicketBookingDto>> getUserReceiptDetails(
            @RequestParam Integer ticketId) {
        CustomApiResponse<TicketBookingDto> response = ticketBookingService.getUserReceiptDetails(ticketId);
        return ResponseEntity.ok(response);
    }


    /**
     * Endpoint for fetching all tickets associated with a user's email.
     *
     * @param email The email of the user for whom the ticket list is requested.
     * @return ResponseEntity containing the API response for fetching all tickets.
     */
    @GetMapping("/ticket-list")
    @Operation(summary = ApiDocumentationTags.FETCH_TICKET_LIST_BY_USER_EMAIL,
            description = ApiDocumentationTags.FETCH_TICKET_LIST_BY_USER_EMAIL_DESCRIPTION,
            tags = ApiDocumentationTags.TICKET_BOOKING)
    @ApiResponse(responseCode = "200", description = ApiResponseMessages.TICKET_LIST_FOUND)
    @ApiResponse(responseCode = "500", description = ApiResponseMessages.TICKET_LIST_NOT_FOUND)
    public ResponseEntity<CustomApiResponse<List<TicketBookingDto>>> getAllTicket(@RequestParam String email) {
        CustomApiResponse<List<TicketBookingDto>> response = ticketBookingService.getAllTickets(email);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint for updating the seat number of a user's ticket.
     *
     * @param ticketId   The ID of the ticket to be updated.
     * @param seatNumber The new seat number to be assigned.
     * @return ResponseEntity containing the API response for updating user seat allocation.
     */
    @PutMapping("/{ticketId}")
    @Operation(summary = ApiDocumentationTags.UPDATE_USER_DETAIL,
            description = ApiDocumentationTags.UPDATE_USER_DETAIL_DESCRIPTION,
            tags = ApiDocumentationTags.TICKET_BOOKING)
    @ApiResponse(responseCode = "200", description = ApiResponseMessages.USER_DETAIL_UPDATED_SUCCESSFUL)
    @ApiResponse(responseCode = "500", description = ApiResponseMessages.FAILED_TO_UPDATE_USER_DETAIL)
    public ResponseEntity<CustomApiResponse<TicketBookingDto>> updateUserSeatAllocationDetails(
            @PathVariable Integer ticketId, @RequestParam Integer seatNumber) {
        try {
            CustomApiResponse<TicketBookingDto> response = ticketBookingService.updateUserSeatAllocation(ticketId, seatNumber);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CustomApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            e.getMessage(), null));
        }
    }


    /**
     * Endpoint for deleting a user.
     *
     * @param userName The username of the user to be deleted.
     * @return ResponseEntity containing the API response for the user deletion operation.
     */
    @DeleteMapping("/")
    @Operation(summary = ApiDocumentationTags.USER_DELETE,
            description = ApiDocumentationTags.USER_DELETE_DESCRIPTION,
            tags = ApiDocumentationTags.TICKET_BOOKING)
    @ApiResponse(responseCode = "200", description = ApiResponseMessages.USER_DELETED_SUCCESSFULLY)
    @ApiResponse(responseCode = "500", description = ApiResponseMessages.USER_DELETE_FAILED)
    public ResponseEntity<CustomApiResponse<TicketBookingDto>> deleteUserDetails(
            @RequestParam String userName) {
        CustomApiResponse<TicketBookingDto> response = ticketBookingService.deleteUserDetail(userName);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint for fetching all users and their seat allocations based on the specified section.
     *
     * @param section The section for which users are requested (e.g., "A", "B").
     * @return ResponseEntity containing the API response for fetching users and their seat allocations.
     */
    @GetMapping("/users-by-section")
    @Operation(summary = ApiDocumentationTags.FETCH_USERS_BY_SECTION,
            description = ApiDocumentationTags.FETCH_USERS_BY_SECTION_DESCRIPTION,
            tags = ApiDocumentationTags.TICKET_BOOKING)
    @ApiResponse(responseCode = "200", description = ApiResponseMessages.USERS_IN_SECTION_FOUND)
    @ApiResponse(responseCode = "500", description = ApiResponseMessages.USERS_IN_SECTION_NOT_FOUND)
    public ResponseEntity<CustomApiResponse<List<TicketBookingDto>>> getUsersBySection(@RequestParam String section) {
        CustomApiResponse<List<TicketBookingDto>> response = ticketBookingService.getUsersBySection(section);
        return ResponseEntity.ok(response);
    }


}
