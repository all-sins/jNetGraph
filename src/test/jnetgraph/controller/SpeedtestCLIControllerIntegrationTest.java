package jnetgraph.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jnetgraph.dto.SpeedtestCLIDTO;
import jnetgraph.mapper.SpeedtestCLIMapper;
import jnetgraph.repository.SpeedtestCLIRepository;
import jnetgraph.repository.UserRepository;
import jnetgraph.service.SpeedtestCLIService;
import jnetgraph.service.UserService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.security.test.context.support.WithMockUser;

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
    private SpeedtestCLIService speedtestCLIService;
    @Autowired
    private SpeedtestCLIMapper speedtestCLIMapper;


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


        mvc.perform(post("/rest/api/SpeedtestCLI.svc/speedtestcli/1")
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(speedtestCLIDTO)))
                .andExpect(status().isOk())

        ;






//        speedtestCLIService.setCheck(false);

//        mvc.perform(post("/rest/api/SpeedtestCLI.svc/speedtestcli/1/stop"));

        assertEquals(1,speedtestCLIRepository.findAll().size());

    }
}
