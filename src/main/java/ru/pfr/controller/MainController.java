package ru.pfr.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pfr.Application;
import ru.pfr.model.pkv.Logi;
import ru.pfr.model.pkv.User;
import ru.pfr.service.LogiService;

import java.util.Date;

@Controller
@RequestMapping(value = {"/", "/pkv"})
public class MainController {


    @Autowired
    LogiService logiService;

    private static final Logger logger = LogManager.getLogger(Application.class);

    @RequestMapping
    public String mains(
            @AuthenticationPrincipal User user,
            Model model) {
        logiService.save(new Logi(new Date(),user.getLogin(),"Авторизация прошла успешно MainController mains()"));
        logger.info("User = " + user.getLogin() + " Авторизация прошла успешно MainController mains()");
        if (user.getRayon().getKod().equals("000"))
            return "redirect:/pkv/opfr";
        else if (user.getRayon().getKod().equals("999"))
            return "redirect:/pkv/admintwo";
        else if (user.getRayon().getKod().equals("001") ||
                user.getRayon().getKod().equals("002") ||
                user.getRayon().getKod().equals("003") ||
                user.getRayon().getKod().equals("004") ||
                user.getRayon().getKod().equals("005") ||
                user.getRayon().getKod().equals("006") ||
                user.getRayon().getKod().equals("007") ||
                user.getRayon().getKod().equals("009") ||
                user.getRayon().getKod().equals("013") ||
                user.getRayon().getKod().equals("014") ||
                user.getRayon().getKod().equals("015") ||
                user.getRayon().getKod().equals("016") ||
                user.getRayon().getKod().equals("017") ||
                user.getRayon().getKod().equals("018") ||
                user.getRayon().getKod().equals("019") ||
                user.getRayon().getKod().equals("020") ||
                user.getRayon().getKod().equals("021") ||
                user.getRayon().getKod().equals("022") ||
                user.getRayon().getKod().equals("023") ||
                user.getRayon().getKod().equals("024") ||
                user.getRayon().getKod().equals("025") ||
                user.getRayon().getKod().equals("026") ||
                user.getRayon().getKod().equals("027"))
            return "redirect:/pkv/upfr";
        return "/pkv";
    }
}
