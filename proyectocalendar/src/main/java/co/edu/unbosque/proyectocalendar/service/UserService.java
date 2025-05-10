package co.edu.unbosque.proyectocalendar.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.proyectocalendar.dto.UserDTO;
import co.edu.unbosque.proyectocalendar.model.User;
import co.edu.unbosque.proyectocalendar.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ModelMapper modelMapper;

	public boolean exist(Long id) {
		return userRepo.existsById(id);
	}

	public int create(UserDTO data) {
		User entity = modelMapper.map(data, User.class);
		if (findUsernameAlreadyTaken(entity)) {
			return 4;
		} else {
			if (!esCorreoValido(entity.getEmail())) {
				return 3;
			} else {
				if (!esContraseñaSegura(entity.getPassword())) {
					return 2;
				} else {
					if (!entity.getPassword().equals(data.getConfirmPassword())) {
						return 1;
					} else {
						userRepo.save(entity);
						return 0;
					}
				}
			}
		}
	}

	public boolean findUsernameAlreadyTaken(User newUser) {
		Optional<User> found = userRepo.findByUserName(newUser.getUserName());
		if (found.isPresent()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean esContraseñaSegura(String password) {
		String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?#])[A-Za-z\\d@$!%*?#]{12,}$";
		return password.matches(regex);
	}

	public boolean esCorreoValido(String email) {
		String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
		return email.matches(regex);
	}
}
