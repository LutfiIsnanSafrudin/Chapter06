package org.binaracademy.bioskopbackend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.binaracademy.bioskopbackend.enumeration.MoviePhase;
import org.binaracademy.bioskopbackend.enumeration.MovieStatus;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.sql.Blob;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity // Menandakan bahwa POJO class ini merepresent table pada database
@Table(name = "movie",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = {"movie_code", "name"}),
            @UniqueConstraint(columnNames = "poster_image")
        }) // OPTIONAL, bisa digunakan untuk memberikan nama table yg berbeda nama class
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id // Menandakan ini adalah column pk
    @GeneratedValue(generator = "UUID") // Column ini jika kosong akan di generate otomatis value nya
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator") // Generator value di column jika kosong
    private String id;

//    @Column(name = "name", length = 100) // Penanda column pada table sekaligus konfigurasi spec pada kolom tsb.
    @Column(name = "name", length = 100, unique = true)
    @Schema(example = "Test Swagger")
    private String name;

    @Column(name = "movie_code", length = 20)
    private String movieCode;

    @Column(name = "poster_image")
    private String posterImage;

    // Sebetulnya ga perlu pake @Column.
    private String synopsis;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "movie",
            fetch = FetchType.LAZY
    ) // menunjukkan bahwa kelas Movie memiliki hubungan One To Many dengan Schedules
    private List<Schedule> schedules;

//    @Transient // Java akan skip field ini dan tidak direpresentasikan sebagai kolom di table.
//    private String isAccessed;

//    @Column(name = "schedule")
//    private Date schedule;

//    private String seat;

    @Enumerated(EnumType.STRING)
    private MovieStatus movieStatus;

    @Enumerated(EnumType.ORDINAL)
    private MoviePhase moviePhase;

    private byte[] imageFile;


    // movie1, movie2, movie3
    // schedule11, schedule12, ...

    // a, b, c, d, e
    // String data = a, b, c ...
    // a...a5, b...b3, c...c5

}
