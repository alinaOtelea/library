package jpa;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import javax.persistence.*;

@Getter @Setter @Builder
@Entity
public class Imprumut {
    @Tolerate
    public Imprumut(){}

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int idImprumut;

    @Column
    private int idCititor;

    @Column
    private int idCarte;

    @Column
    private String data_imprumut;

    @Column
    private String data_retur;

    @Column
    private int returnata;
}
