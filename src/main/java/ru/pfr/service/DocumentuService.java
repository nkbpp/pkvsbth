package ru.pfr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.model.pkv.Dokumentu;
import ru.pfr.repo.pkv.DokumentuRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentuService {
    @Autowired
    DokumentuRepository dokumentuRepository;

    @Transactional
    public void save(Dokumentu dokument) {
        dokumentuRepository.save(dokument);
    }

    public List<Dokumentu> findAll() {
        return dokumentuRepository.findAll();
    }

    public Optional<Dokumentu> findById(Long id) {
        return dokumentuRepository.findById(id);
    }

    public List<Dokumentu> findAllList(int i) {
        int start = 0;
        int end = 0;
        int kolvonastr = 10;
        start = kolvonastr * i - kolvonastr;
        end = kolvonastr * i;

        List<Dokumentu> dokumentList = dokumentuRepository.findAll();

        end = end < dokumentList.size() ? end : dokumentList.size();

        List<Dokumentu> dokumentList1;
        try {
            dokumentList1 = dokumentList.subList(start, end);
        } catch (Exception e) {
            dokumentList1 = new ArrayList<>();
        }
        return dokumentList1;
    }
}
