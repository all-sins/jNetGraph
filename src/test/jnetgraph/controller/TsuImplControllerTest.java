package jnetgraph.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jnetgraph.repository.TsuImplRepository;
import jnetgraph.service.TsuImplService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
If you are using JUnit 4, don’t forget to add @RunWith(SpringRunner.class) to your test, otherwise the annotations will
be ignored. If you are using JUnit 5, there’s no need to add the equivalent @ExtendWith(SpringExtension.class) as
@SpringBootTest and the other @…TestAnnotations are already annotated with it.
*/
@SpringBootTest

// Required because JUnit 4.12 is used.
@RunWith(SpringRunner.class)

// Can't autowire MockMvc without this annotation.
@AutoConfigureMockMvc

@TestPropertySource(locations = "classpath:application-test.properties")

public class TsuImplControllerTest {

    @Autowired
    private TsuImplRepository tsuImplRepository;

    @Autowired
    private TsuImplService tsuImplService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final long INTERVAL = 1L;
    private static final long FIRST_USER_ID = 1L;
    private static final long SECOND_USER_ID = 2L;
    private static final long TIME_TO_WAIT_FOR_DATA = 15000L;

    private static boolean SETUP_DONE = false;

    @Before
    public void setup() throws Exception {

        // Ignore setup if it's done already.
        if (SETUP_DONE) {
            return;
        }

        // TODO: What the hell is this.
        // Mara says:
        // Tas brīnums manuprāt bija vajadzīgs lai mockam varētu padot lietotaaju, kurš laiž metodi.
        // Un tas wbAppContextSetup vnkpasaka lai viņš setapu ņem no mūsu esošā konteksta...ne? makes sense?
        // mockMvc = MockMvcBuilders
        //        .webAppContextSetup(webAppContext)
        //        .apply(springSecurity())
        //        .build();

        // SETUP TEST DATA.
        // Change measuring interval to 1ms, so that measuring runs nonstop as fast as possible.
        mockMvc.perform(
                post("/rest/api/TsuImplProbe.svc/measuring/config/interval/set("+ INTERVAL +")")
                        .with(csrf()).param("action", "signup")
        )
                .andExpect(status().isOk());


        // [FIRST_USER_ID] Start measuring.
        mockMvc.perform(
                post("/rest/api/TsuImplProbe.svc/measuring/user("+ FIRST_USER_ID +")/on")
                        .with(csrf()).param("action", "signup")
        )
                .andExpect(status().isOk());

        Thread.sleep(TIME_TO_WAIT_FOR_DATA + (long) (INTERVAL * 1.5));

        // [FIRST_USER_ID] Stop measuring.
        mockMvc.perform(
                post("/rest/api/TsuImplProbe.svc/measuring/user("+ FIRST_USER_ID +")/off")
                        .with(csrf()).param("action", "signup")
        )
                .andExpect(status().isOk());

        // [SECOND_USER_ID] Start measuring.
        mockMvc.perform(
                post("/rest/api/TsuImplProbe.svc/measuring/user("+ SECOND_USER_ID +")/on")
                        .with(csrf()).param("action", "signup")
        )
                .andExpect(status().isOk());

        Thread.sleep(TIME_TO_WAIT_FOR_DATA + (long) (INTERVAL * 1.5));

        // [SECOND_USER_ID] Stop measuring.
        mockMvc.perform(
                post("/rest/api/TsuImplProbe.svc/measuring/user("+ SECOND_USER_ID +")/off")
                        .with(csrf()).param("action", "signup")
        )
                .andExpect(status().isOk());

        // Wait for measuring to be completely finished.
        Thread.sleep(5000L);

        // Setup done flag set.
        SETUP_DONE = true;

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getAllData() throws Exception {

        // Check response.
        MvcResult mvcResult = mockMvc.perform(get("/rest/api/TsuImplProbe.svc/measuring/allData")).andReturn();

        // TODO: Count database entries. ONLY NON-CONTROLLER USING PART OF THE TEST. Maybe add controller functionality?
        long amountInDB = tsuImplRepository.count();

        // Map the incoming serialized JSON String to a workable Java Object called JsonNode and then use that to
        // determine how many fields it has. (JsonNode.size() does that.)
        int amountOfUserIDs = objectMapper.readTree(mvcResult.getResponse().getContentAsString()).size();

        // Assert that the amount of rows in the database is equal to the amount of fields in received JSON.
        assertEquals(amountInDB, amountOfUserIDs);

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getAllDataWithId() throws Exception {

        // Request data from controller.
        MvcResult mvcResult1 = mockMvc.perform(get("/rest/api/TsuImplProbe.svc/measuring/user("+ FIRST_USER_ID +")/all")).andReturn();
        MvcResult mvcResult2 = mockMvc.perform(get("/rest/api/TsuImplProbe.svc/measuring/user("+ SECOND_USER_ID +")/all")).andReturn();

        System.out.println("mvcResult1 = " + mvcResult1.getResponse().getContentAsString());
        System.out.println("mvcResult2 = " + mvcResult2.getResponse().getContentAsString());

        // The request returns a special DTO that doesn't include the "user_id", because the id was already specified
        // in the request, therefore there is no reason to include it. Because of that the test uses "exec_timestamp"
        // fields to count instead.
        String respStr1 = mvcResult1.getResponse().getContentAsString();
        JsonNode json1 = objectMapper.readTree(respStr1);
        int size1 = json1.size();
        // long user1IDs = objectMapper.readTree(mvcResult1.getResponse().getContentAsString()).findValues("exec_timestamp").size();

        String respStr2 = mvcResult2.getResponse().getContentAsString();
        JsonNode json2 = objectMapper.readTree(respStr2);
        int size2 = json2.size();
        // long user2IDs = objectMapper.readTree(mvcResult2.getResponse().getContentAsString()).findValues("exec_timestamp").size();

        // TODO: Create repository level methods for counting amount of data with user.
        // This problem becomes more complicated because there's relation going on, since you don't want the PK of
        // tsu_impl table, but the user_fk. For now this works with a very ugly solution.

        // long user1InDB = tsuImplRepository.countById(FIRST_USER_ID);
        long user1InDB = tsuImplRepository.getDataWithUserID(FIRST_USER_ID).size();
        // long user2InDB = tsuImplRepository.countById(SECOND_USER_ID);
        long user2InDB = tsuImplRepository.getDataWithUserID(SECOND_USER_ID).size();

        // Assert that the amount of rows in the database is equal to the amount of "user_id" occurrences in the
        // received JSON.
        // assertEquals(user1InDB, user1IDs);
        // assertEquals(user2InDB, user2IDs);
        assertEquals(user1InDB, size1);
        assertEquals(user2InDB, size2);

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getDataFromTo() {
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getDataFromToExp() {
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void resetMeasuringInterval() throws Exception {

        mockMvc.perform(
                post("/rest/api/TsuImplProbe.svc/measuring/config/interval/reset")
                        .with(csrf()).param("action", "signup")
        )
                .andExpect(status().isOk());

        assertEquals(tsuImplService.getInterval(), 60000L);

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void changeMeasuringInterval() throws Exception {

        mockMvc.perform(
                post("/rest/api/TsuImplProbe.svc/measuring/config/interval/set(60001)")
                        .with(csrf()).param("action", "signup")
        )
                .andExpect(status().isOk());

        assertEquals(tsuImplService.getInterval(), 60001L);

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void measuringToggle() {
    }

}
