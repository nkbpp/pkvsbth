package ru.pfr.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pfr.Application;
import ru.pfr.model.pkv.*;
import ru.pfr.repo.pkv.StatistikaRepository;
import ru.pfr.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.pfr.classes.Translit.cyr2lat;

@Controller
@RequestMapping("/pkv/opfr")
@PreAuthorize("hasRole('ROLE_OPFR')")
public class OpfrController {

    @Autowired
    RayonService rayonService;
    @Autowired
    ZayavService zayavService;
    @Autowired
    StatService statService;
    @Autowired
    DokumentzayavService dokumentzayavService;
    @Autowired
    DocumentuService documentuService;
    @Autowired
    OpfrService opfrService;
    @Autowired
    KbkVidService kbkVidService;
    @Autowired
    LogiService logiService;
    @Autowired
    StatistikaRepository statistikaRepository;

    private static final Logger logger = LogManager.getLogger(Application.class);

    @GetMapping()
    public String startopfr(
            @AuthenticationPrincipal User user,
            Model model) {
        logiService.save(new Logi(new Date(), user.getLogin(), "Главная страница OpfrController startopfr()"));
        logger.info("User = " + user.getLogin() + " OpfrController startopfr()");
        try {
            Iterable<Zayav> zayavIterable = zayavService.findAllParameter(1,
                    false,
                    user,
                    null,
                    null,
                    1l,
                    null);
            model.addAttribute("zayavs", zayavIterable);

            Iterable<Rayon> rayons = rayonService.findAllUpfr("000", "999");
            model.addAttribute("rayon", rayons);

            Iterable<Dokumentzayav> dokumentzayavs = dokumentzayavService.findAllList(1);
            model.addAttribute("dokumentzayav", dokumentzayavs);

            Iterable<Dokumentu> dokumentu = documentuService.findAllList(1);
            model.addAttribute("dokument", dokumentu);

            model.addAttribute("user", user);

            model.addAttribute("stat", 1);
        } catch (Exception e) {
            logiService.save(new Logi(new Date(), user.getLogin(), 1l, " Что то пошло не так OpfrController startopfr() Error: " + e));
            logger.error("User = " + user.getLogin() + " Что то пошло не так OpfrController startopfr() Error: " + e);
        }
        return "opfr";
    }

    @GetMapping("/rb")
    public String rb(
            @RequestParam(value = "list", defaultValue = "1") int list,
            @RequestParam(value = "reg", defaultValue = "") String reg,
            @RequestParam(value = "inn", defaultValue = "") String inn,
            @RequestParam(value = "kodRayon", defaultValue = "") String kodRayon,
            @RequestParam(value = "stat", defaultValue = "1") String stat,
            @AuthenticationPrincipal User user,
            Model model) {
        logiService.save(new Logi(new Date(), user.getLogin(), " OpfrController rb()"));
        logger.info("User = " + user.getLogin() + " OpfrController rb()");
        try {
            Iterable<Zayav> zayavIterable = zayavService.findAllParameter(
                    list,
                    false,
                    user,
                    reg.equals("") ? null : reg,
                    inn.equals("") ? null : inn,
                    Long.parseLong(stat.trim()),
                    kodRayon.trim().equals("") ? null : kodRayon.trim());
            model.addAttribute("zayavs", zayavIterable);
            model.addAttribute("stat", Long.parseLong(stat.trim()));
        } catch (Exception e) {
            logiService.save(new Logi(new Date(), user.getLogin(), 1l, " Что то пошло не так OpfrController rb() Error: " + e));
            logger.error("User = " + user.getLogin() + " Что то пошло не так OpfrController rb() Error: " + e);
        }
        return "fragmentupfr/opfrfrag :: opfrhistory";
    }

    @GetMapping("/containerdocz")
    public String containerdocz(
            @RequestParam(value = "list", defaultValue = "1") int list,
            Model model) {
        /*logger.info("LOG OpfrController containerdocz()");*/
        try {
            Iterable<Dokumentzayav> dokumentzayavs = dokumentzayavService.findAllList(list);
            model.addAttribute("dokumentzayav", dokumentzayavs);
        } catch (Exception e) {
            logiService.save(new Logi(new Date(), "system", 1l, " Что то пошло не так OpfrController containerdocz() Error: " + e));
            logger.error("Что то пошло не так OpfrController containerdocz()");
        }

        return "fragmentupfr/opfrfrag :: dokumentzayav";
    }

