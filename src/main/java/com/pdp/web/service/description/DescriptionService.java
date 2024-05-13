package com.pdp.web.service.description;

import com.pdp.web.model.description.Description;
import com.pdp.web.repository.description.DescriptionRepository;
import com.pdp.web.service.BaseService;

import java.util.List;

/**
 * Service interface for managing descriptions.
 * <p>
 * This interface defines methods for adding, removing, updating, searching,
 * and retrieving descriptions within the system.
 * </p>
 * <p>
 * Implementing classes should provide functionality to interact with a repository
 * storing descriptions, typically through a {@link DescriptionRepository}.
 * </p>
 *
 * @param <T> the type of description entity
 * @param <L> the type of list containing descriptions
 * @author Nishonov Doniyor
 * @see DescriptionRepository For managing the storage of descriptions.
 * @see BaseService Base service interface providing common methods for service implementations.
 */
public interface DescriptionService extends BaseService<Description, List<Description>> {
    DescriptionRepository repository = DescriptionRepository.getInstance();
}
