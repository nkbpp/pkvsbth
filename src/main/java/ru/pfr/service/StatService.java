package ru.pfr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pfr.model.pkv.Stat;
import ru.pfr.repo.pkv.StatRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StatService {
    @Autowired
    StatRepository statRepository;

    public List<Stat> findAll() {
        return statRepository.findAll();
    }

    public List<Stat> findAllOpfr() {
        List<Stat> list = statRepository.findAll();
        List<Stat> list1 = new ArrayList<>();
        list.forEach(stat -> {
            if (stat.getId() == 2 || stat.getId() == 3) {
                list1.add(stat);
            }
        });
        return list1;
    }

    public Optional<Stat> findById(Long id) {
        return statRepository.findById(id);
    }
}
