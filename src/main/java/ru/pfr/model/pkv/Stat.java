package ru.pfr.model.pkv;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stat")
public class Stat {

    @Id
    @Column(name = "id_stat")
    private Long id;

    @Column(name = "stat")
    private String stat;

    public Stat(Long id, String stat) {
        this.id = id;
        this.stat = stat;
    }

    public Stat() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}
