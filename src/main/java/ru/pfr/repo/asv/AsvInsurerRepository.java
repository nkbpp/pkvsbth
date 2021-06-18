package ru.pfr.repo.asv;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.asv.AsvInsurer;

public interface AsvInsurerRepository extends JpaRepository<AsvInsurer, Long> {
    AsvInsurer findByInsurerRegNum(String insurerRegNum);
}
