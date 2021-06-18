package ru.pfr.repo.asv;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.asv.AsvKbk;

import java.util.List;

public interface AsvKbkRepository extends JpaRepository<AsvKbk, Long> {
    List<AsvKbk> findAllByKbkCodeStartingWith(String kbkKode);
}
