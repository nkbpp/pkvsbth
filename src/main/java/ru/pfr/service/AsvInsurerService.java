package ru.pfr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pfr.model.asv.AsvInsurer;
import ru.pfr.repo.asv.AsvInsurerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AsvInsurerService {

    @Autowired
    private AsvInsurerRepository asvInsurerRepository;

    public Optional<AsvInsurer> findById(Long id) {
        return asvInsurerRepository.findById(id);
    }

    public List<AsvInsurer> findAll() {
        return asvInsurerRepository.findAll();
    }

    public AsvInsurer findByInsurerRegNum(String insurerRegNum) {
        return asvInsurerRepository.findByInsurerRegNum(insurerRegNum);
    }
}
