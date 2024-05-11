package com.pdp.web.service.picture;

import com.pdp.web.model.picture.Picture;
import com.pdp.web.repository.picture.PictureRepository;
import com.pdp.web.service.BaseService;

import java.util.List;

public interface PictureService extends BaseService<Picture, List<Picture>> {
    PictureRepository repository = PictureRepository.getInstance();
}
