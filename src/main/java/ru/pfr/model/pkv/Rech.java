package ru.pfr.model.pkv;

import javax.persistence.*;

@Entity
@Table(name = "rech")
public class Rech {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rech", nullable = false)
    private Long idRech;

    @OneToOne
    @JoinColumn(name = "id_zayav")
    private Zayav id_zayav;

    @Column(name = "nom_rech", nullable = true, length = 25)
    private String nomRech;

    @Column(name = "data_rech", nullable = true)
    private String dataRech;

    public Rech() {
    }

    public Rech(Zayav id_zayav, String nomRech, String dataRech) {
        this.id_zayav = id_zayav;
        this.nomRech = nomRech;
        this.dataRech = dataRech;
    }

    public Long getIdRech() {
        return idRech;
    }

    public void setIdRech(Long idRech) {
        this.idRech = idRech;
    }

    public Zayav getId_zayav() {
        return id_zayav;
    }

    public void setId_zayav(Zayav id_zayav) {
        this.id_zayav = id_zayav;
    }

    public String getNomRech() {
        return nomRech;
    }

    public void setNomRech(String nomRech) {
        this.nomRech = nomRech;
    }

    public String getDataRech() {
        return dataRech;
    }

    public void setDataRech(String dataRech) {
        this.dataRech = dataRech;
    }
}
