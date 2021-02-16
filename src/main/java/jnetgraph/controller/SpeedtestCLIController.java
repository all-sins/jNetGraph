package jnetgraph.controller;

import jnetgraph.dto.SpeedtestCLIDTO;
import jnetgraph.mapper.SpeedtestCLIMapper;
import jnetgraph.service.SpeedtestCLIService;
import jnetgraph.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
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

//    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/speedtestcli/{userId}")
    public void addNewEntry(@PathVariable("userId") Long userId)  {
        speedtestCLIService.setCheck(true);
        speedtestCLIService.createNewEntry(userService.findById(userId));

    }

    //Controller method to stop getting data. If not called speedtestCLIService.createNewEntry() will keep running.
//    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/speedtestcli/stop")
    public void stop() {
        speedtestCLIService.setCheck(false);

    }

    @GetMapping("/speedtestcli")
//    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public List<SpeedtestCLIDTO> getAll(){
        return speedtestCLIService.getAll().stream().map(speedtestCLIMapper::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/speedtestcli({id})")
//    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public List<SpeedtestCLIDTO> getAllForUser(@PathVariable("id") Long id){
        return  speedtestCLIService.getAllForUser(id).stream().map(speedtestCLIMapper::toDTO).collect(Collectors.toList());
    }

    //Getting all existing entries for particular userId for particular time period.
    @GetMapping("/speedtestcli({userId}){startDate}till{endDate}")
//    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public List<SpeedtestCLIDTO> getDateForPeriod(@PathVariable("startDate") String startDate,
                                                  @PathVariable("endDate") String endDate,
                                                  @PathVariable("userId") String userId) throws ParseException {

        return speedtestCLIService.getDataForPeriod(startDate, endDate, userId).stream().map(speedtestCLIMapper::toDTO).collect(Collectors.toList());


    }

}
