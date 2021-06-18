package ru.pfr.model.pkv;



import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

@Entity
@Table(name = "statistika")
public class StatistikaViev {
    @Id
    @Column(name = "kod")
    private String kod;

    @Column(name = "namerayon")
    private String namerayon;

    @Column(name = "kolvo")
    private String kolvo;

    @Column(name = "summa")
    private String summa;

    @Column(name = "s1")
    private String s1;

    @Column(name = "s2")
    private String s2;

    @Column(name = "s3")
    private String s3;

    @Column(name = "s4")
    private String s4;

    public StatistikaViev() {
    }

    public String getKod() {
        return kod;
    }

    public String getNamerayon() {
        return namerayon;
    }

    public String getKolvo() {
        return kolvo;
    }

    public String getSumma() {
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(2);
        Double scientificDouble = Double.parseDouble(summa);
        return df.format(scientificDouble);
    }

    public String getS1() {
        return s1;
    }

    public String getS2() {
        return s2;
    }

    public String getS3() {
        return s3;
    }

    public String getS4() {
        return s4;
    }
}
