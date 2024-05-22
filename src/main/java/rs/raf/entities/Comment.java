package rs.raf.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private Long id;
    private Long articleId;

    @NotNull(message = "Author field is required")
    @NotEmpty(message = "Author field is required")
    private String author;

    @NotNull(message = "Text field is required")
    @NotEmpty(message = "Text field is required")
    private String text;

    @NotNull(message = "Date field is required")
    @NotEmpty(message = "Date field is required")
    private String date;


}
