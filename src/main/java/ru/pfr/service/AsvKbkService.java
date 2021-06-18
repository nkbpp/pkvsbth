package ru.pfr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pfr.model.asv.AsvKbk;
import ru.pfr.repo.asv.AsvKbkRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AsvKbkService {

    @Autowired
    private AsvKbkRepository asvKbkRepository;

    public List<AsvKbk> findAll() {
        return asvKbkRepository.findAll();
    }

    public List<AsvKbk> findAllList(int i, String filter) {
        int start = 0;
        int end = 0;
        int kolvonastr = 10;
        start = kolvonastr * i - kolvonastr;
        end = kolvonastr * i;

        List<AsvKbk> asvKbks = filter.trim().equals("") ? asvKbkRepository.findAll() :
                asvKbkRepository.findAllByKbkCodeStartingWith(filter);

        end = end < asvKbks.size() ? end : asvKbks.size();

        List<AsvKbk> asvKbks1;
        try {
            asvKbks1 = asvKbks.subList(start, end);
        } catch (Exception e) {
            asvKbks1 = new ArrayList<>();
        }
        return asvKbks1;
    }

}
