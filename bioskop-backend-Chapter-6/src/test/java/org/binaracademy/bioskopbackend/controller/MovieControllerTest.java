package org.binaracademy.bioskopbackend.controller;

import org.binaracademy.bioskopbackend.model.Movie;
import org.binaracademy.bioskopbackend.model.response.MovieResponse;
import org.binaracademy.bioskopbackend.model.response.Response;
import org.binaracademy.bioskopbackend.service.MovieService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@AutoConfigureMockMvc
@SpringBootTest
public class MovieControllerTest {

    @InjectMocks
    MovieController movieController;

    @Mock
    MovieService movieService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getAllMovie_test_success() {
        Mockito.when(movieService.getAllMovie()).thenReturn(Arrays.asList(MovieResponse.builder()
                .name("test").img("img test").id("test").build()));

        ResponseEntity<List<MovieResponse>> movies = movieController.getMovies();

        Mockito.verify(movieService).getAllMovie();
        Assertions.assertEquals(200, movies.getStatusCode());
        Assertions.assertEquals(1, movies.getBody().size());
    }

    @Test
    void getAllMovieApi() throws Exception {
        Mockito.when(movieService.getAllMovie()).thenReturn(Arrays.asList(MovieResponse.builder()
                .name("test").img("img test").id("test").build()));

        mockMvc.perform(MockMvcRequestBuilders.post("/get-all-movies").with(new RequestPostProcessor() {
                    @Override
                    public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                        return null;
                    }
                }))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", "application/json"));
    }

}
