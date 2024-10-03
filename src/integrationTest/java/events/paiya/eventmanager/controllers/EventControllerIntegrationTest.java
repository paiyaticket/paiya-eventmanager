package events.paiya.eventmanager.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import events.paiya.eventmanager.domains.Event;
import events.paiya.eventmanager.domains.EventOrganizer;
import events.paiya.eventmanager.domains.PhysicalAddress;
import events.paiya.eventmanager.enumeration.EventType;
import events.paiya.eventmanager.mappers.EventMapper;
import events.paiya.eventmanager.services.EventService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.*;
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

    MockMvc mockMvc;

    @Autowired
    private EventService eventService;

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
        String ownerId = "owner@gmail.com";
        mockMvc.perform(get("/v1/events/owned-by")
                        .param("owner", ownerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$[0].owner").value(ownerId));
    }

    @Test
    @Order(3)
    void findEventsByStartingDateBetweenAndVisibilityIsTrue()  throws Exception {
        String minDate = "2023-06-30T12:00:00Z";
        String maxDate = "2023-09-30T12:00:00Z";
        this.buildAndSaveTwoEvents();
        mockMvc.perform(get("/v1/events/date-between")
                        .param("minDate", minDate)
                        .param("maxDate", maxDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
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
                .andExpect(jsonPath("$[0].physicalAddress.town").value("Abidjan"));
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

        Event oldEvent = eventService.findByIdAndPublishedIsTrue(EVENT1_ID);

        mockMvc.perform(patch("/v1/events/"+EVENT1_ID)
                        .content(objectMapper.writeValueAsBytes(eventMapper.toResource(event)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version").value(oldEvent.getVersion()+1));
    }

    @Test
    @Order(8)
    void publish() throws Exception {
        mockMvc.perform(patch("/v1/events/publish/"+EVENT1_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.publicationDate").exists())
                .andExpect(jsonPath("$.published").value(true));
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
                .eventType(EventType.SINGLE_EVENT)
                .eventCategory("")
                .tags(List.of("danse", "ziglibiti", "Abidjan", "côte d'ivoire"))
                .imageCover("")
                .published(true)
                .owner("owner@gmail.com")
                .build();
    }

    private void buildAndSaveTwoEvents(){
        Event event2 = buildEvent(EVENT2_ID,
                "Festival du gbégbé", "Festival", "", "", "Lorem ipsum", "Lorem ipsum dolor", "owner@gmail.com");
        event2.setStartTime(Instant.parse("2023-07-01T12:00:00Z"));
        event2.setPublished(true);
        event2.setPhysicalAddress(PhysicalAddress.builder().country("CIV").town("Daloa").build());

        Event event3 = buildEvent(EVENT3_ID,
                "Concert de John Yalley", "Concert", "", "", "Lorem ipsum", "Lorem ipsum dolor", "owner@gmail.com");
        event2.setStartTime(Instant.parse("2023-08-01T12:00:00Z"));
        event3.setPublished(true);
        event3.setPhysicalAddress(PhysicalAddress.builder().country("CIV").town("Abidjan").build());

        eventService.create(event2);
        eventService.create(event3);
    }

    private Event buildEvent(String id, String... params){
        String title = (params.length > 0) ? params[0] : "";
        String eventCategory = (params.length > 2) ? params[2] : "";
        String imageCover = (params.length > 3) ? params[3] : "";
        String summary = (params.length > 4) ? params[4] : "";
        String description = (params.length > 5) ? params[5] : "";
        String createdBy = (params.length > 6) ? params[6] : "";
        return Event.builder()
                .id(id)
                .title(title)
                .eventType(EventType.SINGLE_EVENT)
                .eventCategory(eventCategory)
                .imageCover(imageCover)
                .summary(summary)
                .description(description)
                .owner(createdBy)
                .build();
    }

}
