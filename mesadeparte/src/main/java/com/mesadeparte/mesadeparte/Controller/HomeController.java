package com.mesadeparte.mesadeparte.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mesadeparte.mesadeparte.Entities.Usuario;
import com.mesadeparte.mesadeparte.Services.UsuarioServices;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

@Controller
public class HomeController {
    @Autowired
    UsuarioServices service;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        Usuario u = new Usuario();
        model.addAttribute("usuario", u);
        return "/login";
    }
    
    @GetMapping("/home")
    public String dashboard (Model model) {
        Usuario u = new Usuario();
        model.addAttribute("usuario", u);
        return "/home";
    }


    @PostMapping("/login")
    public String login(@RequestParam String dni, @RequestParam String clave, Model model, HttpSession session) {
        Usuario u = service.leeLogin(dni, clave);
        if (u==null) {return "redirect:/";}
        model.addAttribute("usuario",u);
        session.setAttribute("idsession", u.getIdusuario());
        session.setAttribute("tiposession",u.getTipo());
        return "redirect:home";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("idsession");
        session.removeAttribute("tiposession");
        return "redirect:login";
    }
}
