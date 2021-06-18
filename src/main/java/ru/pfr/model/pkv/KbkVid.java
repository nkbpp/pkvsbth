package ru.pfr.model.pkv;

import ru.pfr.model.asv.AsvKbk;

import javax.persistence.*;


@Entity
@Table(name = "kbkvid")
public class KbkVid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_kbkvid")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_zayav")
    private Zayav id_zayav;

    @Transient
    private AsvKbk asvKbk;

    @Column(name = "id_kbk")
    private String id_kbk;

    @Column(name = "summ1")
    private Float summ1;

    @Column(name = "summ2")
    private Float summ2;

    public KbkVid() {
    }

    public KbkVid(Zayav id_zayav, String id_kbk, Float summ1, Float summ2) {
        this.id_zayav = id_zayav;
        this.id_kbk = id_kbk;
        this.summ1 = summ1;
        this.summ2 = summ2;
    }

    public AsvKbk getAsvKbk() {
        return asvKbk;
    }

    public void setAsvKbk(AsvKbk asvKbk) {
        this.asvKbk = asvKbk;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Zayav getId_zayav() {
        return id_zayav;
    }

    public void setId_zayav(Zayav id_zayav) {
        this.id_zayav = id_zayav;
    }

    public String getId_kbk() {
        return id_kbk;
    }

    public void setId_kbk(String id_kbk) {
        this.id_kbk = id_kbk;
    }

    public Float getSumm1() {
        return summ1;
    }

    public void setSumm1(Float summ1) {
        this.summ1 = summ1;
    }

    public Float getSumm2() {
        return summ2;
    }

    public void setSumm2(Float summ2) {
        this.summ2 = summ2;
    }
}
