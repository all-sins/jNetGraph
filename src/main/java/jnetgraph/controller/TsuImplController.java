package jnetgraph.controller;

import jnetgraph.dto.MeasuringInfoDTO;
import jnetgraph.dto.TsuImplDTO;
import jnetgraph.mapper.TsuImplMapper;
import jnetgraph.model.TsuImpl;
import jnetgraph.repository.TsuImplRepository;
import jnetgraph.service.TsuImplService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest/api/TsuImplProbe.svc")
public class TsuImplController {

    private final TsuImplService tsuImplService;
    private final TsuImplRepository tsuImplRepository;
    private final TsuImplMapper tsuImplMapper;

    @Autowired
    public TsuImplController(TsuImplService tsuImplService, TsuImplRepository tsuImplRepository, TsuImplMapper tsuImplMapper) {
        this.tsuImplService = tsuImplService;
        this.tsuImplRepository = tsuImplRepository;
        this.tsuImplMapper = tsuImplMapper;
    }

    // What is the difference between all these different HTTP
    // requests on a lower level? Say, what differentiates a
    // POST from a PUT?

    // TODO: Add controller functionality.
    @GetMapping("/tsuImpl")
    List<TsuImplDTO> getAllData() {
        List<TsuImpl> all = tsuImplRepository.findAll();
        List<TsuImplDTO> convAll = new ArrayList<>();
        for (TsuImpl item : all) {
            convAll.add(tsuImplMapper.toDTO(item));
        }
        return convAll;
    }

    // TODO: Make the controller return JSON error/status update.
    @PostMapping("/measuring({toggle})")
    MeasuringInfoDTO measuringToggle(@PathVariable String toggle) {

        // Toggle the measuring thread in case of proper usage of path variable.
        // "on" or "off" case ignored.
        MeasuringInfoDTO infoDTO = new MeasuringInfoDTO();
        if (toggle.equalsIgnoreCase("on")) {
            if (tsuImplService.isMeasuringRunning()) {
                infoDTO.setDetails("Measurer already enabled. Ignoring!");
            } else {
                tsuImplService.setMeasuringRunning(true);
                tsuImplService.runMeasurer();
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
            infoDTO.setDetails("Unrecognized command! Use /measuring(ON) or /measuring(OFF). (Case ignored.)");
        }
        infoDTO.setRunning(String.valueOf(tsuImplService.isMeasuringRunning()));
        return infoDTO;
    }

}
