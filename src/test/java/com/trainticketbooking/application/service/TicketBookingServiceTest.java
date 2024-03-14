package com.trainticketbooking.application.service;

import com.trainticketbooking.application.common.ApiResponseMessages;
import com.trainticketbooking.application.common.CustomApiResponse;
import com.trainticketbooking.application.domain.TicketBooking;
import com.trainticketbooking.application.dto.TicketBookingDto;
import com.trainticketbooking.application.util.TicketBookingMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketBookingServiceTest {

    @Mock
    private TicketBookingMapper ticketBookingMapper;

    @Mock
    private TicketBookingService ticketBookingServiceMock;

    @InjectMocks
    private TicketBookingService ticketBookingService;

    private static final int MAX_SEATS_PER_SECTION = 20;

    private Map<Integer, TicketBooking> ticketBookingMap;
    private Set<Integer> sectionASeats;
    private Set<Integer> sectionBSeats;

    @BeforeEach
    public void setUp() {
        ticketBookingMap = new ConcurrentHashMap<>();
        sectionASeats = new HashSet<>();
        sectionBSeats = new HashSet<>();
        ticketBookingService = new TicketBookingService(ticketBookingMapper);
    }

    @Test
    public void testPurchaseTicket() {
        // Arrange
        TicketBookingDto ticketBookingDto = new TicketBookingDto(
                1, "Source", "Destination", "User", "user@example.com",
                BigDecimal.TEN, "A", 1);
        when(ticketBookingMapper.toDto(any(TicketBooking.class))).thenReturn(ticketBookingDto);

        // Act
        CustomApiResponse<TicketBookingDto> response = ticketBookingService.purchaseTicket(ticketBookingDto);

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(ApiResponseMessages.TICKET_BOOKING_SUCCESSFULLY, response.getMessage());
        assertNotNull(response.getData());
        assertEquals(ticketBookingDto, response.getData());
    }

    @Test
    public void testGenerateTicketId() {
        // Arrange
        ticketBookingMap.put(1, new TicketBooking());

        // Act
        Integer ticketId = ticketBookingService.generateTicketId();

        // Assert
        assertEquals(1, ticketId);
    }

    @Test
    public void testGetUserReceiptDetails_TicketFound() {
        // Arrange
        Integer ticketId = 1;
        TicketBooking ticketBooking = new TicketBooking(ticketId,
                "Source", "Destination", "User", "user@example.com",
                BigDecimal.TEN, "A", 1);
        ticketBookingMap.put(ticketId, ticketBooking);
        ReflectionTestUtils.setField(ticketBookingService, "ticketBookingMap", ticketBookingMap);

        when(ticketBookingMapper.toDto(ticketBooking)).thenReturn(new TicketBookingDto(
                ticketId, "Source", "Destination", "User", "user@example.com",
                BigDecimal.TEN, "A", 1));

        // Act
        CustomApiResponse<TicketBookingDto> response = ticketBookingService.getUserReceiptDetails(ticketId);

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(ApiResponseMessages.USER_RECEIPT_FETCHED_SUCCESSFUL, response.getMessage());
        assertEquals(ticketBookingMapper.toDto(ticketBooking), response.getData());
    }

    @Test
    public void testGetUserReceiptDetails_TicketNotFound() {
        // Arrange
        Integer ticketId = 1;

        // Act
        CustomApiResponse<TicketBookingDto> response = ticketBookingService.getUserReceiptDetails(ticketId);

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(ApiResponseMessages.TICKET_NOT_FOUND, response.getMessage());
        assertEquals(null, response.getData());
    }

    @Test
    public void testGetAllTickets_TicketsFound() {
        // Arrange
        String userEmail = "user@example.com";
        TicketBookingDto ticketBookingDto = new TicketBookingDto(1, "Source", "Destination", "User", userEmail,
                BigDecimal.TEN, "A", 1);
        List<TicketBooking> userTickets = new ArrayList<>();
        userTickets.add(new TicketBooking(1, "Source", "Destination", "User", userEmail,
                BigDecimal.TEN, "A", 1));

        ticketBookingMap.put(1, userTickets.get(0));

        when(ticketBookingMapper.toDto(userTickets.get(0))).thenReturn(ticketBookingDto);
        ReflectionTestUtils.setField(ticketBookingService, "ticketBookingMap", ticketBookingMap);

        // Act
        CustomApiResponse<List<TicketBookingDto>> response = ticketBookingService.getAllTickets(userEmail);

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(ApiResponseMessages.TICKET_LIST_FOUND, response.getMessage());
        assertEquals(1, response.getData().size());
        assertEquals(ticketBookingDto, response.getData().get(0));
    }

    @Test
    public void testUpdateUserSeatAllocation_ValidSeatNumber() {
        // Arrange
        int ticketId = 1;
        int seatNumber = 3;
        TicketBookingDto ticketBookingDto = new TicketBookingDto(ticketId, "Source", "Destination", "User", "user@example.com",
                BigDecimal.TEN, "A", 3);

        TicketBooking ticketBooking = new TicketBooking(ticketId, "Source", "Destination", "User", "user@example.com",
                BigDecimal.TEN, "A", 1);

        ticketBookingMap.put(ticketId, ticketBooking);

        when(ticketBookingMapper.toDto(ticketBooking)).thenReturn(ticketBookingDto);
        ReflectionTestUtils.setField(ticketBookingService, "ticketBookingMap", ticketBookingMap);

        // Act
        CustomApiResponse<TicketBookingDto> response = ticketBookingService.updateUserSeatAllocation(ticketId, seatNumber);

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(ApiResponseMessages.USER_DETAIL_UPDATED_SUCCESSFUL, response.getMessage());
        assertEquals(ticketBookingDto, response.getData());
        assertEquals(seatNumber, ticketBooking.getSeatNumber());
    }

    @Test
    public void testUpdateUserSeatAllocation_InvalidSeatNumber() {
        // Arrange
        int ticketId = 1;
        int seatNumber = 41;

        TicketBooking ticketBooking = new TicketBooking(ticketId, "Source", "Destination", "User", "user@example.com",
                BigDecimal.TEN, "A", 1);

        ticketBookingMap.put(ticketId, ticketBooking);
        ReflectionTestUtils.setField(ticketBookingService, "ticketBookingMap", ticketBookingMap);

        // Act
        CustomApiResponse<TicketBookingDto> response = ticketBookingService.updateUserSeatAllocation(ticketId, seatNumber);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("Invalid seat number. Seat number must be between 1 and " + MAX_SEATS_PER_SECTION * 2, response.getMessage());
        assertEquals(null, response.getData());
        assertEquals(1, ticketBooking.getSeatNumber()); // Seat number should not change
    }

    @Test
    public void testDeleteUserDetail_UserFound() {
        // Arrange
        int ticketId = 1;
        String userName = "User";

        TicketBooking ticketBooking = new TicketBooking(ticketId, "Source", "Destination", userName, "user@example.com",
                BigDecimal.TEN, "A", 1);

        ticketBookingMap.put(ticketId, ticketBooking);
        ReflectionTestUtils.setField(ticketBookingService, "ticketBookingMap", ticketBookingMap);
        // Act
        CustomApiResponse<TicketBookingDto> response = ticketBookingService.deleteUserDetail(userName);

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(ApiResponseMessages.USER_DELETED_SUCCESSFULLY, response.getMessage());
        assertEquals(ticketBookingMapper.toDto(ticketBooking), response.getData());
    }

    @Test
    public void testDeleteUserDetail_UserNotFound() {
        // Arrange
        String userName = "NonExistentUser";

        // Act
        CustomApiResponse<TicketBookingDto> response = ticketBookingService.deleteUserDetail(userName);

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(ApiResponseMessages.USER_NOT_FOUND, response.getMessage());
        assertEquals(null, response.getData());
    }

    @Test
    public void testGetUsersBySection_UsersFound() {
        // Arrange
        String section = "A";

        TicketBooking ticket1 = new TicketBooking(1, "Source", "Destination", "User1", "user1@example.com",
                BigDecimal.TEN, "A", 1);
        TicketBooking ticket2 = new TicketBooking(2, "Source", "Destination", "User2", "user2@example.com",
                BigDecimal.TEN, "A", 2);

        Map<Integer, TicketBooking> ticketBookingMap = new HashMap<>();
        ticketBookingMap.put(1, ticket1);
        ticketBookingMap.put(2, ticket2);
        ReflectionTestUtils.setField(ticketBookingService, "ticketBookingMap", ticketBookingMap);
        when(ticketBookingMapper.toDto(ticket1)).thenReturn(new TicketBookingDto(1, "Source", "Destination",
                "User1", "user1@example.com", BigDecimal.TEN, "A", 1));
        when(ticketBookingMapper.toDto(ticket2)).thenReturn(new TicketBookingDto(2, "Source", "Destination",
                "User2", "user2@example.com", BigDecimal.TEN, "A", 2));
        

        // Act
        CustomApiResponse<List<TicketBookingDto>> response = ticketBookingService.getUsersBySection(section);

        // Assert
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(ApiResponseMessages.USERS_IN_SECTION_FOUND, response.getMessage());
        assertEquals(2, response.getData().size());
        assertEquals(new TicketBookingDto(1, "Source", "Destination", "User1", "user1@example.com",
                BigDecimal.TEN, "A", 1), response.getData().get(0));
        assertEquals(new TicketBookingDto(2, "Source", "Destination", "User2", "user2@example.com",
                BigDecimal.TEN, "A", 2), response.getData().get(1));
    }

}