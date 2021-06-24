package com.damidiaz.microservicios.app.usuarios.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.damidiaz.microservicios.app.usuarios.models.entity.Alumno;
import com.damidiaz.microservicios.app.usuarios.services.AlumnoService;

@RestController
public class AlumnoController {

	@Autowired
	private AlumnoService alumnoService;
	
	@GetMapping
	//<?> para poder guardar en el body de la respuesta cualquier entidad
	public ResponseEntity<?> listar(){
		return ResponseEntity.ok().body(this.alumnoService.findAll()); //cuando el status del response sea 200(.ok()) se va a cargar en el cuerpo la lista de todos los alumnos
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> ver(@PathVariable Long id){
		
		Optional<Alumno> optional = this.alumnoService.findById(id);
		
		if(optional.isEmpty()) {
			return ResponseEntity.notFound().build(); //si el status es 404 (.notFound()) construye la respuesta (.build) con el cuerpo vacio
			
		}
		
		return ResponseEntity.ok(optional.get());
	}
	
	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Alumno alumno){
		Alumno alumnoDb = this.alumnoService.save(alumno);
		return ResponseEntity.status(HttpStatus.CREATED).body(alumnoDb);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@RequestBody Alumno alumno, @PathVariable Long id){
		
		Optional<Alumno> optional = this.alumnoService.findById(id);
		
		if(optional.isEmpty()) {
			ResponseEntity.notFound().build(); //si el status es 404 (.notFound()) construye la respuesta (.build) con el cuerpo vacio
			
		}
		
		Alumno alumnoDb = optional.get();
		alumnoDb.setApellido(alumno.getNombre());
		alumnoDb.setApellido(alumno.getApellido());
		alumnoDb.setEmail(alumno.getEmail());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(this.alumnoService.save(alumnoDb));
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id){
		this.alumnoService.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	
	
	
	
	
	
	
	
	
}
