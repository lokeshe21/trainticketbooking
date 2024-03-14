# Train Ticket Booking Application

## Overview

The Train Ticket Booking Application provides a platform for users to book train tickets, manage their bookings, and retrieve ticket details. It offers RESTful APIs for various ticket booking operations such as creating, retrieving, updating, and deleting tickets.

## Features

- **Ticket Booking**: Users can book train tickets by providing necessary details such as source, destination, username, email, and price.
- **Seat Allocation**: Seats are automatically allocated based on availability in different sections (A and B).
- **Receipt Details**: Users can retrieve receipt details for their booked tickets using the ticket ID.
- **Ticket List**: Users can fetch a list of all tickets associated with their email address.
- **Seat Update**: Users can update the seat number for their booked tickets.
- **User Deletion**: Administrators can delete user details based on the username.
- **User Search**: Users can search for all users and their seat allocations based on the specified section.

## Technologies Used

- **Java**: The application is built using Java programming language.
- **Spring Boot**: Spring Boot framework is used to develop RESTful APIs and manage dependencies.
- **Swagger**: Swagger is integrated for API documentation and testing.
- **Lombok**: Lombok library is used to reduce boilerplate code with annotations such as @Data, @AllArgsConstructor, and @NoArgsConstructor.

## API Endpoints

### Ticket Booking

- **POST** `/ticket-booking/`: Endpoint for purchasing a ticket.
  - Request Body: TicketBookingDto
  - Response: CustomApiResponse<TicketBookingDto>
  #### Request

    ```json
    {
      "from": "Chennai",
      "to": "Trichy",
      "userName": "Lokesh",
      "userEmail": "lokeshe@yahoo.com",
      "pricePaid": 200
    }
    ``` 
  #### Response

    ```json
    {
      "status": 200,
      "message": "Ticket Booking Successful",
      "data": {
        "ticketId": 1,
        "from": "Chennai",
        "to": "Trichy",
        "userName": "Lokesh",
        "userEmail": "lokeshe@yahoo.com",
        "pricePaid": 200,
        "section": "A",
        "seatNumber": 1
      }
    }
    ```

### Receipt Details

- **GET** `/ticket-booking/user-receipt-detail`: Endpoint for retrieving user receipt details.
  - Request Param: ticketId
  - Response: CustomApiResponse<TicketBookingDto>
  #### Request
       ticketId:1

  #### Response

    ```json
    {
      "status": 200,
      "message": "User Receipt Fetched Successfully",
      "data": {
        "ticketId": 1,
        "from": "Chennai",
        "to": "Trichy",
        "userName": "Lokesh",
        "userEmail": "lokeshe@yahoo.com",
        "pricePaid": 200,
        "section": "A",
        "seatNumber": 2
      }
    }
    ```


### Ticket List

- **GET** `/ticket-booking/ticket-list`: Endpoint for fetching all tickets associated with a user's email.
  - Request Param: email
  - Response: CustomApiResponse<List<TicketBookingDto>>
  #### Request
      email:lokeshe@yahoo.com
  #### Response

    ```json
    {
      "status": 200,
      "message": "Ticket List Found",
      "data": [
        {
          "ticketId": 1,
          "from": "Chennai",
          "to": "Trichy",
          "userName": "Lokesh",
          "userEmail": "lokeshe@yahoo.com",
          "pricePaid": 200,
          "section": "A",
          "seatNumber": 2
        }
      ]
    }
    ```

### Seat Update

- **PUT** `/ticket-booking/{ticketId}`: Endpoint for updating the seat number of a user's ticket.
  - Path Variable: ticketId
  - Request Param: seatNumber
  - Response: CustomApiResponse<TicketBookingDto>
  #### Request
      ticketId : 1 seatNumber : 2
  #### Response

    ```json
    {
      "status": 200,
      "message": "User Detail Updated successfully",
      "data": {
        "ticketId": 1,
        "from": "Chennai",
        "to": "Trichy",
        "userName": "Lokesh",
        "userEmail": "lokeshe@yahoo.com",
        "pricePaid": 200,
        "section": "A",
        "seatNumber": 2
      }
    }
    ```
### User Deletion

- **DELETE** `/ticket-booking/`: Endpoint for deleting a user.
  - Request Param: userName
  - Response: CustomApiResponse<TicketBookingDto>
  #### Request
      userName : Lokesh
  #### Response

    ```json
    {
      "status": 200,
      "message": "User Deleted Successfully",
      "data": {
        "ticketId": 1,
        "from": "Chennai",
        "to": "Trichy",
        "userName": "Lokesh",
        "userEmail": "lokeshe@yahoo.com",
        "pricePaid": 200,
        "section": "A",
        "seatNumber": 2
      }
    }
    ```

### User Search

- **GET** `/ticket-booking/users-by-section`: Endpoint for fetching all users and their seat allocations based on the specified section.
  - Request Param: section
  - Response: CustomApiResponse<List<TicketBookingDto>>
  #### Request
      section : a
  #### Response

    ```json
    {
      "status": 200,
      "message": "Users in the specified section found.",
      "data": [
        {
          "ticketId": 1,
          "from": "Chennai",
          "to": "Trichy",
          "userName": "Lokesh",
          "userEmail": "lokeshe@yahoo.com",
          "pricePaid": 200,
          "section": "A",
          "seatNumber": 2
        }
      ]
    }
    ```


## Setup and Usage

1. Clone the repository to your local machine.
2. Open the project in your preferred IDE.
3. Build and run the project.
4. Access the API endpoints using tools like Postman or Swagger UI. 
   Swagger UI can be accessed [here](http://localhost:8080/ticket-booking/swagger-ui/index.html#/).

## Contributors

- Lokesh

