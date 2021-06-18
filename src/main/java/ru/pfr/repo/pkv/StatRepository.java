package ru.pfr.repo.pkv;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.pkv.Stat;

public interface StatRepository extends JpaRepository<Stat, Long> {
}
