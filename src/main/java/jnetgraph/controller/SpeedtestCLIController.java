package jnetgraph.controller;

import jnetgraph.dto.SpeedtestCLIDTO;
import jnetgraph.mapper.SpeedtestCLIMapper;
import jnetgraph.model.SpeedtestCLI;
import jnetgraph.service.SpeedtestCLIService;
import jnetgraph.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/api/SpeedtestCLI.svc")
public class SpeedtestCLIController {

    private final SpeedtestCLIService speedtestCLIService;
    private final UserService userService;
    private final SpeedtestCLIMapper speedtestCLIMapper;


    @Autowired
    public SpeedtestCLIController(SpeedtestCLIService speedtestCLIService, UserService userService, SpeedtestCLIMapper speedtestCLIMapper) {
        this.speedtestCLIService = speedtestCLIService;
        this.userService = userService;
        this.speedtestCLIMapper = speedtestCLIMapper;
    }

//    @PostMapping("/speedtestcli/{userId}")
//    public SpeedtestCLI addNewEntry(@PathVariable("userId") Long userId) throws IOException {
//            return speedtestCLIService.createNewEntry(userService.findById(userId));
//
//    }

    @PostMapping("/speedtestcli/{userId}")
    public void addNewEntry(@PathVariable("userId") Long userId) throws IOException, InterruptedException {
        speedtestCLIService.setCheck(true);
        speedtestCLIService.createNewEntry(userService.findById(userId));

    }

    @PostMapping("/speedtestcli/{userId}/stop")
    public void stop(@PathVariable("userId") Long userId) throws IOException {
        speedtestCLIService.setCheck(false);

    }


    @GetMapping("/speedtestcli{startDate}till{endDate}")
    public List<SpeedtestCLIDTO> getDateForPeriod(@PathVariable("startDate") String startDate,
                                                  @PathVariable("endDate") String endDate) throws ParseException {

        return speedtestCLIService.getDataForPeriod(startDate, endDate).stream().map(speedtestCLIMapper::toDTO).collect(Collectors.toList());


    }
}
