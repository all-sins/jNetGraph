package jnetgraph.service;

import jnetgraph.model.TsuImpl;
import jnetgraph.repository.TsuImplRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TsuImplService {

    private final TsuImplRepository tsuImplRepository;

    @Autowired
    public TsuImplService(TsuImplRepository tsuImplRepository) {
        this.tsuImplRepository = tsuImplRepository;
    }

    // TODO: Figure out how to finish TsuImplService implementation.
    // How would you find a specific entry? What would you use for the query instead of an id?

    public TsuImpl addNewTsuImpl(TsuImpl tsuImpl) {return tsuImplRepository.save(tsuImpl);}

    public void deleteTsuImpl(TsuImpl tsuImpl) {tsuImplRepository.delete(tsuImpl);}

}
