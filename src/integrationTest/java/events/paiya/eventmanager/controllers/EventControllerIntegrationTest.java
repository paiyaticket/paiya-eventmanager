package events.paiya.eventmanager.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import events.paiya.eventmanager.domains.Event;
import events.paiya.eventmanager.domains.EventOrganizer;
import events.paiya.eventmanager.domains.PhysicalAdresse;
import events.paiya.eventmanager.domains.TicketCategorie;
import events.paiya.eventmanager.mappers.EventMapper;
import events.paiya.eventmanager.services.EventServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("integration-test")
public class EventControllerIntegrationTest {
    private static final String EVENT1_ID = "64ef8526c36bf3454a04cf92";
    private static final String EVENT2_ID = "65ed4726c36bf3454a04cf92";
    private static final String EVENT3_ID = "67ed5426c36bf3454a04cf98";
    private static final String TICKET_CATEGORIE_CODE = EVENT1_ID+".CAT.e8f7c3ef-f416-4a61-ab50-60902e31dab2";

    MockMvc mockMvc;

    @Autowired
    private EventServiceImpl eventService;

    @Autowired
    private EventMapper eventMapper;

    private static ObjectMapper objectMapper;

    @BeforeAll
    public static void init(){
        objectMapper = new ObjectMapper();
    }

