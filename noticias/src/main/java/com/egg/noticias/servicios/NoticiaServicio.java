package com.egg.noticias.servicios;

import com.egg.noticias.entidades.Noticia;
import com.egg.noticias.exceptions.MiException;
import com.egg.noticias.repositorios.NoticiaRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NoticiaServicio {
    
    @Autowired
    private NoticiaRepositorio noticiaRepositorio;
    
    @Transactional
    public void crearNoticia(String titulo, String cuerpo) throws MiException{
        
        validar(titulo, cuerpo);
        
        Noticia noticia = new Noticia();
        
        noticia.setTitulo(titulo);
        noticia.setCuerpo(cuerpo);
        
        noticia.setAlta(new Date());
        
        noticiaRepositorio.save(noticia);
    }
    
    public List<Noticia> listarNoticia(){
        
        List<Noticia> noticias = new ArrayList();
        
        noticias = noticiaRepositorio.findAll();
        
        return noticias;
    }
    
    @Transactional
    public void modificarNoticia(String titulo, String cuerpo) throws MiException{
        
        validar( titulo, cuerpo);
        
        Optional <Noticia> respuesta = noticiaRepositorio.findById(titulo);
        
        if (respuesta.isPresent()) {
            
            Noticia noticia = respuesta.get();
            
            noticia.setTitulo(titulo);
            noticia.setCuerpo(cuerpo);
            
            noticiaRepositorio.save(noticia);
        }
    }
    
    public Noticia getOne(String id){
        return noticiaRepositorio.getOne(id);
        
    }
    
    private void validar(String titulo, String cuerpo) throws MiException{
        if (titulo.isEmpty() || titulo == null){
            throw new MiException("El titulo no puede ser nulo o estar vacio");
        }
        if (cuerpo == null || cuerpo.isEmpty()){
            throw new MiException("El cuerpo de la noticia no puede estar vacio");
        }
    }

    @Transactional
    public void eliminar(String id)throws MiException {
        
        noticiaRepositorio.deleteById(id);
    }
}
