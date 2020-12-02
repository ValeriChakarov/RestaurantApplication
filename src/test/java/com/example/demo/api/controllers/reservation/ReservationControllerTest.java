package com.example.demo.api.controllers.reservation;

import com.example.demo.api.entities.reservation.ReservationRequest;
import com.example.demo.domain.Reservation;
import com.example.demo.services.reservation.ReservationService;
import com.example.demo.utilities.DateTimeFormatManager;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;


@RunWith(SpringRunner.class)
@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    void fetchAllReservations() throws Exception {
        String startDate = "2020-11-13 19:00:00";
        String endDate = "2020-11-13 21:00:00";
        LocalDateTime startDateTime = DateTimeFormatManager.getLocalDateTimeFormat(startDate);
        LocalDateTime endDateTime = DateTimeFormatManager.getLocalDateTimeFormat(endDate);
        Reservation reservation = new Reservation(UUID.randomUUID(), UUID.randomUUID(), startDateTime, endDateTime, 5, UUID.randomUUID(), UUID.randomUUID());
        List<Reservation> reservations = new ArrayList<>();
        reservations = List.of(reservation);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Mockito.doReturn(reservations).when(reservationService).getAllReservations();
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/reservation/allReservations")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(reservation.getId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].guestId", Matchers.is(reservation.getGuestId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].capacity", Matchers.is(reservation.getCapacity())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].restaurantId", Matchers.is(reservation.getRestaurantId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].tableId", Matchers.is(reservation.getTableId().toString())));
    }


    @Test
    @WithMockUser(username = "user1", password = "user1Pass", roles = "USER")
    void makeReservation() throws Exception {
        String startDate = "2020-11-13 19:00:00";
        String endDate = "2020-11-13 21:00:00";
        UUID id = UUID.randomUUID();
        UUID guestId = UUID.fromString("0853d107-41cd-49f9-a7e0-9ac00fd5840");
        UUID restaurantId = UUID.fromString("0f3d0f54-69cd-42d4-9267-49380a54ba78");
        UUID tableId = UUID.fromString("3b34c232-1f22-4b6d-b346-0ed77a2b5d38");

        LocalDateTime startDateTime = DateTimeFormatManager.getLocalDateTimeFormat(startDate);
        LocalDateTime endDateTime = DateTimeFormatManager.getLocalDateTimeFormat(endDate);
        Reservation reservation = new Reservation(id, guestId,
                startDateTime,
                endDateTime, 7, restaurantId, tableId);
        ReservationRequest reservationRequest = new ReservationRequest(guestId.toString(), startDate,
                endDate, 7, restaurantId.toString(), tableId.toString());
        Mockito.doReturn(reservation).when(reservationService).createReservation(reservationRequest);
        String reservationRequestJsonString = "{\"guestId\":\"0853d107-41cd-49f9-a7e0-09ac00fd5840\",\"startTime\":\"2020-11-13 19:00:00\",\"endTime\":\"2020-11-13 21:00:00\",\"capacity\":7,\"restaurantId\":\"0f3d0f54-69cd-42d4-9267-49380a54ba78\",\"tableId\":\"3b34c232-1f22-4b6d-b346-0ed77a2b5d38\"}";
        mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/reservation/create")
                .contentType(MediaType.APPLICATION_JSON).content(reservationRequestJsonString))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(reservation.getId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.guestId", Matchers.is(reservation.getGuestId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.capacity", Matchers.is(reservation.getCapacity())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.restaurantId", Matchers.is(reservation.getRestaurantId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tableId", Matchers.is(reservation.getTableId().toString())));
    }

    @Test
    @WithMockUser(username = "user1", password = "user1Pass", roles = "USER")
    void cancelReservation() throws Exception {
        UUID id = UUID.fromString("73bf8c32-baa4-489f-ab13-d1b9899bf65d");
        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/reservation/cancel/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}