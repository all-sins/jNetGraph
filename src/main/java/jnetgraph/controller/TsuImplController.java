package jnetgraph.controller;

import jnetgraph.dto.MeasuringInfoDTO;
import jnetgraph.dto.TsuImplDTO;
import jnetgraph.dto.TsuImplWithUserDTO;
import jnetgraph.dto.UserPeriodRequestDTO;
import jnetgraph.mapper.StringToDate;
import jnetgraph.mapper.TsuImplMapper;
import jnetgraph.mapper.TsuImplWithUserDTOMapper;
import jnetgraph.model.TsuImpl;
import jnetgraph.model.User;
import jnetgraph.probe.TsuImplProbe;
import jnetgraph.repository.TsuImplRepository;
import jnetgraph.repository.UserRepository;
import jnetgraph.service.TsuImplService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.text.ParseException;
import java.util.*;


@RestController
@RequestMapping("/rest/api/TsuImplProbe.svc")
public class TsuImplController {

    private final TsuImplService tsuImplService;
    private final TsuImplRepository tsuImplRepository;
    private final TsuImplMapper tsuImplMapper;
    private final TsuImplWithUserDTOMapper tsuImplWithUserDTOMapper;
    private final UserRepository userRepository;

    private final org.slf4j.Logger log = LoggerFactory.getLogger(TsuImplProbe.class);

    @Autowired
    public TsuImplController(TsuImplService tsuImplService,
                             TsuImplRepository tsuImplRepository,
                             TsuImplMapper tsuImplMapper,
                             TsuImplWithUserDTOMapper tsuImplWithUserDTOMapper,
                             UserRepository userRepository) {
        this.tsuImplService = tsuImplService;
        this.tsuImplRepository = tsuImplRepository;
        this.tsuImplMapper = tsuImplMapper;
        this.tsuImplWithUserDTOMapper = tsuImplWithUserDTOMapper;
        this.userRepository = userRepository;
    }

    // What is the difference between all these different HTTP
    // requests on a lower level? Say, what differentiates a
    // POST from a PUT?

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/measuring/allData")
    List<TsuImplWithUserDTO> getAllData() {
        List<TsuImpl> all = tsuImplRepository.findAll();
        List<TsuImplWithUserDTO> convAll = new ArrayList<>();
        for (TsuImpl item : all) {
            convAll.add(tsuImplWithUserDTOMapper.toDTO(item));
        }
        return convAll;
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/measuring/user({id})/all")
    List<TsuImplDTO> getAllData(@PathVariable Long id) {
        List<TsuImpl> all = tsuImplRepository.getDataWithUserID(id);
        List<TsuImplDTO> converted = new ArrayList<>();
        for (TsuImpl item : all) {
            converted.add(tsuImplMapper.toDTO(item));
        }
        return converted;
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/measuring/user({userId})/from({from})/to({to})")
    List<TsuImplDTO> getDataFromTo(@PathVariable String from,
                                   @PathVariable String to,
                                   @PathVariable Long userId) throws ParseException {

        // Execute custom method with custom SQL to get specific data in period from user with ID.
        List<TsuImpl> results = tsuImplService.getDataForPeriod(from, to, userId);

        // Prep return list.
        List<TsuImplDTO> convertedResults = new ArrayList<>();

        // Convert each item in results to a TsuImplDTO and then add it to the prepped return list.
        results.forEach( (tsuImpl) -> {
            convertedResults.add(tsuImplMapper.toDTO(tsuImpl));
        });

        return convertedResults;
    }

    // Experimental.
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/measuring/user/fromTo")
    List<TsuImplDTO> getDataFromToExp(@RequestBody UserPeriodRequestDTO dto) {

        return null;

    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/measuring/config/interval/reset)")
    MeasuringInfoDTO resetMeasuringInterval() {
        log.debug("[TsuImpl] Sleep interval changed " +
                "from "+ tsuImplService.getInterval() + " ms (" + tsuImplService.getInterval() / 1000 + " sec) " +
                "to "+ 60000L +" ms ("+ 60000L / 1000 +" sec)");
        tsuImplService.setInterval(60000L);
        return new MeasuringInfoDTO(
                String.valueOf(tsuImplService.isMeasuringRunning()),
                "Measuring interval successfully changed.",
                60000L,
                tsuImplService.getUserIdToMeasure()
        );
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/measuring/config/interval/set({longMilis})")
    MeasuringInfoDTO changeMeasuringInterval(@PathVariable Long longMilis) {
        log.debug("[TsuImpl] Sleep interval changed " +
                "from "+ tsuImplService.getInterval() + " ms (" + tsuImplService.getInterval() / 1000 + " sec) " +
                "to "+ longMilis +" ms ("+ longMilis / 1000 +" sec)");
        tsuImplService.setInterval(longMilis);
        return new MeasuringInfoDTO(
                String.valueOf(tsuImplService.isMeasuringRunning()),
                "Measuring interval successfully changed.",
                longMilis,
                tsuImplService.getUserIdToMeasure()
        );
    }

    // Toggle the measuring thread for a specific user.
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/measuring/user({id})/{toggle}")
    MeasuringInfoDTO measuringToggle(@PathVariable String toggle, @PathVariable Long id) {

        // Create MeasuringInfoDTO to return info back to client in the form of a serialized JSON.
        // Jackson is the library that handles it under the hood and it's a part of Spring Boot.
        MeasuringInfoDTO infoDTO = new MeasuringInfoDTO();

        // If Optional is present then the user with the specified ID was found.
        Optional<User> opt = userRepository.findById(id);
        if (opt.isPresent()) {

            // Check for proper usage of path variable. ("on" or "off" case ignored.)
            if (toggle.equalsIgnoreCase("on")) {
                if (tsuImplService.isMeasuringRunning()) {
                    infoDTO.setDetails("Measurer already enabled. Ignoring!");
                } else {
                    tsuImplService.setMeasuringRunning(true);
                    tsuImplService.setUserIdToMeasure(id);
                    tsuImplService.runMeasurer(opt.get());
                    infoDTO.setDetails("Measurer enabled.");
                }
            } else if (toggle.equalsIgnoreCase("off")) {
                if (tsuImplService.isMeasuringRunning()) {
                    tsuImplService.setMeasuringRunning(false);
                    infoDTO.setDetails("Measurer was enabled, but now is disabled.");
                } else {
                    infoDTO.setDetails("Measurer already disabled. Ignoring!");
                }
            } else {
                infoDTO.setDetails("Unrecognized command! Use /measuring/{toggle}({id}). (ON or OFF - Case ignored.)");
            }

            // If the returned Optional is empty -> no user with given ID was found.
        } else {
            infoDTO.setDetails("User with ID(" + id + ") doesn't exist!");
        }

        // Return infoDTO with information about the operation.
        infoDTO.setUserId(tsuImplService.getUserIdToMeasure());
        infoDTO.setRunning(String.valueOf(tsuImplService.isMeasuringRunning()));
        return infoDTO;
    }

}
