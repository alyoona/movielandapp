package com.stroganova.movielandapp.service.impl;

import com.stroganova.movielandapp.dao.PosterDao;
import com.stroganova.movielandapp.service.PosterService;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultPosterService implements PosterService {

   private final PosterDao posterDao;

    @Override
    public void link(long movieId, String picturePath) {
        posterDao.link(movieId, picturePath);
    }

    @Override
    public void update(long movieId, String picturePath) {
        if (picturePath != null) {
            posterDao.update(movieId, picturePath);
        }

    }
}
