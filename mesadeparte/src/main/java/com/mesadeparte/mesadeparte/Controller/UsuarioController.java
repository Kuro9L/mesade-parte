package com.mesadeparte.mesadeparte.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mesadeparte.mesadeparte.Entities.Usuario;
import com.mesadeparte.mesadeparte.Entities.UsuarioDto;
import com.mesadeparte.mesadeparte.Services.UsuarioServices;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioServices service;

    @GetMapping({ "", "/" })
    public String getUsuario(Model model, HttpSession session) {

        if (session.getAttribute("tiposession") == null) {
            return "redirect:/login";
        }
        var usuario = service.listUsuario();
        model.addAttribute("usuario", usuario);
        return "usuario/index";
    }

    @GetMapping({ "/create" })
    public String createUsuario(Model model, HttpSession session) {

        if (session.getAttribute("tiposession") == null) {
            return "redirect:/login";
        }
        UsuarioDto dto = new UsuarioDto();
        model.addAttribute("dto", dto);
        return "usuario/create";
    }

    @PostMapping({ "/create" })
    public String createUsuario(@ModelAttribute UsuarioDto dto) {
        Usuario usuario = new Usuario();
        usuario.setDni(dto.getDni());
        usuario.setApellidos(dto.getApellidos());
        usuario.setNombres(dto.getNombres());
        usuario.setClave(dto.getClave());
        usuario.setTipo(dto.getTipo());
        service.saveUsuario(usuario);
        return "redirect:/usuario";
    }

    @GetMapping({ "/edit" })
    public String editUsuario(Model model, @RequestParam Long id, HttpSession session) {
        if (session.getAttribute("tiposession") == null) {
            return "redirect:/login";
        }
        Usuario usuario = service.leeIdUsuario(id);
        if (usuario == null) {
            return "redirect:/usuario";
        }
        UsuarioDto dto = new UsuarioDto();
        dto.setDni(usuario.getDni());
        dto.setApellidos(usuario.getApellidos());
        dto.setNombres(usuario.getNombres());
        dto.setClave(usuario.getClave());
        dto.setTipo(usuario.getTipo());
        dto.setEstado(usuario.getEstado());
        model.addAttribute("dto", dto);
        model.addAttribute("usuario", usuario);
        return "usuario/edit";
    }

    @PostMapping({ "/edit" })
    public String editUsuario(@ModelAttribute UsuarioDto dto, @RequestParam Long id) {
        Usuario usuario = new Usuario();
        usuario.setIdusuario(id);
        usuario.setDni(dto.getDni());
        usuario.setApellidos(dto.getApellidos());
        usuario.setNombres(dto.getNombres());
        usuario.setClave(dto.getClave());
        usuario.setTipo(dto.getTipo());
        usuario.setEstado(dto.getEstado());

        service.saveUsuario(usuario);
        return "redirect:/usuario";
    }

    @GetMapping({ "/delete" })
    public String deleteUsuario(@RequestParam Long id) {
        Usuario usuario = service.leeIdUsuario(id);
        if (usuario != null) {
            service.deleteUsuario(id);
        }
        return "redirect:/usuario";
    }
}
