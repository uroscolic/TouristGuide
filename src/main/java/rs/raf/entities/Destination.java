package rs.raf.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Destination {

    private Long id;
    @NotNull(message = "Name field is required")
    @NotEmpty(message = "Name field is required")
    @UniqueElements(message = "Name already exists")
    private String name;

    @NotNull(message = "Description field is required")
    @NotEmpty(message = "Description field is required")
    private String description;

}
