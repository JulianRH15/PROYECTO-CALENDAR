package co.edu.unbosque.proyectocalendar.controller;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.proyectocalendar.dto.UserDTO;
import co.edu.unbosque.proyectocalendar.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = { "*" })
public class UserController {
	@Autowired
	private UserService userServ;

	@PostMapping("/crear")
	public ResponseEntity<String> crear(@RequestParam String userName, @RequestParam String email,
			@RequestParam String password, @RequestParam String confirmPassword) {

		UserDTO nuevoUsuario = new UserDTO(userName, email, password, confirmPassword);
		int estado = userServ.create(nuevoUsuario);
		if (estado == 0) {
			return new ResponseEntity<>("Usuario creado con exito", HttpStatus.CREATED);
		} else {
			if (estado == 3) {
				return new ResponseEntity<>("Error al crear usuario, el correo no es valido",
						HttpStatus.NOT_ACCEPTABLE);
			} else {
				if (estado == 2) {
					return new ResponseEntity<>(
							"Error al crear usuario, la contraseña debe tener más de 6 caracteres, al menos una letra mayuscula, al menos una letra minuscula, al menos un numero  y por lo menos un caracter especial",
							HttpStatus.NOT_ACCEPTABLE);
				} else {
					if (estado == 1) {
						return new ResponseEntity<>("Error al crear usuario, las contraseñas no coinciden",
								HttpStatus.NOT_ACCEPTABLE);
					}
				}
				return new ResponseEntity<>("Error al crear usuario", HttpStatus.NOT_ACCEPTABLE);

			}
		}
	}

	@PostMapping("/crearconjson")
	public ResponseEntity<String> crearConJson(@RequestBody UserDTO nuevoUsuario) {
		int estado = userServ.create(nuevoUsuario);
		if (estado == 0) {
			return new ResponseEntity<String>("Uusario creado", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<String>("Error al crear usuario", HttpStatus.NOT_ACCEPTABLE);
		}
	}

}
