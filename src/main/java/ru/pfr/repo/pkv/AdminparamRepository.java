package ru.pfr.repo.pkv;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.model.pkv.Adminparam;


import java.util.Optional;

public interface AdminparamRepository extends JpaRepository<Adminparam, Long> {
    public Optional<Adminparam> findById(Long l);
    //public Adminparam findBy(String login);
}
