package ru.pfr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.model.pkv.KbkVid;
import ru.pfr.repo.pkv.KbkVidRepository;

import java.util.List;
import java.util.Optional;

@Service
public class KbkVidService {
    @Autowired
    KbkVidRepository kbkVidRepository;

    @Transactional
    public void save(KbkVid kbkVid) {
        kbkVidRepository.save(kbkVid);
    }

    public Optional<KbkVid> findById(Long id) {
        return kbkVidRepository.findById(id);
    }


    public List<KbkVid> findAll() {
        List<KbkVid> kbkVids = kbkVidRepository.findAll();
        return kbkVids;
    }
}
