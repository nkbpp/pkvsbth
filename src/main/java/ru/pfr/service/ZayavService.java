package ru.pfr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.model.pkv.KbkVid;
import ru.pfr.model.pkv.User;
import ru.pfr.model.pkv.Zayav;
import ru.pfr.repo.asv.AsvKbkRepository;
import ru.pfr.repo.pkv.ZayavRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ZayavService {

    @Autowired
    ZayavRepository zayavRepository;
    @Autowired
    AsvKbkRepository asvKbkRepository;
    @Autowired
    KbkVidService kbkVidService;

    @Transactional
    public void save(Zayav zayav) {
        zayavRepository.save(zayav);
    }

    @Transactional
    public void delete(Long id) {
        zayavRepository.deleteById(id);
    }

    public Zayav findById(Long id) {
        Zayav zayav = zayavRepository.findById(id).get();
        zayav.getKbkVids().forEach(kbkVid -> {
            kbkVid.setAsvKbk(asvKbkRepository.findById(Long.valueOf(kbkVid.getId_kbk())).get());
        });
        return zayav;
    }

    public List<Zayav> findAll() {
        List<Zayav> list = zayavRepository.findAll();
        list.forEach(zayav ->
        {
            try {
                zayav.getKbkVids().forEach(kbkVid -> {
                    try {
                        kbkVid.setAsvKbk(asvKbkRepository.findById(Long.valueOf(kbkVid.getId_kbk())).get());
                    } catch (Exception e) {
                        Zayav z = zayav;
                        KbkVid k = kbkVid;
                    }
                });
            } catch (Exception e) {
                List<KbkVid> kbkVids = kbkVidService.findAll();
                List<KbkVid> kbkVids1 = new ArrayList<>();
                kbkVids.forEach(kbkVid -> {
                    if (kbkVid.getId_zayav().getId() == zayav.getId()) {
                        kbkVids1.add(kbkVid);
                    }
                });
                kbkVids1.forEach(kbkVid ->
                        kbkVid.setAsvKbk(asvKbkRepository.findById(Long.valueOf(kbkVid.getId_kbk())).get())
                );
                zayav.setKbkVids(kbkVids1);
            }
        });
        return list;
    }

    private List<Zayav> filtrRegnum(List<Zayav> l, String r) {
        List<Zayav> l2 = new ArrayList<>();
        l.forEach(zayav -> {

            if (zayav.getRegnum().trim().equals(r.trim()))
                l2.add(zayav);
            else
                zayav.getRegnum();
        });
        return l2;
    }

    private List<Zayav> filtrInn(List<Zayav> l, String n) {
        List<Zayav> l2 = new ArrayList<>();
        l.forEach(zayav -> {
            if (zayav.getInn().trim().equals(n.trim()))
                l2.add(zayav);
        });
        return l2;
    }

    private List<Zayav> filtrStat(List<Zayav> l, Long s) {
        List<Zayav> l2 = new ArrayList<>();
        l.forEach(zayav -> {
            if (zayav.getStat().getId() == s)
                l2.add(zayav);
        });
        return l2;
    }

    public List<Zayav> findAllParameter(int i, boolean moi, User user, String regnum, String n, Long s, String kodRayon) {
        List<Zayav> list = findAll();
        List<Zayav> list1 = new ArrayList<>();

        if (user.getRayon().getKod().equals("000")) { //пользователь опфр
            list.forEach(zayav -> {

                if (moi) {
                    if (zayav.getUser().getId().equals(user.getId())) {
                        list1.add(zayav);
                    }
                } else {
                    list1.add(zayav);
                }

            });
/*            if(kodRayon!=null){
                list.forEach(zayav -> {
                    if(zayav.getUser().getRayon().getKod().equals(kodRayon)){
                        list1.add(zayav);
                    }
                });
            } else
                list1.addAll(list);*/
        } else { //пользователь упфр
            list.forEach(zayav -> {
                if (zayav.getUser().getRayon().getKod().equals(user.getRayon().getKod())) {
                    if (moi) {
                        if (zayav.getUser().getId().equals(user.getId())) {
                            list1.add(zayav);
                        }
                    } else {
                        list1.add(zayav);
                    }
                }
            });
        }

        List<Zayav> list3 = new ArrayList<>();
        list3.addAll(list1);
        if (regnum != null)
            list3 = filtrRegnum(list3, regnum);
        if (n != null)
            list3 = filtrInn(list3, n);
        if (s != null)
            list3 = filtrStat(list3, s);

        int start = 0;
        int end = 0;
        int kolvonastr = 10;
        start = kolvonastr * i - kolvonastr;
        end = kolvonastr * i;

        end = end < list3.size() ? end : list3.size();

        List<Zayav> list2;
        try {
            list2 = list3.subList(start, end);
        } catch (Exception e) {
            list2 = new ArrayList<>();
        }

        return list2;
    }

}
