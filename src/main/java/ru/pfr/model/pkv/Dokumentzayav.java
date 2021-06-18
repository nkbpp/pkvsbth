package ru.pfr.model.pkv;

import javax.persistence.*;

@Entity
@Table(name = "dokumentzayav")
public class Dokumentzayav {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dokumentzayav", nullable = false)
    private Long idDokumentzayav;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "dokument", columnDefinition = "LONGBLOB", nullable = true)
    private byte[] dokument;
    @Column(name = "name_dokument", nullable = true, length = 400)
    private String nameDokument;
    @Column(name = "name_file", nullable = true, length = 400)
    private String nameFile;

    public Dokumentzayav() {
    }

    public Dokumentzayav(byte[] dokument, String nameDokument, String nameFile) {
        this.dokument = dokument;
        this.nameDokument = nameDokument;
        this.nameFile = nameFile;
    }

    public Long getIdDokumentzayav() {
        return idDokumentzayav;
    }

    public void setIdDokumentzayav(Long idDokumentzayav) {
        this.idDokumentzayav = idDokumentzayav;
    }

    public byte[] getDokument() {
        return dokument;
    }

    public void setDokument(byte[] dokument) {
        this.dokument = dokument;
    }

    public String getNameDokument() {
        return nameDokument;
    }

    public void setNameDokument(String nameDokument) {
        this.nameDokument = nameDokument;
    }

    public String getNameFile() {
        return nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }
}
