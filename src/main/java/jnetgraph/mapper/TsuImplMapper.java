package jnetgraph.mapper;

import jnetgraph.dto.TsuImplDTO;
import jnetgraph.model.TsuImpl;
import org.springframework.stereotype.Component;

@Component
public class TsuImplMapper {

    public TsuImplDTO toDTO(TsuImpl tsuImpl) {
        return new TsuImplDTO(
                tsuImpl.getExec_timestamp(),
                tsuImpl.getResponse_time(),
                tsuImpl.getDownload_speed()
        );
    }

    public TsuImpl fromDTO(TsuImplDTO tsuImplDTO) {
        return new TsuImpl(
                tsuImplDTO.getExec_timestamp(),
                tsuImplDTO.getResponse_time(),
                tsuImplDTO.getDownload_speed()
        );
    }

}
