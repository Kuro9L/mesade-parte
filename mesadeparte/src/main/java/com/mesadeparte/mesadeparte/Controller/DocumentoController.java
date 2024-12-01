package com.mesadeparte.mesadeparte.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.PathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.mesadeparte.mesadeparte.Entities.Documento;
import com.mesadeparte.mesadeparte.Entities.DocumentoDto;
import com.mesadeparte.mesadeparte.Entities.Seguimiento;
import com.mesadeparte.mesadeparte.Entities.SeguimientoDto;
import com.mesadeparte.mesadeparte.Entities.Usuario;
import com.mesadeparte.mesadeparte.Services.DocumentoServices;
import com.mesadeparte.mesadeparte.Services.SeguimientoServices;
import com.mesadeparte.mesadeparte.Services.UsuarioServices;

import java.util.Date;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/documento")
public class DocumentoController {

    @Autowired
    private DocumentoServices service;

    @Autowired
    private SeguimientoServices serviceSeguimiento;

    @Autowired
    private UsuarioServices serviceUsuario;

    @GetMapping({ "", "/" })
    public String getDocumento(Model model, HttpSession session) {
        if (session.getAttribute("tiposession") == null) {
            return "redirect:/login";
        }
        var documento = service.listDocumento();
        // Procesamos los documentos para extraer solo el nombre del archivo
        documento.forEach(doc -> {
            String archivoNombre = Paths.get(doc.getArchivo()).getFileName().toString();
            doc.setArchivo(archivoNombre); // Reemplazamos la ruta completa por el nombre
        });
        model.addAttribute("documento", documento);
        return "documento/index";
    }

    @GetMapping({ "/create" })
    public String createDocumento(Model model) {

        DocumentoDto dto = new DocumentoDto();
        model.addAttribute("dto", dto);
        return "documento/create";
    }

    @PostMapping({ "/create" })
    public String createDocumento(@ModelAttribute DocumentoDto dto, @RequestParam("archivo") MultipartFile file) {
        Documento documento = new Documento();

        try { // Generar un código de seguridad aleatorio de 4 dígitos
            Random rand = new Random();
            int codSeguridad = 1000 + rand.nextInt(9000); // Genera un número entre 1000 y 9999
            documento.setCodseguridad(String.valueOf(codSeguridad)); // Asignar el código como un String

            // Establecer el estado por defecto como "En Proceso"
            documento.setEstado("1");

            // Establecer la fecha actual por defecto
            LocalDateTime fechaActual = LocalDateTime.now(); // Obtiene la fecha y hora actual
            Date fechaUtil = Date.from(fechaActual.atZone(ZoneId.systemDefault()).toInstant()); // Convierte
                                                                                                // LocalDateTime a
                                                                                                // java.util.Date
            java.sql.Date fechaSql = new java.sql.Date(fechaUtil.getTime()); // Convierte java.util.Date a java.sql.Date
            documento.setFecha(fechaSql); // Asigna la fecha al documento

            // Guardar archivo en una ruta específica
            String fileName = file.getOriginalFilename();
            String uploadDir = "uploads/"; // Ruta donde se guardarán los archivos
            try {
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                file.transferTo(uploadPath.resolve(fileName));
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/create?error";
            }

            documento.setNumexpediente(dto.getNumexpediente());
            documento.setTipodoc(dto.getTipodoc());
            documento.setFolio(dto.getFolio());
            documento.setAsunto(dto.getAsunto());

            // Aca se Guardan los datos de Remitente//
            documento.setTipopersona(dto.getTipopersona());
            documento.setNumdoc(dto.getNumdoc());
            documento.setNombres(dto.getNombres());
            documento.setCorreo(dto.getCorreo());
            documento.setTelefono(dto.getTelefono());

            // Guardar la ruta del archivo
            documento.setArchivo(uploadDir + fileName);

            service.saveDocumento(documento);
            return "redirect:/?success=true";
        } // Redirigir con el mensaje de éxito
        catch (Exception e) {
            e.printStackTrace();
            return "redirect:/create?error=true"; // Redirigir con el mensaje de error
        }

    }

    @GetMapping("/view/{id}")
    public ResponseEntity<Resource> viewDocumento(@PathVariable("id") Long numExpediente) {
        // Recupera el documento de la base de datos usando el numExpediente
        Documento documento = service.leeIdDocumento(numExpediente);
        if (documento == null) {
            return ResponseEntity.notFound().build(); // Si no se encuentra el documento
        }

        // Obtén la ruta del archivo
        String archivoPath = documento.getArchivo(); // 'uploads/nombre_del_archivo.pdf'
        Path path = Paths.get(archivoPath);

        // Verifica si el archivo existe
        if (Files.exists(path)) {
            Resource resource = new PathResource(path); // Usamos PathResource
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF) // Establece el tipo de contenido a PDF
                    .body(resource);
        }

        // Si no existe el archivo, devuelve un error
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping({ "/edit" })
    public String editDocumento(Model model, @RequestParam Long id, HttpSession session) {
        if (session.getAttribute("tiposession") == null) {
            return "redirect:/login";
        }

        Documento documento = service.leeIdDocumento(id);
        if (documento == null) {
            return "redirect:/documento";
        }

        DocumentoDto dto = new DocumentoDto();
        dto.setFecha(documento.getFecha());
        dto.setNumexpediente(documento.getNumexpediente());
        dto.setNombres(documento.getNombres());
        dto.setTipodoc(documento.getTipodoc());
        dto.setNumdoc(documento.getNumdoc());
        dto.setAsunto(documento.getAsunto());
        dto.setFolio(documento.getFolio());
        dto.setEstado(documento.getEstado());

        model.addAttribute("dto", dto);
        model.addAttribute("documento", documento);

        // Cargar seguimientos
        var seguimientos = documento.getSeguimientos();
        model.addAttribute("seguimientos", seguimientos);

        return "documento/edit";
    }

    @PostMapping({ "/edit" })
    public String editUsuario(@ModelAttribute DocumentoDto dto, @RequestParam Long id,
            @RequestParam(value = "archivo", required = false) MultipartFile file) {
        // Buscar el documento existente por su ID
        Documento documento = service.leeIdDocumento(id);
        if (documento == null) {
            return "redirect:/documento"; // Si el documento no existe, redirigir
        }

        // Solo actualizar el estado, los demás campos permanecen igual
        documento.setEstado(dto.getEstado());

        // Si se envió un archivo nuevo, guardarlo y actualizar la ruta
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String uploadDir = "uploads/"; // Ruta donde se guardarán los archivos
            try {
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                file.transferTo(uploadPath.resolve(fileName));
                // Actualizar la ruta del archivo
                documento.setArchivo(uploadDir + fileName);
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/documento?error=ErrorAlSubirArchivo"; // En caso de error al subir archivo
            }
        }

        // Guardar los cambios en la base de datos (solo el estado y el archivo si es
        // nuevo)
        service.saveDocumento(documento); // Asegúrate de que este método guarda correctamente la entidad

        return "redirect:/documento"; // Redirigir a la página de documentos después de la actualización
    }

    

}
