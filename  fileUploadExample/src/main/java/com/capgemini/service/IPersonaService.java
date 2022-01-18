package com.capgemini.service;

import java.util.List;

import com.capgemini.entities.Persona;

public interface IPersonaService {

	public List<Persona> personas();
	public Persona getPersona(String id);
	public void guardarPersona(Persona persona);
}
