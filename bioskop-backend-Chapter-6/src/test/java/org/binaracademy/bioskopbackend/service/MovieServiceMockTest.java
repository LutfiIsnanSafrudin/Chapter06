package org.binaracademy.bioskopbackend.service;

import org.binaracademy.bioskopbackend.model.Movie;
import org.binaracademy.bioskopbackend.model.response.MovieResponse;
import org.binaracademy.bioskopbackend.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@AutoConfigureMockMvc
@SpringBootTest
public class MovieServiceMockTest {

    @InjectMocks
    MovieServiceImpl movieService;

    @Mock
    MovieRepository movieRepository;

    /**
     * Inject mock bisa menggunakan cara seperti ini, atau bisa juga dengan @InjectMocks tapi harus class impl yang digunakan
      */
//    @BeforeEach
//    void initTest() {
//        movieService = new MovieServiceImpl(movieRepository);
//    }

    @Test
    void getMovieDetailTest() {
        Mockito.when(movieRepository.findByName(Mockito.anyString())).thenReturn(Movie.builder()
                        .name("testing").posterImage("Test Image")
                .build());

        MovieResponse movieResponse = movieService.getMovieDetail("testing");

        Mockito.verify(movieRepository, Mockito.times(1)).findByName(Mockito.anyString());

        Assertions.assertNotNull(movieResponse);
        Assertions.assertEquals("Test Image", movieResponse.getImg());
    }
}
