package ru.pfr.model.pkv;

import javax.persistence.*;

@Entity
@Table(name = "opfr")
public class Opfr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_opfr", nullable = false)
    private Long idOpfr;

    @Column(name = "nom_zap", nullable = false, length = 50)
    private String nomZap;

    @Column(name = "data_otpr", nullable = false)
    private String dataOtpr;

    @Column(name = "nom_otv", nullable = true, length = 50)
    private String nomOtv;

    @Column(name = "data_otv", nullable = true)
    private String dataOtv;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dokument", nullable = true)
    private Dokumentu dokumentu;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_zayav")
    private Zayav id_zayav;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;

/*    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dokumentzayav", nullable = true)
    private Dokumentzayav dokumentzayav;*/

    public Opfr() {
    }

    public Opfr(String nomZap, String dataOtpr, String nomOtv, String dataOtv, Dokumentu dokumentu, Zayav id_zayav, User user/*, Dokumentzayav dokumentzayav*/) {
        this.nomZap = nomZap;
        this.dataOtpr = dataOtpr;
        this.nomOtv = nomOtv;
        this.dataOtv = dataOtv;
        this.dokumentu = dokumentu;
        this.id_zayav = id_zayav;
        this.user = user;
        /*this.dokumentzayav = dokumentzayav;*/
    }

    public Long getIdOpfr() {
        return idOpfr;
    }

    public void setIdOpfr(Long idOpfr) {
        this.idOpfr = idOpfr;
    }

    public String getNomZap() {
        return nomZap;
    }

    public void setNomZap(String nomZap) {
        this.nomZap = nomZap;
    }

    public String getDataOtpr() {
        return dataOtpr;
    }

    public void setDataOtpr(String dataOtpr) {
        this.dataOtpr = dataOtpr;
    }

    public String getNomOtv() {
        return nomOtv;
    }

    public void setNomOtv(String nomOtv) {
        this.nomOtv = nomOtv;
    }

    public String getDataOtv() {
        return dataOtv;
    }

    public void setDataOtv(String dataOtv) {
        this.dataOtv = dataOtv;
    }

    public Dokumentu getDokumentu() {
        return dokumentu;
    }

    public void setDokumentu(Dokumentu dokumentu) {
        this.dokumentu = dokumentu;
    }

    public Zayav getId_zayav() {
        return id_zayav;
    }

    public void setId_zayav(Zayav id_zayav) {
        this.id_zayav = id_zayav;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

/*    public Dokumentzayav getDokumentzayav() {
        return dokumentzayav;
    }

    public void setDokumentzayav(Dokumentzayav dokumentzayav) {
        this.dokumentzayav = dokumentzayav;
    }*/
}
