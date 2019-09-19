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
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DefaultPosterService implements PosterService {

    @NonNull
    PosterDao posterDao;

    @Override
    public void add(long movieId, String picturePath) {
        posterDao.add(movieId, picturePath);
    }
}
