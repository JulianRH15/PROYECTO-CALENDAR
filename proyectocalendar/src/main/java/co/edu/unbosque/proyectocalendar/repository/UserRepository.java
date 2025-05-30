package co.edu.unbosque.proyectocalendar.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.proyectocalendar.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
	public Optional<User> findByUserName(String userName);


}
