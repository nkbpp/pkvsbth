package ru.pfr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.model.pkv.Logerr;
import ru.pfr.repo.pkv.LogerrRepository;

import java.util.List;
import java.util.Optional;


@Service
public class LogerrService {
    @Autowired
    LogerrRepository logerrRepository;

    @Transactional
    public void save(Logerr logerr) {
        logerrRepository.save(logerr);
    }

    public List<Logerr> findAll() {
        return logerrRepository.findAll();
    }

    public Logerr findByLoginuser(String login) {
        return logerrRepository.findByLoginuser(login).get();
    }

    public Logerr findById(Long id) {
        return logerrRepository.findById(id).get();
    }
}
