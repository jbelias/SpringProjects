
package com.egg.noticias.controladores;

import com.egg.noticias.entidades.Noticia;
import com.egg.noticias.exceptions.MiException;
import com.egg.noticias.servicios.NoticiaServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/noticia")
public class NoticiaControlador {
    
    @Autowired
    private NoticiaServicio noticiaServicio;
    
    @GetMapping("/registrar") //localhost:8080/noticia/registrar
    public String registrar() {
        return "noticia_form.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String titulo, @RequestParam String cuerpo, ModelMap modelo){
        
        try {
            noticiaServicio.crearNoticia(titulo, cuerpo);
            
            modelo.put("Exito", "La noticia fue cargado correctamente");
        } catch (MiException ex) {
            
            modelo.put("Error", ex.getMessage());
            return "noticia_form.html";
        }
        
        return "index.html";
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        List<Noticia> noticias = noticiaServicio.listarNoticia();
        
        modelo.addAttribute("noticias", noticias);
        
        return "noticia_list.html";
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        
        modelo.put("noticia", noticiaServicio.getOne(id));
        
        return "noticia_modificar.html";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String titulo, String cuerpo, ModelMap modelo){
        try {
            noticiaServicio.modificarNoticia(titulo, cuerpo);
            return "redirect:../lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "noticia_modificar.html";
        }
    }
    
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id) throws MiException{
        
        noticiaServicio.eliminar(id);
        
        return "redirect:../lista";
    }
    
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, String titulo, String cuerpo, ModelMap modelo) {
        try {
            noticiaServicio.eliminar(id);
            return "redirect:../lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "noticia_eliminar.html";
        }
    }
}