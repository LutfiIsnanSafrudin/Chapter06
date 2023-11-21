package org.binaracademy.bioskopbackend.repository;

import lombok.Data;
import org.binaracademy.bioskopbackend.enumeration.MoviePhase;
import org.binaracademy.bioskopbackend.enumeration.MovieStatus;
import org.binaracademy.bioskopbackend.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {

    Movie findByName(String name); // select * from movie m where m.name = :name

    @Query(nativeQuery = true,
//            value = "select m, (select s from schedule s where s.start_time <= now() and s.end_time >= now()) from movie m;")
            value = "select * from movie " +
                    "join schedule on schedule.movie_id = movie.id " +
                    "where schedule.start_time <= :schedule and schedule.end_time >= :schedule")
    List<Movie> getMoviesOnSchedule(@Param("schedule") Date schedule);

    @Query(nativeQuery = true,
            value = "select * from movie " +
                    "join schedule on schedule.movie_id = movie.id " +
                    "where schedule.start_time <= :schedule and schedule.end_time >= :schedule")
    List<Movie> getMoviesOnSchedulePaged(@Param("schedule") Date schedule, Pageable pageable);

    @Query(nativeQuery = true, value = "select * from movie")
    Page<Movie> findAllWithPaging(Pageable pageable);

    @Query(nativeQuery = true, value = "insert into movie(id, name, poster_image, synopsis) " +
            "values(:id, :name, :posterImage, :synopsis)")
    void submitNewMovie(@Param("id") String id, @Param("name") String movieName, @Param("posterImage") String posterImage,
            @Param("synopsis") String synopsis);

    @Query(nativeQuery = true, value = "update movie set name = :newName where name = :oldName")
    void editMovieName(@Param("oldName") String oldName, @Param("newName") String newName);

    @Query(nativeQuery = true, value = "delete from movie where name = :name")
    void deleteMovieFromName(@Param("name") String name);

    @Query(nativeQuery = true, value = "select * from movie " +
            "left join schedule on schedule.movie_id = movie.id")
    List<Movie> findMovieWithSchedule();

//    Movie getMovieDetail()

//    @Query(value = "select m from Movie m where m.schedule < :schedule and m.seat like %:seat% ")
//    List<Movie> getMoviesOnScheduleAndSeat(@Param("schedule") Date schedule, @Param("seat") String seat);

}
