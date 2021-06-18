package ru.pfr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pfr.Application;
import ru.pfr.model.pkv.Logi;
import ru.pfr.model.pkv.Rayon;
import ru.pfr.model.pkv.User;
import ru.pfr.service.LogiService;
import ru.pfr.service.RayonService;
import ru.pfr.service.UserService;

@Controller
@RequestMapping("/pkv/admin")
public class AdminController {

    @Autowired
    RayonService rayonService;

    @Autowired
    UserService userService;

    @Autowired
    LogiService logiService;

    @GetMapping()
    public String rayadd(
            @AuthenticationPrincipal User user,
            Model model) {

        User ss = user;

        Iterable<User> users = userService.findAll();
        model.addAttribute("users", users);

        Iterable<Rayon> rayons = rayonService.findAll();
        model.addAttribute("rayons", rayons);

        return "admin";
    }


    @GetMapping("/frag")
    public String rayadd2(@RequestParam String namerayon,
                          @RequestParam String kod,
                          Model model) {
        Rayon rayon = new Rayon(namerayon, kod);
        rayonService.save(rayon);

        Iterable<Rayon> rayons = rayonService.findAll();
        model.addAttribute("rayons", rayons);

        return "fragment/ray :: frag";
    }

    @GetMapping("/frag2")
    public String rayadd3(Model model) {
        Iterable<Rayon> rayons = rayonService.findAll();
        model.addAttribute("rayons", rayons);
        return "fragment/ray :: frag2";
    }

/*    @GetMapping("/frag1")
    public String useradd(@RequestParam String login,
                          @RequestParam String password,
                          @RequestParam String name,
                          @RequestParam Long id,
                          Model model) {

        Rayon rayon = rayonService.findById(id).get();
        User user = new User(login, password, name, rayon);
        userService.save(user);

        Iterable<User> users = userService.findAll();
        model.addAttribute("users", users);

        Iterable<Rayon> rayons = rayonService.findAll();
        model.addAttribute("rayons", rayons);

        return "fragment/ray :: frag1";
    }*/



}
