package jnetgraph.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jnetgraph.dto.SpeedtestCLIDTO;
import jnetgraph.mapper.SpeedtestCLIMapper;
import jnetgraph.repository.SpeedtestCLIRepository;
import jnetgraph.repository.UserRepository;
import jnetgraph.service.SpeedtestCLIService;
import jnetgraph.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc
public class SpeedtestCLIControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private SpeedtestCLIRepository speedtestCLIRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext context;


    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void getDataForPeriod() throws Exception {
        mvc.perform(get("/rest/api/SpeedtestCLI.svc/speedtestcli(1)2021-02-01till2021-02-10"))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"stcliId\":1,\"execTimestamp\":1612981038108,\"jitterMS\":0.379," +
                        "\"latencyMS\":2.124,\"downloadSpeedMbps\":35.0,\"uploadSpeedMbps\":38.545456,\"packetLossPercentage\":0.0}]"));


    }


    //TODO: Viņš neapstājas
    @Test
    @WithMockUser
    public void addNewEntry() throws Exception {
//        Whitebox.setInternalState(speedtestCLIService, "check", false);


        SpeedtestCLIDTO speedtestCLIDTO = new SpeedtestCLIDTO();
        speedtestCLIDTO.setJitterMS(10);
        speedtestCLIDTO.setLatencyMS(10.56f);
        speedtestCLIDTO.setDownloadSpeedMbps(30.56f);
        speedtestCLIDTO.setUploadSpeedMbps(30.080f);
        speedtestCLIDTO.setPacketLossPercentage(1.080f);


        mvc.perform(get("/rest/api/SpeedtestCLI.svc/speedtestcli/1")
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(speedtestCLIDTO)))
                .andExpect(status().isOk())

        ;






//        speedtestCLIService.setCheck(false);

//        mvc.perform(post("/rest/api/SpeedtestCLI.svc/speedtestcli/1/stop"));

        assertEquals(1,speedtestCLIRepository.findAll().size());

    }
}
