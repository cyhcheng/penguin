package org.penguin.project.tutorial.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Slf4j
@Controller
public class HomeController {

    @Value("${tutorial.app.title}")
    String title;

    @GetMapping(path = "/")
    @ResponseBody
    public String all() {
        return "Spring boot Tutorial Classic —— Home";
    }

    @GetMapping("/album/{id}")
    public String pcAlbum(@PathVariable int id, Model model, HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent");
        Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            log.info("{} = {}", name, request.getHeader(name));
        }
        log.info("/pc/album?id=" + id);
        model.addAttribute("id", id);
        model.addAttribute("userAgent", userAgent);
        model.addAttribute("title", title);
        log.info("属性: {}", title);
        return "/pc/album";
    }

}
