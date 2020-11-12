package com.example.demo.api.reservation;

import com.example.demo.api.user.UserController;
import com.example.demo.domain.Reservation;
import com.example.demo.domain.User;
import com.example.demo.services.reservation.DateTimeFormatManager;
import com.example.demo.services.reservation.ReservationService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


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
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId", Matchers.is(reservation.getUserId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].capacity", Matchers.is(reservation.getCapacity())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].startTime", Matchers.is(startDateTime)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].endTime", Matchers.is(endDateTime)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].restaurantId", Matchers.is(reservation.getRestaurantId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].tableId", Matchers.is(reservation.getTableId().toString())));
    }

    @Test
    void makeReservation() throws Exception {
        String startDate = "2020-11-13 19:00:00";
        String endDate = "2020-11-13 21:00:00";
        UUID id = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        UUID tableId = UUID.randomUUID();

        LocalDateTime startDateTime = DateTimeFormatManager.getLocalDateTimeFormat(startDate);
        LocalDateTime endDateTime = DateTimeFormatManager.getLocalDateTimeFormat(endDate);
        Reservation reservation = new Reservation(id, userId,
                startDateTime,
                endDateTime, 5, restaurantId, tableId);
        Mockito.doReturn(reservation).when(reservationService).createReservation(userId, startDateTime, endDateTime, 5, restaurantId, tableId);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("userID", userId.toString());
        params.add("startTime", startDate);
        params.add("endTime", endDate);
        params.add("capacity", "5");
        params.add("restaurantID", restaurantId.toString());
        params.add("tableID", tableId.toString());
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/reservation/create")
                .queryParams(params)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        resultActions
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(reservation.getId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId", Matchers.is(reservation.getUserId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.capacity", Matchers.is(reservation.getCapacity())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.restaurantId", Matchers.is(reservation.getRestaurantId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tableId", Matchers.is(reservation.getTableId().toString())));
    }

    @Test
    void cancelReservation() throws Exception {
        UUID id = UUID.fromString("73bf8c32-baa4-489f-ab13-d1b9899bf65d");
        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/reservation/cancel/"+id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}