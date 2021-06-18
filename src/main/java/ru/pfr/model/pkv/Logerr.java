package ru.pfr.model.pkv;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "logerr")
public class Logerr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "loginuser")
    private String loginuser;

    @Column(name = "active")
    private Long active;

    @Column(name = "date")
    private Date date;

    public Logerr() {
    }

    public Logerr(String loginuser) {
        this.loginuser = loginuser;
        this.active = 0l;
        this.date = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginuser() {
        return loginuser;
    }

    public void setLoginuser(String loginuser) {
        this.loginuser = loginuser;
    }

    public Long getActive() {
        return active;
    }

    public void setActive(Long active) {
        this.active = active;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
