package jpa;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import javax.persistence.*;

@Getter @Setter @Builder
@Entity
public class Carte {
    @Tolerate
    public Carte(){}

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int idCarte;

    @Column
    private String autor;

    @Column
    private String titlu;

    @Column
    private String domeniu;

    @Column
    private String editura;

    @Column
    private int stoc;

    @Column
    private int data_publicare;

    @Column
    private String isbn;

    @Column
    private int disponibile;

}
