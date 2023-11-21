package org.binaracademy.bioskopbackend.service;

import org.aspectj.lang.annotation.After;
import org.binaracademy.bioskopbackend.enumeration.MoviePhase;
import org.binaracademy.bioskopbackend.enumeration.MovieStatus;
import org.binaracademy.bioskopbackend.model.Movie;
import org.binaracademy.bioskopbackend.model.Schedule;
import org.binaracademy.bioskopbackend.model.response.MovieResponse;
import org.binaracademy.bioskopbackend.repository.MovieRepository;
import org.binaracademy.bioskopbackend.service.MovieService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieServiceTest {

    @Autowired
    MovieServiceImpl movieService;

    @SpyBean
    MovieRepository movieRepository;

    @BeforeEach
    void prepare() {
//        movieRepository.deleteAll();
    }
    @AfterEach
    void tearDown() {
        movieRepository.deleteAll();
    }

    @Test
    void testSaveMovie() {
        movieService.addNewMovie(Movie.builder()
                        .synopsis("Synopsis")
                        .name("Name ajah")
                        .schedules(null)
                        .movieCode("Name code")
                        .moviePhase(MoviePhase.REVIEW)
                        .movieStatus(MovieStatus.UPCOMING)
                .build());
    }



    @Test
    void testMovieCurrentlyShowing_success() {
        Movie movieOnSchedule = Movie.builder()
                .name("test 1")
                .movieCode("test1")
                .schedules(Arrays.asList(Schedule.builder()
                                .startTime(new Date(new Date().getTime() - 10000L))
                                .endTime(new Date(new Date().getTime() + 10000L))
                        .build()))
                .posterImage("poster 1")
                .synopsis("synopsis")
                .build();
        Movie movieOffSchedule = Movie.builder()
                .name("test 2")
                .movieCode("test2")
                .schedules(Arrays.asList(Schedule.builder()
                        .startTime(new Date(new Date().getTime() + 10000L))
                        .endTime(new Date(new Date().getTime() + 90000L))
                        .build()))
                .build();
        movieRepository.save(movieOnSchedule);
        movieRepository.save(movieOffSchedule);
//        movieRepository.saveAll(Arrays.asList(movieOnSchedule, movieOffSchedule));

        List<Movie> movieActual = movieService.getMovieCurrentlyShowing(null);
        Assertions.assertEquals(1, movieActual.size());
        Assertions.assertEquals("test1", movieActual.get(0).getMovieCode());
    }

    @Test
    void testGetAllMovie_success() {
        Movie movie = Movie.builder()
                .name("test 1")
                .movieCode("test1")
                .schedules(Arrays.asList(
                        Schedule.builder()
                                .startTime(new Date())
                                .endTime(new Date())
                                .build()
                ))
                .build();
        movieRepository.save(movie);

        List<MovieResponse> movieActual = movieService.getAllMovie();
        Assertions.assertEquals(1, movieActual.size());
    }

    @Test
    void testSubmitMovie_success() {
        movieService.submitMovie(Movie.builder()
                        .id("testId")
                .synopsis("Synopsis")
                .name("Name ajah")
                .schedules(null)
                .movieCode("Name code")
                .moviePhase(MoviePhase.REVIEW)
                .movieStatus(MovieStatus.UPCOMING)
                        .posterImage("poster")
                .build());
    }

    @Test
    @Transactional
    void testGetAllMoviePaged_success() {
        Movie movie = Movie.builder()
                .id("movie")
                .name("test 1")
                .movieCode("test1")
                .schedules(Arrays.asList(
                        Schedule.builder()
                                .scheduleId("TEST")
                                .startTime(new Date())
                                .endTime(new Date())
                                .build()
                ))
                .build();
        movieRepository.save(movie);

        List<Movie> movieActual = movieService.getAllMovieOri();

        Assertions.assertEquals(1, movieActual.size());
        Assertions.assertEquals(1, movieActual.get(0).getSchedules().size());
        Assertions.assertNotNull(movieActual);
    }

    @Test
    void testGetAllMovie_joinWithSchedule_success() {
        Movie movie = Movie.builder()
                .name("test 1")
                .movieCode("test1")
                .schedules(Arrays.asList(
                        Schedule.builder()
                                .startTime(new Date())
                                .endTime(new Date())
                                .build()
                ))
                .build();
        movieRepository.save(movie);

        List<Movie> movieActual = movieService.getAllMovieOri();

        Assertions.assertEquals(1, movieActual.size());
        Assertions.assertEquals(1, movieActual.get(0).getSchedules().size());
        Assertions.assertNotNull(movieActual);
    }

    @Test
    void testGetAllMovie_spy_success() {
        Movie movie = Movie.builder()
                .name("test 1")
                .movieCode("test1")
                .schedules(Arrays.asList(
                        Schedule.builder()
                                .startTime(new Date())
                                .endTime(new Date())
                                .build()
                ))
                .build();
        movieRepository.save(movie);

        Mockito.when(movieRepository.findAll()).thenReturn(Arrays.asList(Movie.builder()
                .name("Spy Test")
                .movieCode("spy_test")
                .schedules(Arrays.asList(
                        Schedule.builder()
                                .startTime(new Date())
                                .endTime(new Date())
                                .build()
                ))
                .posterImage("spy test poster euy")
                .build()));
        List<MovieResponse> movieActual = movieService.getAllMovie();
        Mockito.verify(movieRepository, Mockito.times(1)).findAll();
        Assertions.assertEquals(1, movieActual.size());
    }
}
