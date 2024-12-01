package com.mesadeparte.mesadeparte.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mesadeparte.mesadeparte.Services.SeguimientoServices;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/seguimiento")
public class SeguimientoController {

    @Autowired
    private SeguimientoServices service;

    @GetMapping({ "", "/" })
    public String getSeguimiento(Model model, HttpSession session) {

        if (session.getAttribute("tiposession") == null) {
            return "redirect:/login";
        }
        var seguimiento = service.listSeguimiento();
        model.addAttribute("seguimiento", seguimiento);
        return "usuario/index";
    }

}
