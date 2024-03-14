package com.trainticketbooking.application.util;


import com.trainticketbooking.application.domain.TicketBooking;
import com.trainticketbooking.application.dto.TicketBookingDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface TicketBookingMapper {

    @Mapping(target = "ticketId", source = "ticketId")
    @Mapping(target = "from", source = "from")
    @Mapping(target = "to", source = "to")
    @Mapping(target = "userName", source = "userName")
    @Mapping(target = "userEmail", source = "userEmail")
    @Mapping(target = "pricePaid", source = "pricePaid")
    @Mapping(target = "section", source = "section")
    @Mapping(target = "seatNumber", source = "seatNumber")
    TicketBookingDto toDto(TicketBooking ticketBooking);
}