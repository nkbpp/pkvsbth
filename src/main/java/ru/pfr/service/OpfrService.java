package ru.pfr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.model.pkv.Opfr;
import ru.pfr.repo.pkv.OpfrRepository;

import java.util.List;

@Service
public class OpfrService {
    @Autowired
    OpfrRepository opfrRepository;

    public List<Opfr> findAll() {
        return opfrRepository.findAll();
    }

    @Transactional
    public void save(Opfr opfr) {
        opfrRepository.save(opfr);
    }

}
