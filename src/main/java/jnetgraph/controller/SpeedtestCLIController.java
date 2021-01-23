package jnetgraph.controller;
import jnetgraph.model.SpeedtestCLI;
import jnetgraph.service.SpeedtestCLIService;
import jnetgraph.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/rest/api/SpeedtestCLI.svc")
public class SpeedtestCLIController {

    private final SpeedtestCLIService speedtestCLIService;
    private final UserService userService;

    @Autowired
    public SpeedtestCLIController(SpeedtestCLIService speedtestCLIService, UserService userService) {
        this.speedtestCLIService = speedtestCLIService;
        this.userService = userService;
    }

    @PostMapping("/speedtestcli/{userId}")
    public SpeedtestCLI addNewEntry(@PathVariable("userId") Long userId) throws IOException {
        return speedtestCLIService.createNewEntry(userService.findById(userId));

    }
}
