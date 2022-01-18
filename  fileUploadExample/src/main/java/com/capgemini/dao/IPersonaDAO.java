package com.capgemini.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.entities.Persona;

@Repository
public interface IPersonaDAO extends JpaRepository<Persona, Long> {

}
