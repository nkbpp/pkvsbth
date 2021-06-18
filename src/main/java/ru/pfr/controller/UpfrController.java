package ru.pfr.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pfr.Application;
import ru.pfr.model.asv.AsvInsurer;
import ru.pfr.model.asv.AsvKbk;
import ru.pfr.model.pkv.*;
import ru.pfr.service.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Controller
@RequestMapping("/pkv/upfr")
public class UpfrController {

    @Autowired
    RayonService rayonService;
    @Autowired
    UserService userService;
    @Autowired
    AsvInsurerService asvInsurerService;
    @Autowired
    AsvKbkService asvKbkService;
    @Autowired
    ZayavService zayavService;
    @Autowired
    KbkVidService kbkVidService;
    @Autowired
    StatService statService;
    @Autowired
    RechService rechService;
    @Autowired
    DokumentzayavService dokumentzayavService;
    @Autowired
    DocumentuService documentuService;
    @Autowired
    LogiService logiService;
    private static final Logger logger = LogManager.getLogger(Application.class);

    @GetMapping()
    public String startupfr(
            @AuthenticationPrincipal User user,
            Model model) {
        logiService.save(new Logi(new Date(),user.getLogin(),"Главная страница UpfrController startupfr()"));
        logger.info("User = " + user.getLogin() + " UpfrController startupfr()");
        try {
/*        AsvInsurer asvInsurer = asvInsurerService.findById(21116l).get();
        model.addAttribute("asvInsurers", asvInsurer);*/
            Iterable<Zayav> zayavIterable = zayavService.findAllParameter(1, true, user, null, null, null, null);
            //Iterable<Zayav> zayavIterable = zayavService.findAll();
            model.addAttribute("zayavs", zayavIterable);

            Iterable<Dokumentzayav> dokumentzayavs = dokumentzayavService.findAllList(1);
            model.addAttribute("dokumentzayav", dokumentzayavs);

            Iterable<Stat> stats = statService.findAll();
            model.addAttribute("stats", stats);
            model.addAttribute("user", user);
        } catch (Exception e) {
            logiService.save(new Logi(new Date(),user.getLogin(),1l," Что то пошло не так UpfrController startupfr() Error: " + e));
            logger.error("User = " + user.getLogin() +
                    " Что то пошло не так UpfrController startupfr() Error: " + e);
        }
        return "upfr";
    }

    @GetMapping("/historys")
    public String historys(
            @RequestParam(value = "list", defaultValue = "1") int list,
            @RequestParam(value = "reg", defaultValue = "") String reg,
            @RequestParam(value = "inn", defaultValue = "") String inn,
            @RequestParam(value = "stat", defaultValue = "0") String stat,
            @RequestParam(value = "moi", defaultValue = "true") String moi,
            @AuthenticationPrincipal User user,
            Model model) {
        logiService.save(new Logi(new Date(),user.getLogin(),"таблица с данными UpfrController historys()"));
        logger.info("User = " + user.getLogin() + " UpfrController historys()");
        try {
            Iterable<Zayav> zayavIterable = zayavService.findAllParameter(
                    list,
                    moi.equals("true"),
                    user,
                    reg.equals("") ? null : reg,
                    inn.equals("") ? null : inn,
                    stat.equals("0") ? null : Long.parseLong(stat)
                    , null);
            model.addAttribute("zayavs", zayavIterable);
        } catch (Exception e) {
            logiService.save(new Logi(new Date(),user.getLogin(),1l," Что то пошло не так UpfrController historys() Error: " + e));
            logger.error("User = " + user.getLogin() +
                    " Что то пошло не так UpfrController historys() Error: " + e);
        }
        return "fragmentupfr/vvodzayav :: upfrhistory";
    }

    //автодополнение
    @GetMapping(value = "/searchregnom/{searchregnom}", produces = "application/json")
    public @ResponseBody
    AsvInsurer searchregnom(
            @PathVariable String searchregnom) {
        return asvInsurerService.findByInsurerRegNum(searchregnom);
    }

