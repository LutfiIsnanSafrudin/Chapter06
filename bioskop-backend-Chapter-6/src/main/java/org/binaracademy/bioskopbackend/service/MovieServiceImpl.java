package org.binaracademy.bioskopbackend.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.binaracademy.bioskopbackend.model.Movie;
import org.binaracademy.bioskopbackend.model.Schedule;
import org.binaracademy.bioskopbackend.model.response.MovieResponse;
import org.binaracademy.bioskopbackend.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Async
    @Override
    public List<MovieResponse> getAllMovie() {
        log.info("Starting to get All movie");
        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return movieRepository.findAll().stream()
                .map(movie -> MovieResponse.builder()
                        .id(movie.getId())
                        .name(movie.getName())
                        .img(movie.getPosterImage())
                        .build())
                .collect(Collectors.toList());
    }

//    public CompletableFuture getAllMovieAsync() {
//
//    }

    @Override
    public List<Movie> getMovieCurrentlyShowing(Date date) {
        Date requestDate = Optional.ofNullable(date)
                .orElse(new Date());
        List<Movie> movies = movieRepository.getMoviesOnSchedule(requestDate);
        return movies;
    }

    @Override
    public List<Movie> getMovieCurrentlyShowingWithFilterSeat(Date date, String seat) {
        return null;
    }

    @Override
    public Boolean addNewMovie(Movie movie) {
        log.info("Trying to save new movie");
        return Optional.ofNullable(movie)
                .map(newMovie -> movieRepository.save(movie))
                .map(result -> {
                    boolean isSuccess = Objects.nonNull(result);
                    if(isSuccess) {
                        log.info("Saving movie successful with movie name : {} and generated Id of : {}", movie.getName(),
                                movie.getId());
                    }
                    return isSuccess;
                })
                .orElseGet(() -> {
                    log.error("Saving movie unsuccessful for movie name : {}", movie.getName());
                    return Boolean.FALSE;
                });
    }

    @Override
    public MovieResponse getMovieDetail(String selectedMovieName) {
        // TODO : ambil data movie, berdasarkan nama movie nya
        log.info("Getting movie detail info of {}", selectedMovieName);
        return Optional.ofNullable(movieRepository.findByName(selectedMovieName))
                .map(movie -> MovieResponse.builder()
                        .id(movie.getId())
                        .name(movie.getName())
                        .img(movie.getPosterImage())
                        .build())
                .orElse(null);
    }

    @Override
    public Page<Movie> getMoviePaged(int page) {
        return movieRepository.findAllWithPaging(PageRequest.of(page, 2));
    }

    @Override
    public List<Movie> getAllMovieOri() {
        return movieRepository.findMovieWithSchedule();
    }

    @Override
    public Boolean submitMovie(Movie movie) {
        movieRepository.save(movie);
        return true;
//        try {
//            movieRepository.submitNewMovie(movie.getId(), movie.getName(), movie.getPosterImage(), movie.getSynopsis());
//            return true;
//        } catch(Exception e) {
//            return false;
//        }
    }

    @Override
    public Boolean updateMovieName(String oldName, String newName) {
        try{
            movieRepository.editMovieName(oldName, newName);
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean deleteMovieFromName(String name) {
        try {
            movieRepository.deleteMovieFromName(name);
            return true;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
