package org.binaracademy.bioskopbackend.service;

import org.binaracademy.bioskopbackend.model.Movie;
import org.springframework.stereotype.Service;

public interface OrderService {

    Boolean orderMovie(Movie movie);

}
