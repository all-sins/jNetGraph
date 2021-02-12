package jnetgraph.mapper;

import jnetgraph.dto.TsuImplWithUserDTO;
import jnetgraph.model.TsuImpl;
import org.springframework.stereotype.Component;

@Component
public class TsuImplWithUserDTOMapper {

    public TsuImplWithUserDTO toDTO(TsuImpl entity) {
        return new TsuImplWithUserDTO(
                entity.getExec_timestamp(),
                entity.getResponse_time(),
                entity.getDownload_speed(),
                entity.getUser().getId()
        );
    }

    public TsuImpl fromDTO(TsuImplWithUserDTO dto) {
        return new TsuImpl(
                dto.getExec_timestamp(),
                dto.getResponse_time(),
                dto.getDownload_speed()
        );
    }

}