    @BeforeEach
    void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(new EventController(eventService, eventMapper))
                .build();

    }

    @Test
    @Order(0)
    void findAllEvents() throws Exception {
        eventService.deleteAll();
        mockMvc.perform(get("/v1/events")).andExpect(status().isOk());
    }

    @Test
    @Order(1)
    void create() throws Exception {
        Event event = this.buildEvent();
        mockMvc.perform(post("/v1/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(eventMapper.toResource(event))))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(2)
    void findEventsByOwner() throws Exception {
        String ownerId = "23ff1ef4-283a-4a6a-9e73-c33c6bb53d73";
        mockMvc.perform(get("/v1/events/owned-by")
                        .param("ownerId", ownerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[0].createdBy").value(ownerId));
    }

    @Test
    @Order(3)
    void findEventsByStartingDateBetweenAndVisibilityIsTrue()  throws Exception {
        String minDate = "2023-06-30";
        String maxDate = "2023-08-30";
        this.buildAndSaveTwoEvents();
        mockMvc.perform(get("/v1/events/date-between")
                        .param("minDate", minDate)
                        .param("maxDate", maxDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @Order(4)
    void findEventsByTitleLikeIgnoreCaseAndVisibilityIsTrue() throws Exception {
        String title = "festival";
        mockMvc.perform(get("/v1/events/title-like")
                        .param("title", title)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Festival du ziglibiti"));
    }

    @Test
    @Order(5)
    void findEventsByTown() throws Exception {
        String town = "Abi";
        mockMvc.perform(get("/v1/events/town-like")
                        .param("town", town)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].physicalAdresse.town").value("Abidjan"));
    }

    @Test
    @Order(6)
    void findByVisibilityIsTrue() throws Exception {
        String page = "1";
        String size = "2";
        mockMvc.perform(get("/v1/events/paginated")
                        .param("page", page)
                        .param("size", size)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().stringValues("Total-Elements", "3"))
                .andExpect(header().stringValues("Total-Pages", "2"));
    }

    @Test
    @Order(7)
    void update() throws Exception {
        Event event = buildEvent();
        event.setTitle("Concert de Ernesto Djédjé");
        event.setEventOrganizer(EventOrganizer.builder()
                .id(UUID.randomUUID().toString())
                .name("djédjé prod").email("djedje@gmail.com").build());

        Event oldEvent = eventService.findByIdAndVisibilityIsTrue(EVENT1_ID);

        mockMvc.perform(put("/v1/events/"+EVENT1_ID)
                        .content(objectMapper.writeValueAsBytes(eventMapper.toResource(event)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value(oldEvent.getVersion()+1));
    }

    @Test
    @Order(8)
    void publish() throws Exception {
        mockMvc.perform(put("/v1/events/publish/"+EVENT1_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.publicationDate").exists())
                .andExpect(jsonPath("$.visibility").value(true));
    }

    @Test
    @Order(9)
    void addTicketCategorie() throws Exception {
        TicketCategorie ticketCategorie = TicketCategorie.builder()
                .categorieCode(TICKET_CATEGORIE_CODE)
                .categorieName("stater")
                .description("Low standing").price(10000d).quantity(100)
                .startDateOfSales(LocalDateTime.parse("2023-10-05T00:00:00"))
                .endDateOfSales(LocalDateTime.parse("2023-10-20T00:00:00")).build();

        mockMvc.perform(put("/v1/events/"+EVENT1_ID+"/ticket-categorie/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(ticketCategorie)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticketCategories").isNotEmpty())
                .andExpect(jsonPath("$.ticketCategories[0].price").value(10000d));
    }

    @Test
    @Order(10)
    void updateTicketCategorie() throws Exception {
        TicketCategorie updateTicketCategorie = TicketCategorie.builder()
                .categorieCode(TICKET_CATEGORIE_CODE)
                .categorieName("stater updated")
                .description("Low standing").price(10500d).quantity(90)
                .startDateOfSales(LocalDateTime.parse("2023-10-05T00:00:00"))
                .endDateOfSales(LocalDateTime.parse("2023-10-20T00:00:00")).build();

        mockMvc.perform(put("/v1/events/"+EVENT1_ID+"/ticket-categorie/update/"+TICKET_CATEGORIE_CODE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(updateTicketCategorie)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticketCategories").isNotEmpty())
                .andExpect(jsonPath("$.ticketCategories[0].price").value(10500d))
                .andExpect(jsonPath("$.ticketCategories[0].quantity").value(90))
                .andExpect(jsonPath("$.ticketCategories[0].categorieName").value("stater updated"))
                .andExpect(jsonPath("$.ticketCategories[0].description").value("Low standing"));
    }

    @Test
    @Order(11)
    void removeTicketCategorie() throws Exception {

        mockMvc.perform(put("/v1/events/"+EVENT1_ID+"/ticket-categorie/remove/"+TICKET_CATEGORIE_CODE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticketCategories").isEmpty());
    }

    @Test
    @Order(12)
    void deleteEvent() throws Exception {
        mockMvc.perform(delete("/v1/events/"+EVENT1_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private Event buildEvent(){
        return Event.builder()
                .id(EVENT1_ID)
                .title("Festival du ziglibiti")
                .eventType("Festival")
                .eventCategory("")
                .tags(List.of("danse", "ziglibiti", "Abidjan", "côte d'ivoire"))
                .imageCover("")
                .visibility(true)
                .createdBy("23ff1ef4-283a-4a6a-9e73-c33c6bb53d73")
                .build();
    }

    private void buildAndSaveTwoEvents(){
        Event event2 = buildEvent(EVENT2_ID,
                "Festival du gbégbé", "Festival", "", "", "Lorem ipsum", "Lorem ipsum dolor", "23ff1ef4-283a-4a6a-9e73-c33c6bb53d73");
        event2.setStartingDate(LocalDate.of(2023, 7, 1));
        event2.setVisibility(true);
        event2.setPhysicalAdresse(PhysicalAdresse.builder().country("CIV").town("Daloa").build());

        Event event3 = buildEvent(EVENT3_ID,
                "Concert de John Yalley", "Concert", "", "", "Lorem ipsum", "Lorem ipsum dolor", "23ff1ef4-283a-4a6a-9e73-c33c6bb53d73");
        event3.setStartingDate(LocalDate.of(2023, 8, 1));
        event3.setVisibility(true);
        event3.setPhysicalAdresse(PhysicalAdresse.builder().country("CIV").town("Abidjan").build());

        eventService.create(event2);
        eventService.create(event3);
    }

    private Event buildEvent(String id, String... params){
        String title = (params.length > 0) ? params[0] : "";
        String eventType = (params.length > 1) ? params[1] : "";
        String eventCategory = (params.length > 2) ? params[2] : "";
        String imageCover = (params.length > 3) ? params[3] : "";
        String resume = (params.length > 4) ? params[4] : "";
        String description = (params.length > 5) ? params[5] : "";
        String createdBy = (params.length > 6) ? params[6] : "";
        return Event.builder()
                .id(id)
                .title(title)
                .eventType(eventType)
                .eventCategory(eventCategory)
                .imageCover(imageCover)
                .resume(resume)
                .description(description)
                .createdBy(createdBy)
                .build();
    }

}
