package ru.pfr.model.asv;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ASV_KBK")
public class AsvKbk {
    @Id
    @Column(name = "KBK_ID")
    private Long kbkId;

    @Column(name = "KBK_CODE")
    private String kbkCode;

    @Column(name = "KBK_FULL_NAME")
    private String kbkFullName;

    @Column(name = "KBK_SHORT_NAME")
    private String kbkShortName;

    public String getKbkFullName(int i) {
        return kbkFullName.length() > i ? kbkFullName.trim().substring(0, i) + "..." : kbkFullName;
    }

    public String getKbkShortName(int i) {
        return kbkShortName.length() > i ? kbkShortName.trim().substring(0, i) + "..." : kbkShortName;
    }

    public Long getKbkId() {
        return kbkId;
    }

    public void setKbkId(Long kbkId) {
        this.kbkId = kbkId;
    }

    public String getKbkCode() {
        return kbkCode;
    }

    public void setKbkCode(String kbkCode) {
        this.kbkCode = kbkCode;
    }

    public String getKbkFullName() {
        return kbkFullName;
    }

    public void setKbkFullName(String kbkFullName) {
        this.kbkFullName = kbkFullName;
    }

    public String getKbkShortName() {
        return kbkShortName;
    }

    public void setKbkShortName(String kbkShortName) {
        this.kbkShortName = kbkShortName;
    }
}
