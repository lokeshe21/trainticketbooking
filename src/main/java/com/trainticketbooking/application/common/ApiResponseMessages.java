package com.trainticketbooking.application.common;


/**
 * This class contains constant messages for API responses in the Ticket Booking application.
 */
public class ApiResponseMessages {

    /**
     * Message for a successful ticket booking.
     */
    public static final String TICKET_BOOKING_SUCCESSFULLY = "Ticket Booking Successful";

    /**
     * Message for a failed ticket booking.
     */
    public static final String TICKET_BOOKING_FAILED = "Ticket Booking Fail";

    /**
     * Message for successfully fetching user receipt.
     */
    public static final String USER_RECEIPT_FETCHED_SUCCESSFUL = "User Receipt Fetched Successfully";

    /**
     * Message for when a ticket is not found.
     */
    public static final String TICKET_NOT_FOUND = "Ticket Not Found";

    /**
     * Message for when the ticket list is not found.
     */
    public static final String TICKET_LIST_NOT_FOUND = "Ticket List Not Found";

    /**
     * Message for when the ticket list is found.
     */
    public static final String TICKET_LIST_FOUND = "Ticket List Found";

    /**
     * Message for successful user detail update.
     */
    public static final String USER_DETAIL_UPDATED_SUCCESSFUL = "User Detail Updated successfully";

    /**
     * Message for failed user detail update.
     */
    public static final String FAILED_TO_UPDATE_USER_DETAIL = "Failed To Update User";

    /**
     * Message for successful user deletion.
     */
    public static final String USER_DELETED_SUCCESSFULLY = "User Deleted Successfully";

    /**
     * Message for failed user deletion.
     */
    public static final String USER_DELETE_FAILED = "Failed To Delete User";

    /**
     * Message for user deletion when user not found.
     */
    public static final String USER_NOT_FOUND = "User not found.";

    /**
     * Represents the message indicating that a seat is already occupied.
     */
    public static final String SEAT_ALREADY_OCCUPIED = "Seat is already occupied.";

    /**
     * Message indicating that users in the specified section are found.
     */
    public static final String USERS_IN_SECTION_FOUND = "Users in the specified section found.";

    /**
     * Message indicating that no users are found in the specified section.
     */
    public static final String USERS_IN_SECTION_NOT_FOUND = "No users found in the specified section.";

    private ApiResponseMessages() {
    }
}