package ru.pfr.repo.pkv;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.pkv.StatistikaViev;

public interface StatistikaRepository extends JpaRepository<StatistikaViev, Long> {
}
