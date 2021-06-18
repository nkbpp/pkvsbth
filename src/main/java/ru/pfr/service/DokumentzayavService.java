package ru.pfr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.model.pkv.Dokumentzayav;
import ru.pfr.repo.pkv.DokumentzayavRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DokumentzayavService {
    @Autowired
    DokumentzayavRepository dokumentzayavRepository;

    @Transactional
    public void save(Dokumentzayav dokumentzayav) {
        dokumentzayavRepository.save(dokumentzayav);
    }

    public List<Dokumentzayav> findAll() {
        return dokumentzayavRepository.findAll();
    }

    public Optional<Dokumentzayav> findById(Long id) {
        return dokumentzayavRepository.findById(id);
    }

    public List<Dokumentzayav> findAllList(int i) {
        int start = 0;
        int end = 0;
        int kolvonastr = 10;
        start = kolvonastr * i - kolvonastr;
        end = kolvonastr * i;

        List<Dokumentzayav> dokumentzayavList = dokumentzayavRepository.findAll();

        end = end < dokumentzayavList.size() ? end : dokumentzayavList.size();

        List<Dokumentzayav> dokumentzayavList1;
        try {
            dokumentzayavList1 = dokumentzayavList.subList(start, end);
        } catch (Exception e) {
            dokumentzayavList1 = new ArrayList<>();
        }
        return dokumentzayavList1;
    }

}
