package com.pdp.web.service.picture;

import com.pdp.web.model.picture.Picture;
import com.pdp.web.repository.picture.PictureRepository;
import com.pdp.web.service.BaseService;

import java.util.List;

/**
 * Interface for services related to managing pictures.
 * This service provides basic CRUD operations for picture entities.
 *
 * @see Picture
 * @see PictureRepository
 */
public interface PictureService extends BaseService<Picture, List<Picture>> {
    /**
     * The singleton instance of the PictureRepository.
     */
    PictureRepository repository = new PictureRepository();
}
