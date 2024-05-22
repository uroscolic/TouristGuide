package rs.raf.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    /*
    Članak - Entitet koji sadrži naslov, tekst, vreme kreiranja, broj poseta, autora
    (korisnika koji je napisao članak), komentare čitalaca, kao i aktivnosti na osnovu
    kojih će se raditi pretraga članaka u kojima se piše o istom tipu aktivnosti.
    Svaki članak se bavi tačno jednom destinacijom (svaki članak pripada tačno jednoj destinaciji).
     */
    private Long id;
    private Long destinationId;
    private List<Long> activities = new ArrayList<>();


    @NotNull(message = "Title field is required")
    @NotEmpty(message = "Title field is required")
    private String title;

    @NotNull(message = "Text field is required")
    @NotEmpty(message = "Text field is required")
    private String text;

//    @NotNull(message = "Date field is required")
//    @NotEmpty(message = "Date field is required")
    private String date;

    private int numberOfVisits = 0;

    @NotNull(message = "Author field is required")
    @NotEmpty(message = "Author field is required")
    private String author;



}
