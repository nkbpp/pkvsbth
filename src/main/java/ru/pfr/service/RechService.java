package ru.pfr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.model.pkv.Rech;
import ru.pfr.repo.pkv.RechRepository;

import java.util.List;

@Service
public class RechService {
    @Autowired
    RechRepository rechRepository;

    public List<Rech> findAll() {
        return rechRepository.findAll();
    }

    @Transactional
    public void save(Rech rech) {
        rechRepository.save(rech);
    }

    @Transactional
    public void delete(Rech rech) {
        rechRepository.delete(rech);
    }


}
