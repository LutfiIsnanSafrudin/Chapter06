package org.binaracademy.bioskopbackend.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.binaracademy.bioskopbackend.model.Schedule;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponse {

    private String id;
    private String name;
    private String img;

}
