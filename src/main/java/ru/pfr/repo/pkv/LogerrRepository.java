package ru.pfr.repo.pkv;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.pkv.Logerr;

import java.util.Optional;

public interface LogerrRepository extends JpaRepository<Logerr, Long> {
    //List<Logerr> findByKodNotAndKodNot(String p1, String p2);
    public Optional<Logerr> findByLoginuser(String login);
}