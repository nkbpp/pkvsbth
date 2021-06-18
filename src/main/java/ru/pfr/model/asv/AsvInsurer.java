package ru.pfr.model.asv;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ASV_INSURER")
public class AsvInsurer {
    @Id
    @Column(name = "INSURER_ID")
    private Long Id;

    @Column(name = "INSURER_REG_NUM")
    private String insurerRegNum;

    @Column(name = "INSURER_INN")
    private String insurerInn;

    @Column(name = "INSURER_KPP")
    private String insurerKpp;

    @Column(name = "INSURER_FULL_NAME")
    private String insurerFullName;

    @Column(name = "INSURER_SHORT_NAME")
    private String insurerShortName;

    @Column(name = "INSURER_COMP_NAME")
    private String insurerCompName;

    public String getInsurerShortName() {
        return insurerShortName;
    }

    public void setInsurerShortName(String insurerShortName) {
        this.insurerShortName = insurerShortName;
    }

    public String getInsurerCompName() {
        return insurerCompName;
    }

    public void setInsurerCompName(String insurerCompName) {
        this.insurerCompName = insurerCompName;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getInsurerRegNum() {
        return insurerRegNum;
    }

    public void setInsurerRegNum(String insurerRegNum) {
        this.insurerRegNum = insurerRegNum;
    }

    public String getInsurerInn() {
        return insurerInn;
    }

    public void setInsurerInn(String insurerInn) {
        this.insurerInn = insurerInn;
    }

    public String getInsurerKpp() {
        return insurerKpp;
    }

    public void setInsurerKpp(String insurerKpp) {
        this.insurerKpp = insurerKpp;
    }

    public String getInsurerFullName() {
        return insurerFullName;
    }

    public void setInsurerFullName(String insurerFullName) {
        this.insurerFullName = insurerFullName;
    }
}
