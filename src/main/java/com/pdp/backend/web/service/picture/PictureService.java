package com.pdp.backend.web.service.picture;

import com.pdp.backend.web.model.picture.Picture;
import com.pdp.backend.web.repository.picture.PictureRepository;
import com.pdp.backend.web.service.BaseService;

import java.util.List;

public interface PictureService extends BaseService<Picture, List<Picture>> {
    PictureRepository repository = new PictureRepository();
}
