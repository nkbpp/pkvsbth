package ru.pfr.model.pkv;


import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Entity
@Table(name = "zayav")
public class Zayav {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_zayav")
    private Long id;

    @Column(name = "regnum")
    private String regnum;

    @Column(name = "name")
    private String name;

    @Column(name = "inn")
    private String inn;

    @Column(name = "kpp")
    private String kpp;

    @Column(name = "date_zaya")
    private String date_zaya;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_status")
    private Stat stat;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_users")
    private User user;

    @Column(name = "date_zagr")
    //@Temporal(TemporalType.DATE)
    private String dateZagr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dokumentzayav", nullable = true)
    private Dokumentzayav dokumentzayav;

    //не в таблице
    @OneToMany(mappedBy = "id_zayav", fetch = FetchType.EAGER)
    private List<KbkVid> kbkVids;

    //не в таблице
    @OneToOne(mappedBy = "id_zayav", fetch = FetchType.EAGER)
    private Opfr opfr;

    //не в таблице
    @OneToOne(mappedBy = "id_zayav", fetch = FetchType.EAGER)
    private Rech rech;

    public Zayav() {
    }

    public Zayav(String regnum, String name, String inn, String kpp, String date_zaya, Stat stat, User user, Dokumentzayav dokumentzayav) {
        this.regnum = regnum;
        this.name = name;
        this.inn = inn;
        this.kpp = kpp;
        this.date_zaya = date_zaya;
        this.stat = stat;
        this.user = user;
        this.dokumentzayav = dokumentzayav;

        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateZagr = formatForDateNow.format(dateNow);
    }

    public Rech getRech() {
        return rech;
    }

    public void setRech(Rech rech) {
        this.rech = rech;
    }

    public String getKpp() {
        return kpp;
    }

    public void setKpp(String kpp) {
        this.kpp = kpp;
    }

    public String getDate_zaya() {
        return date_zaya;
    }

    public String getDate_zayaFormat() {

        LocalDate date = LocalDate.parse(date_zaya, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy", new Locale("ru")));

/*        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy");
        return formatForDateNow.format(date_zaya);*/
    }

    public void setDate_zaya(String date_zaya) {
        this.date_zaya = date_zaya;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegnum() {
        return regnum;
    }

    public void setRegnum(String regnum) {
        this.regnum = regnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public Stat getStat() {
        return stat;
    }

    public void setStat(Stat stat) {
        this.stat = stat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDateZagr() {
        return dateZagr;
    }

    public void setDateZagr(String dateZagr) {
        this.dateZagr = dateZagr;
    }

    public List<KbkVid> getKbkVids() {
        return kbkVids;
    }

    public void setKbkVids(List<KbkVid> kbkVids) {
        this.kbkVids = kbkVids;
    }

    public Opfr getOpfr() {
        return opfr;
    }

    public void setOpfr(Opfr opfr) {
        this.opfr = opfr;
    }

    public Dokumentzayav getDokumentzayav() {
        return dokumentzayav;
    }

    public void setDokumentzayav(Dokumentzayav dokumentzayav) {
        this.dokumentzayav = dokumentzayav;
    }
}
