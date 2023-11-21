package org.binaracademy.bioskopbackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.binaracademy.bioskopbackend.model.Movie;
import org.binaracademy.bioskopbackend.model.response.ErrorResponse;
import org.binaracademy.bioskopbackend.model.response.MovieResponse;
import org.binaracademy.bioskopbackend.model.response.Response;
import org.binaracademy.bioskopbackend.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/movies")
@Slf4j
public class MovieController {

    @Autowired
    MovieService movieService;

//    @RequestMapping(value = "/get-movies", method = RequestMethod.GET, produces = "application/json")
    @PostMapping(value = "/get-all-movies", produces = "application/json")
    @Operation(summary = "Api to get all movies")
    public ResponseEntity<List<MovieResponse>> getMovies() {
        log.info("getting all movies in controller");
        return ResponseEntity.ok().body(movieService.getAllMovie());
    }

//    @RequestMapping(method = RequestMethod.POST, value = "/add", consumes = "application/json")
    @PostMapping(value = "/add-movie")
    @Secured(value = "ROLE_ADMIN")
    public ResponseEntity<String> addNewMovies(@RequestParam("image") MultipartFile imageFile, @RequestBody Movie movie) throws IOException {
//        movieService.submitMovie(Movie.builder()
//                .imageFile(imageFile.getBytes()).build());
        movie.setImageFile(imageFile.getBytes());
        movieService.submitMovie(movie);
        return ResponseEntity.ok().body("Add new movies successful!");
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/delete/{movieName}")
    public ResponseEntity deleteMovie(@PathVariable("movieName") String movieName) {
        movieService.deleteMovieFromName(movieName);
        return ResponseEntity.ok("Delete movie " + movieName + " success!");
    }

//    @RequestMapping(method = RequestMethod.GET, value = "api/movies/detail")
    @GetMapping(value = "/detail")
    @Operation(summary = "Getting detail of one movies by movie name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie found"),
            @ApiResponse(responseCode = "404", description = "Inputted movie name not found")
    })
    public ResponseEntity getMovieDetail(@Parameter(description = "movie name to find")
    @Schema(example = "Spiderman 3")
    @RequestParam("movieName") String movieName) {
        MovieResponse response = movieService.getMovieDetail(movieName);
        log.debug("Movie detail with name {} fetched with detail {}", movieName, response);
        if(Objects.nonNull(response)) {
            return new ResponseEntity<>(Response.builder()
                    .data(movieService.getMovieDetail(movieName))
                    .isSuccess(Boolean.TRUE)
                    .build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(Response.builder()
                .error(ErrorResponse.builder()
                        .errorMessage("Movie with name " + movieName + " not found")
                        .errorCode(HttpStatus.NOT_FOUND.value())
                        .build())
                .data(null)
                .isSuccess(Boolean.FALSE)
                .build(), HttpStatus.NOT_FOUND);
//        return movieService.getMovieDetail(movieName);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/update/{movieName}")
    public ResponseEntity updateMovie(@RequestParam("newMovieName") String newMovieName,
            @PathVariable("movieName") String oldMovieName,
            @RequestHeader("Accept-Languange") String acceptLanguage,
            @RequestBody Movie movie) {
        log.info("Accept-Language - {}", acceptLanguage);
        movieService.updateMovieName(oldMovieName, newMovieName);
        return ResponseEntity.ok("update movie successful!");
    }

    @PostMapping(value = "/download-poster", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity downloadPoster() {
        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=oppenheimer.jpg")
                .body(movieService.getAllMovieOri().stream()
                .filter(val -> Objects.nonNull(val.getImageFile()))
                .map(Movie::getImageFile)
                .findFirst()
                .get());
    }

    @GetMapping(value = "/get-based-on-date")
    public ResponseEntity getBasedOnDate(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return ResponseEntity.ok()
                .body(null);
    }

    private List<?> testWildCard() {
        return Arrays.asList("String", 40);
    }
}