package ru.pfr.repo.pkv;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.pkv.Dokumentu;

public interface DokumentuRepository extends JpaRepository<Dokumentu, Long> {
}