    @GetMapping("/containerdoc")
    public String containerdoc(
            @RequestParam(value = "list", defaultValue = "1") int list,
            Model model) {
        logiService.save(new Logi(new Date(), "system", "Контейнер документов OpfrController containerdoc()"));
        logger.info("LOG OpfrController containerdoc()");
        try {
            Iterable<Dokumentu> dokumentu = documentuService.findAllList(list);
            model.addAttribute("dokument", dokumentu);

        } catch (Exception e) {
            logiService.save(new Logi(new Date(), "system", 1l, " Что то пошло не так OpfrController containerdoc() Error: " + e));
            logger.error("Что то пошло не так OpfrController containerdoc() Error: " + e);
        }
        return "fragmentupfr/opfrfrag :: dokument";
    }

    @GetMapping("/addupfr")
    public String addupfr(
            @RequestParam(value = "zid") String zid,
            @RequestParam(value = "nomzap") String nomzap,
            @RequestParam(value = "dataotprav") String dataotprav,
            @RequestParam(value = "doc") String doc,

            @RequestParam(value = "list", defaultValue = "1") int list,
            @RequestParam(value = "reg", defaultValue = "") String reg,
            @RequestParam(value = "inn", defaultValue = "") String inn,
            @RequestParam(value = "kodRayon", defaultValue = "") String kodRayon,
            @RequestParam(value = "stat", defaultValue = "1") String stat,
            @AuthenticationPrincipal User user,
            Model model) {
        logiService.save(new Logi(new Date(), user.getLogin(), " Добавление данных с регномером " + reg + " OpfrController addupfr()"));
        logger.info("User = " + user.getLogin() + " Добавление данных OpfrController addupfr()");
        try {

            Zayav zayav = zayavService.findById(Long.valueOf(zid));

            Dokumentzayav dokumentzayav = null;
            if (!(doc.equals("undefined") || doc.equals("") || (doc == null))) {
                dokumentzayav = dokumentzayavService.findById(Long.valueOf(doc)).get();
            }

            Opfr opfr = new Opfr(nomzap, dataotprav, null, null, null, zayav, user/*, dokumentzayav*/);
            opfrService.save(opfr);
            zayav.setStat(statService.findById(zayav.getStat().getId() + 1l).get());
            zayavService.save(zayav);

            Iterable<Zayav> zayavIterable = zayavService.findAllParameter(
                    list,
                    false,
                    user,
                    reg.equals("") ? null : reg,
                    inn.equals("") ? null : inn,
                    1l,
                    kodRayon.trim().equals("") ? null : kodRayon.trim());
            model.addAttribute("zayavs", zayavIterable);
            model.addAttribute("stat", 1);

        } catch (Exception e) {
            logiService.save(new Logi(new Date(), user.getLogin(), 1l, " Что то пошло не так OpfrController addupfr() Error: " + e));
            logger.error("User = " + user.getLogin() +
                    " Что то пошло не так OpfrController addupfr() Error: " + e);
        }

        return "fragmentupfr/opfrfrag :: opfrhistory";
    }

    @GetMapping("/updateupfr")
    public String updateupfr(
            @RequestParam(value = "zid") String zid,
            @RequestParam(value = "nomotv") String nomotv,
            @RequestParam(value = "dataotv") String dataotv,
            @RequestParam(value = "doc") String doc,
            @RequestParam(value = "sum") String sum,

            @RequestParam(value = "list", defaultValue = "1") int list,
            @RequestParam(value = "reg", defaultValue = "") String reg,
            @RequestParam(value = "inn", defaultValue = "") String inn,
            @RequestParam(value = "kodRayon", defaultValue = "") String kodRayon,

            @AuthenticationPrincipal User user,
            Model model) {
        logiService.save(new Logi(new Date(), user.getLogin(), "Обновление таблицы OpfrController updateupfr()"));
        logger.info("User = " + user.getLogin() + " OpfrController updateupfr()");
        try {

            Zayav zayav = zayavService.findById(Long.valueOf(zid));

            Dokumentu dokumentu = null;
            if (!(doc.equals("undefined") || doc.equals("") || (doc == null))) {
                dokumentu = documentuService.findById(Long.valueOf(doc)).get();
            }

            Opfr opfr = zayav.getOpfr();
            opfr.setDokumentu(dokumentu);
            opfr.setNomOtv(nomotv);
            opfr.setDataOtv(dataotv);
            opfrService.save(opfr);

            //увеличить статус
            zayav.setStat(statService.findById(zayav.getStat().getId() + 1l).get());
            zayavService.save(zayav);
            //sum2 добавить
            String vsekbk[] = sum.split(";");
            for (int i = 0; i < vsekbk.length; i += 2) {
                KbkVid kbkVid = kbkVidService.findById(Long.valueOf(vsekbk[i])).get();
                Float sum2 = Float.valueOf(vsekbk[i + 1].trim().replace(",", "."));//меняю запятые
                kbkVid.setSumm2(sum2);
                kbkVidService.save(kbkVid);
            }

            Iterable<Zayav> zayavIterable = zayavService.findAllParameter(
                    list,
                    false,
                    user,
                    reg.equals("") ? null : reg,
                    inn.equals("") ? null : inn,
                    2l,
                    kodRayon.trim().equals("") ? null : kodRayon.trim());
            model.addAttribute("zayavs", zayavIterable);
            model.addAttribute("stat", 2);
        } catch (Exception e) {
            logiService.save(new Logi(new Date(), user.getLogin(), 1l, " Что то пошло не так OpfrController updateupfr() Error: " + e));
            logger.error("User = " + user.getLogin() +
                    " Что то пошло не так OpfrController updateupfr() Error: " + e);
        }
        return "fragmentupfr/opfrfrag :: opfrhistory";
    }

