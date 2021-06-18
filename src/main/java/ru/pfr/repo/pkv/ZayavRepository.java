package ru.pfr.repo.pkv;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.pkv.Zayav;

public interface ZayavRepository extends JpaRepository<Zayav, Long> {
}
