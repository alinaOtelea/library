package jpa;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import javax.persistence.*;

@Getter @Setter @Builder
@Entity
public class Cititor {
    @Tolerate
    public Cititor(){}

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int idCititor;

    @Column
    private String cnp;

    @Column
    private String nume;

    @Column
    private String prenume;

    @Column
    private String adresa;

    @Column
    private String telefon;

    @Column
    private String email;

    @Column
    private String data_inregistrare;

    @Column
    private String data_nasterii;

}
