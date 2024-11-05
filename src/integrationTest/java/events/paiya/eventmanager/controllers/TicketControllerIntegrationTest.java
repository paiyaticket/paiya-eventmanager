package events.paiya.eventmanager.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import events.paiya.eventmanager.domains.Ticket;
import events.paiya.eventmanager.mappers.TicketMapper;
import events.paiya.eventmanager.resources.TicketResource;
import events.paiya.eventmanager.services.TicketService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("integration-test")
public class TicketControllerIntegrationTest {

    MockMvc mockMvc;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketMapper ticketMapper;

    private static ObjectMapper objectMapper;

    @BeforeAll
    public static void init(){
        objectMapper = new ObjectMapper();
    }

    @BeforeEach
    void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(new TicketController(ticketService, ticketMapper))
                .build();

    }

    @Test
    @Order(1)
    void create() throws Exception {
        TicketResource ticketResource = this.buildTicketREsource();
        mockMvc.perform(post("/v1/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(ticketResource)))
                .andExpect(status().isCreated());
    }


    Ticket buildTicket(){
        return Ticket.builder()
                    .label("VIP")
                    .code("VIP")
                    .price(10000d)
                    .maximumTicketQuantityPerOrder(3)
                    .transactionFeesSupported(false)
                    .quantity(100)
                    .eventId("65ed4726c36bf3454a04cf92")
                    .build();
    }

    TicketResource buildTicketREsource(){
        return TicketResource.builder()
                    .label("VIP")
                    .code("VIP")
                    .price(10000d)
                    .maximumTicketQuantityPerOrder(3)
                    .transactionFeesSupported(false)
                    .quantity(100)
                    .eventId("65ed4726c36bf3454a04cf92")
                    .build();
    }
    
}