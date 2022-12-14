package org.thuanthanhtech.mymuseummanagement.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.MessageDescriptorFormatException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thuanthanhtech.mymuseummanagement.entity.Media;
import org.thuanthanhtech.mymuseummanagement.repository.MediaRepository;
import org.thuanthanhtech.mymuseummanagement.service.MediaService;
import org.thuanthanhtech.mymuseummanagement.utils.Constants;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    private final MediaRepository mediaRepository;

    @SneakyThrows
    @Override
    public void deleteMedia(Long id) {
        List<Media> media = mediaRepository.findAllByIdAndStatus(id, Constants.STATUS_ACTIVE);
        if (CollectionUtils.isEmpty(media)) {
            throw new MessageDescriptorFormatException("Can not found!");
        }
        for (Media medias : media) {
            medias.setStatus(Constants.STATUS_INACTIVE);
            medias.setModifiedDate(new Date());
            mediaRepository.save(medias);
        }
    }

    @Override
    public List<Media> getAllMedia() {
        return mediaRepository.findAll();
    }
}