    @GetMapping(
            value = "/documentpechat",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public @ResponseBody
    byte[] getDocumentPechat(
            @RequestParam(value = "id") Long id,
            @AuthenticationPrincipal User user,
            HttpServletResponse resp,
            Model model) throws IOException {
        logiService.save(new Logi(new Date(), user.getLogin(), "Печать документа OpfrController getDocumentPechat()"));
        logger.info("User = " + user.getLogin() + " OpfrController getDocumentPechat()");
        InputStream in = null;
        try {
            Zayav zayav = zayavService.findById(Long.valueOf(id));

            Dokumentu dokumentu;
            if (zayav.getKpp() == null || zayav.getKpp().equals("")) {
                //nokpp
                dokumentu = documentuService.findById(1l).get();
            } else {
                //kpp
                dokumentu = documentuService.findById(2l).get();
            }

            InputStream inputStream = new ByteArrayInputStream(dokumentu.getDokument());

            XWPFDocument docxFile = null;
            docxFile = new XWPFDocument(inputStream);
            // открываем файл и считываем его содержимое в объект XWPFDocument

            for (XWPFParagraph p : docxFile.getParagraphs()) {
                List<XWPFRun> runs = p.getRuns();
                if (runs != null) {
                    for (XWPFRun r : runs) {
                        String text = r.getText(0);
                        if (text != null && text.contains("$")) {
                            text = text.replace("$1", zayav.getDate_zayaFormat());
                            text = text.replace("$2", zayav.getRegnum());
                            text = text.replace("$3", zayav.getName());
                            text = text.replace("$4", zayav.getInn());
                            text = text.replace("$5", zayav.getKpp());
                            r.setText(text, 0);
                        }
                    }
                }
            }

            //находим таблицу
            XWPFTable T = null;
            for (XWPFTable tbl : docxFile.getTables()) {
                T = tbl;
                break;
            }

            //пришлось создать новый документ для параграфов
            XWPFDocument document = new XWPFDocument();

            for (KbkVid kbkVid : zayav.getKbkVids()) {
                XWPFTableRow tableRowTwo = T.createRow();

                XWPFParagraph paragraph = document.createParagraph();
                XWPFRun run = paragraph.createRun();
                run.setFontSize(13);
                run.setBold(false);
                run.setText(kbkVid.getAsvKbk().getKbkCode());
                tableRowTwo.getCell(0).setParagraph(paragraph);

                XWPFParagraph paragraph2 = document.createParagraph();
                XWPFRun run2 = paragraph2.createRun();
                run2.setFontSize(13);
                run2.setBold(false);
                run2.setText(String.valueOf(kbkVid.getSumm1()));
                tableRowTwo.getCell(1).setParagraph(paragraph2);

                XWPFParagraph paragraph3 = document.createParagraph();
                XWPFRun run3 = paragraph3.createRun();
                run3.setFontSize(13);
                run3.setBold(false);
                run3.setText(String.valueOf(kbkVid.getAsvKbk().getKbkShortName()));
                tableRowTwo.getCell(2).setParagraph(paragraph3);
            }

            ByteArrayOutputStream b = new ByteArrayOutputStream();
            docxFile.write(b);

            resp.setContentType("application/octet-stream");
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", "document.docx");
            resp.setHeader(headerKey, headerValue);
            resp.setContentLength(b.size());
            resp.getOutputStream().write(b.toByteArray());

            in = new ByteArrayInputStream(b.toByteArray());
        } catch (Exception e) {
            logiService.save(new Logi(new Date(), user.getLogin(), 1l, " Что то пошло не так OpfrController getDocumentPechat() Error: " + e));
            logger.error("User = " + user.getLogin() +
                    " Что то пошло не так OpfrController getDocumentPechat() Error: " + e);
        }
        return IOUtils.toByteArray(in);
    }

    @PostMapping(value = "/dokumentinsertz", produces = "text/plain")
    public @ResponseBody
    String dokumentinsertz(
            @AuthenticationPrincipal User user,
            HttpServletResponse resp,
            HttpServletRequest req,
            Model model) throws IOException {
        logiService.save(new Logi(new Date(), user.getLogin(), "Добавление документа OpfrController dokumentinsertz()"));
        logger.info("User = " + user.getLogin() + " OpfrController dokumentinsertz()");

        try {
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String description = null; // Retrieves <input type="text" name="description">
        String descriptionRU = null;
        try {
            descriptionRU = req.getParameter("description");
            description = cyr2lat(descriptionRU);
        } catch (Exception e) {
        }
        Part filePart = null;
        String fileName = "";
        try {
            filePart = req.getPart("file"); // Retrieves <input type="file" name="file">
            fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
        } catch (Exception e) {
        }
        if (descriptionRU == null || descriptionRU.equals("")) {
            description = fileName;
            descriptionRU = cyr2lat(fileName);
        } else { //регулярка чтобы добавить тип .doc
            Pattern p = Pattern.compile("^.+\\.");
            Matcher m = p.matcher(fileName);
            description = m.replaceAll(description + ".");
        }

        FileInputStream fileContent =
                fileContent = (FileInputStream) filePart.getInputStream();

        Dokumentzayav dokumentzayav = new Dokumentzayav(IOUtils.toByteArray(fileContent),
                description, descriptionRU);

        dokumentzayavService.save(dokumentzayav);
        return "OK";
    }

    @PostMapping(value = "/dokumentinsert", produces = "text/plain")
    public @ResponseBody
    String dokumentinsert(
            @AuthenticationPrincipal User user,
            HttpServletResponse resp,
            HttpServletRequest req,
            Model model) throws IOException {
        logiService.save(new Logi(new Date(), user.getLogin(), "Добавление документа OpfrController dokumentinsert()"));
        logger.info("User = " + user.getLogin() + " OpfrController dokumentinsert()");
        try {
            req.setCharacterEncoding("UTF-8");
            resp.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String description = null; // Retrieves <input type="text" name="description">
        String descriptionRU = null;
        try {
            descriptionRU = req.getParameter("description");
            description = cyr2lat(descriptionRU);
        } catch (Exception e) {
        }
        Part filePart = null;
        String fileName = "";
        try {
            filePart = req.getPart("file"); // Retrieves <input type="file" name="file">
            fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
        } catch (Exception e) {
        }
        if (descriptionRU == null || descriptionRU.equals("")) {
            description = fileName;
            descriptionRU = cyr2lat(fileName);
        } else { //регулярка чтобы добавить тип .doc
            Pattern p = Pattern.compile("^.+\\.");
            Matcher m = p.matcher(fileName);
            description = m.replaceAll(description + ".");
        }

        FileInputStream fileContent =
                fileContent = (FileInputStream) filePart.getInputStream();

        Dokumentu dokument = new Dokumentu(IOUtils.toByteArray(fileContent), description, descriptionRU);

        documentuService.save(dokument);
        return "OK";
    }

    @GetMapping("/stat")
    public String stat(@AuthenticationPrincipal User user,
                       Model model) {

        model.addAttribute("user", user);
        Iterable<StatistikaViev> statistikaVievs = statistikaRepository.findAll();

        long s1[] = new long[4];
        double d = 0;


        for (StatistikaViev s:
        statistikaVievs) {
            s1[0] += Long.parseLong(s.getKolvo());
            d += Double.parseDouble(s.getSumma().replace(',', '.'));
            s1[1] += Long.parseLong(s.getS2());
            s1[2] += Long.parseLong(s.getS3());
            s1[3] += Long.parseLong(s.getS4());
        }


        model.addAttribute("statistika", statistikaVievs);
        model.addAttribute("s1", s1);

        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(2);

        model.addAttribute("d", df.format(d));

        return "stat";
    }

}
