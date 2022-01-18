package com.capgemini.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.capgemini.entities.Persona;
import com.capgemini.service.IPersonaService;

@Controller
@RequestMapping("/")
public class MainController {
	
	@Autowired
	private IPersonaService personaService;
	
	@GetMapping("/listar")
	public String listado(Model model) {
		
		model.addAttribute("personas", personaService.personas());
		
		return "listado";
	}
	
	@GetMapping("/crear")
	public String crear(Model model) {
		
		model.addAttribute("persona", new Persona());
	
		return "formCrear";
	}
	
	@PostMapping("/guardar")
	public String guardar(@ModelAttribute(name = "persona") Persona persona, @RequestParam(name = "file") MultipartFile imagen) {
		
		if (! imagen.isEmpty()) {
			
			// Obtenemos la ruta relativa
			Path imagesFolder = Paths.get("src//main//resources//static/images");
			
			// Ruta absoluta
			String rutaAbsoluta = imagesFolder.toFile().getAbsolutePath();
			
			try {
				byte[] bytesImages = imagen.getBytes();
				
				// Ruta completa, que incluye el nombre original de la imagen 
				Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + imagen.getOriginalFilename());
				
				Files.write(rutaCompleta, bytesImages);
				
				persona.setFoto(imagen.getOriginalFilename());
				
				personaService.guardarPersona(persona);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return "redirect:/listar";
	}
	
	@GetMapping("/detalle/{id}")
	public String detalles(@PathVariable(name = "id") String id, Model model) {
		
		Persona persona = null;
		
		persona = personaService.getPersona(id);
		
		model.addAttribute("persona", persona);
		
		return "detalles";
	}
}