    @GetMapping("/addzayav")
    public String addzayav(
            //@RequestParam(value="list", defaultValue = "1") int list,
            @RequestParam(value = "reg", defaultValue = "") String reg,
            @RequestParam(value = "inn", defaultValue = "") String inn,
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "kpp", defaultValue = "") String kpp,
            @RequestParam(value = "date_zaya", defaultValue = "") String date_zaya,
            @RequestParam(value = "textkbk", defaultValue = "") String textkbk,
            @RequestParam(value = "doc") String doc,
            //@RequestParam(value="stat", defaultValue = "0") String stat,
            @RequestParam(value = "moi", defaultValue = "true") String moi,
            @AuthenticationPrincipal User user,
            Model model) {
        logiService.save(new Logi(new Date(),user.getLogin(),"Добавление заявления " +
                "UpfrController addzayav()  " +
                "reg=" + reg + " " +
                "inn=" + inn + " " +
                "name=" + name + " " +
                "kpp=" + kpp + " " +
                "date_zaya=" + date_zaya + " " +
                "textkbk=" + textkbk + " " +
                "moi=" + moi + " "
                ));
        logger.info("User = " + user.getLogin() + " UpfrController addzayav()");
        try {
            Stat stat = statService.findById(1l).get();
            logiService.save(new Logi(new Date(),user.getLogin(),"UpfrController addzayav() findById Успешно"));
            String s[] = date_zaya.split("\\.");
            String date = s[2] + "-" + s[1] + "-" + s[0];
            logiService.save(new Logi(new Date(),user.getLogin(),"UpfrController addzayav() date_zaya.split Успешно"));

            Dokumentzayav dokumentzayav = null;
            if (!(doc.equals("undefined") || doc.equals("") || (doc == null))) {
                dokumentzayav = dokumentzayavService.findById(Long.valueOf(doc)).get();
            }

            Zayav zayav = new Zayav(reg, name, inn, kpp, date, stat, user, dokumentzayav);

            logiService.save(new Logi(new Date(),user.getLogin(),"UpfrController addzayav() new Zayav Успешно"));

            zayavService.save(zayav);//

            logiService.save(new Logi(new Date(),user.getLogin(),"UpfrController addzayav() zayavService.save Успешно"));
            String[] splitTextkbk = textkbk.split(";");
            for (int i = 0; i < splitTextkbk.length; i = i + 2) {
                Float sum = Float.valueOf(splitTextkbk[i + 1].trim().replace(",", "."));//меняю запятые
                KbkVid kbkVid = new KbkVid(zayav, splitTextkbk[i], sum, null);
                kbkVidService.save(kbkVid);
            }
            logiService.save(new Logi(new Date(),user.getLogin(),"UpfrController addzayav() splitTextkbk Успешно"));
            Iterable<Zayav> zayavIterable = zayavService.findAllParameter(1,
                    moi.equals("true"),
                    user,
                    null,
                    null,
                    null, null);
            model.addAttribute("zayavs", zayavIterable);
            logiService.save(new Logi(new Date(),user.getLogin(),"UpfrController addzayav() Данные добавлены успешно"));
        } catch (Exception e) {
            logiService.save(new Logi(new Date(),user.getLogin(),1l," Что то пошло не так UpfrController addzayav() Error: " + e));
            logger.error("User = " + user.getLogin() +
                    " Что то пошло не так UpfrController addzayav() Error: " + e);
        }
        return "fragmentupfr/vvodzayav :: upfrhistory";
    }

    @GetMapping("/containerkbk")
    public String containerkbk(
            @RequestParam(value = "list", defaultValue = "1") int list,
            @RequestParam(value = "filter", defaultValue = "") String filter,
            Model model) {
        Iterable<AsvKbk> asvKbks = asvKbkService.findAllList(list, filter);
        model.addAttribute("asvKbks", asvKbks);

        return "fragmentupfr/vvodzayav :: containerkbk";
    }

    @GetMapping(value = "/delzayav")
    public String delzayav(
            @RequestParam(value = "id") Long id,
            @RequestParam(value = "list", defaultValue = "1") int list,
            @RequestParam(value = "reg", defaultValue = "") String reg,
            @RequestParam(value = "inn", defaultValue = "") String inn,
            @RequestParam(value = "stat", defaultValue = "0") String stat,
            @RequestParam(value = "moi", defaultValue = "true") String moi,
            @AuthenticationPrincipal User user,
            Model model) {
        logiService.save(new Logi(new Date(),user.getLogin(),"Удалить заявление с номером "+reg+" UpfrController delzayav()"));
        logger.info("User = " + user.getLogin() + " UpfrController delzayav()");
        try {
            zayavService.delete(id);
            Iterable<Zayav> zayavIterable = zayavService.findAllParameter(
                    list,
                    moi.equals("true"),
                    user,
                    reg.equals("") ? null : reg,
                    inn.equals("") ? null : inn,
                    stat.equals("0") ? null : Long.parseLong(stat),
                    null);
            model.addAttribute("zayavs", zayavIterable);
        } catch (Exception e) {
            logiService.save(new Logi(new Date(),user.getLogin(),1l," Что то пошло не так UpfrController delzayav() Error: " + e));
            logger.error("User = " + user.getLogin() +
                    " Что то пошло не так UpfrController delzayav() Error: " + e);
        }
        return "fragmentupfr/vvodzayav :: upfrhistory";
    }

    @GetMapping("/otopfr")
    public String otopfr(
            @RequestParam(value = "list", defaultValue = "1") int list,
            @RequestParam(value = "reg", defaultValue = "") String reg,
            @RequestParam(value = "inn", defaultValue = "") String inn,
            @RequestParam(value = "stat", defaultValue = "0") String stat,
            @RequestParam(value = "moi", defaultValue = "true") String moi,
            @AuthenticationPrincipal User user,
            Model model) {
        logiService.save(new Logi(new Date(),user.getLogin(),"Данные от опфр UpfrController otopfr()"));
        logger.info("User = " + user.getLogin() + " UpfrController otopfr()");
        try {
            Iterable<Zayav> zayavIterable = zayavService.findAllParameter(
                    list,
                    moi.equals("true"),
                    user,
                    reg.equals("") ? null : reg,
                    inn.equals("") ? null : inn,
                    3l, null);
            model.addAttribute("zayavs", zayavIterable);
        } catch (Exception e) {
            logiService.save(new Logi(new Date(),user.getLogin(),1l," Что то пошло не так UpfrController otopfr() Error: " + e));
            logger.error("User = " + user.getLogin() +
                    " Что то пошло не так UpfrController otopfr() Error: " + e);
        }
        return "fragmentupfr/otvet :: otvetbody";
    }

    @GetMapping("/rechupfr")
    public String otupfr(
            @RequestParam(value = "list", defaultValue = "1") int list,
            @RequestParam(value = "reg", defaultValue = "") String reg,
            @RequestParam(value = "inn", defaultValue = "") String inn,
            @RequestParam(value = "stat", defaultValue = "0") String stat,
            @RequestParam(value = "moi", defaultValue = "true") String moi,
            @AuthenticationPrincipal User user,
            Model model) {
        logiService.save(new Logi(new Date(),user.getLogin(),"решения UpfrController otupfr()"));
        logger.info("User = " + user.getLogin() + " UpfrController otupfr()");
        try {
            Iterable<Zayav> zayavIterable = zayavService.findAllParameter(
                    list,
                    moi.equals("true"),
                    user,
                    reg.equals("") ? null : reg,
                    inn.equals("") ? null : inn,
                    4l, null);
            model.addAttribute("zayavs", zayavIterable);
        } catch (Exception e) {
            logiService.save(new Logi(new Date(),user.getLogin(),1l," Что то пошло не так UpfrController otupfr() Error: " + e));
            logger.error("User = " + user.getLogin() +
                    " Что то пошло не так UpfrController otupfr() Error: " + e);
        }
        return "fragmentupfr/rechenie :: recheniebody";
    }

    @GetMapping(value = "/addrech")
    public String addrech(
            @RequestParam(value = "zid") Long id,
            @RequestParam(value = "nomrech", defaultValue = "") String nomrech,
            @RequestParam(value = "datarech", defaultValue = "") String datarech,
            @RequestParam(value = "list", defaultValue = "1") int list,
            @RequestParam(value = "reg", defaultValue = "") String reg,
            @RequestParam(value = "inn", defaultValue = "") String inn,
            @RequestParam(value = "stat", defaultValue = "0") String stat,
            @RequestParam(value = "moi", defaultValue = "true") String moi,
            @AuthenticationPrincipal User user,
            Model model) {
        logiService.save(new Logi(new Date(),user.getLogin(),"Дабавление решения UpfrController addrech()"));
        logger.info("User = " + user.getLogin() + " UpfrController addrech()");
        try {
            Zayav zayav = zayavService.findById(Long.valueOf(id));
            //увеличить статус
            zayav.setStat(statService.findById(zayav.getStat().getId() + 1l).get());
            zayavService.save(zayav);

            Rech rech = zayav.getRech() == null ? new Rech() : zayav.getRech();
            rech.setId_zayav(zayav);
            rech.setNomRech(nomrech.equals("") ? null : nomrech);
            rech.setDataRech(datarech.equals("") ? null : datarech);
            rechService.save(rech);

            Iterable<Zayav> zayavIterable = zayavService.findAllParameter(
                    list,
                    moi.equals("true"),
                    user,
                    reg.equals("") ? null : reg,
                    inn.equals("") ? null : inn,
                    3l, null);
            model.addAttribute("zayavs", zayavIterable);
        } catch (Exception e) {
            logiService.save(new Logi(new Date(),user.getLogin(),1l," Что то пошло не так UpfrController addrech() Error: " + e));
            logger.error("User = " + user.getLogin() +
                    " Что то пошло не так UpfrController addrech() Error: " + e);
        }
        return "fragmentupfr/otvet :: otvetbody";
    }

    @GetMapping(
            value = "/documentget",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public @ResponseBody
    byte[] documentget(
            @RequestParam(value = "id") Long id,
            @AuthenticationPrincipal User user,
            HttpServletResponse resp,
            Model model) throws IOException {
        InputStream in = null;
        logiService.save(new Logi(new Date(),user.getLogin(),"Получить документ UpfrController documentget()"));
        logger.info("User = " + user.getLogin() + " UpfrController documentget()");
        try {
            Dokumentu dokumentu = documentuService.findById(Long.valueOf(id)).get();

            in = new ByteArrayInputStream(dokumentu.getDokument());

            resp.setContentType("application/octet-stream");
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", dokumentu.getNameFile());
            resp.setHeader(headerKey, headerValue);
        } catch (Exception e) {
            logiService.save(new Logi(new Date(),user.getLogin(),1l," Что то пошло не так UpfrController documentget() Error: " + e));
            logger.error("User = " + user.getLogin() +
                    " Что то пошло не так UpfrController documentget() Error: " + e);
        }
        return IOUtils.toByteArray(in);
    }

    @GetMapping(
            value = "/documentgetz",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public @ResponseBody
    byte[] documentgetz(
            @RequestParam(value = "id") Long id,
            @AuthenticationPrincipal User user,
            HttpServletResponse resp,
            Model model) throws IOException {
        InputStream in = null;
        logiService.save(new Logi(new Date(),user.getLogin(),"Получить документ UpfrController documentgetz()"));
        logger.info("User = " + user.getLogin() + " UpfrController documentgetz()");
        try {
            Dokumentzayav dokumentzayav = dokumentzayavService.findById(Long.valueOf(id)).get();

            in = new ByteArrayInputStream(dokumentzayav.getDokument());

            resp.setContentType("application/octet-stream");
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", dokumentzayav.getNameFile());
            resp.setHeader(headerKey, headerValue);
        } catch (Exception e) {
            logiService.save(new Logi(new Date(),user.getLogin(),1l," Что то пошло не так UpfrController documentgetz() Error: " + e));
            logger.error("User = " + user.getLogin() +
                    " Что то пошло не так UpfrController documentgetz() Error: " + e);
        }
        return IOUtils.toByteArray(in);
    }

    @GetMapping("/otcatr")
    public String otcatr(
            @RequestParam(value = "zid") Long id,
            @RequestParam(value = "list", defaultValue = "1") int list,
            @RequestParam(value = "reg", defaultValue = "") String reg,
            @RequestParam(value = "inn", defaultValue = "") String inn,
            @RequestParam(value = "stat", defaultValue = "0") String stat,
            @RequestParam(value = "moi", defaultValue = "true") String moi,
            @AuthenticationPrincipal User user,
            Model model) {
        logiService.save(new Logi(new Date(),user.getLogin(),"Откатить данные UpfrController otcatr()"));
        logger.info("User = " + user.getLogin() + " UpfrController otcatr()");
        try {
            Zayav zayav = zayavService.findById(Long.valueOf(id));
            //уменьшить статус
            zayav.setStat(statService.findById(zayav.getStat().getId() - 1l).get());
            zayavService.save(zayav);
            rechService.delete(zayav.getRech());
            Iterable<Zayav> zayavIterable = zayavService.findAllParameter(
                    list,
                    moi.equals("true"),
                    user,
                    reg.equals("") ? null : reg,
                    inn.equals("") ? null : inn,
                    4l, null);
            model.addAttribute("zayavs", zayavIterable);
        } catch (Exception e) {
            logiService.save(new Logi(new Date(),user.getLogin(),1l," Что то пошло не так UpfrController otcatr() Error: " + e));
            logger.error("User = " + user.getLogin() +
                    " Что то пошло не так UpfrController otcatr() Error: " + e);
        }
        return "fragmentupfr/rechenie :: recheniebody";
    }


}
