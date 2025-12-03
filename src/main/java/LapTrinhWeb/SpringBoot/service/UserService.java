package LapTrinhWeb.SpringBoot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import LapTrinhWeb.SpringBoot.entity.User;

public interface UserService {
	List<User> findAll();
	Optional<User> findById(String id);
	User save(User user);
	void deleteById(String id);
	List<User> findByFullnameContaining(String name);
	Page<User> findByFullnameContaining(String name, Pageable pageable);
	List<User> findByAdmin(Boolean admin);
	long count();
}
