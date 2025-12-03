package LapTrinhWeb.SpringBoot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import LapTrinhWeb.SpringBoot.entity.User;
import LapTrinhWeb.SpringBoot.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> findById(String id) {
		return userRepository.findById(id);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public void deleteById(String id) {
		userRepository.deleteById(id);
	}

	@Override
	public List<User> findByFullnameContaining(String name) {
		return userRepository.findByFullnameContaining(name);
	}

	@Override
	public Page<User> findByFullnameContaining(String name, Pageable pageable) {
		return userRepository.findByFullnameContaining(name, pageable);
	}

	@Override
	public List<User> findByAdmin(Boolean admin) {
		return userRepository.findByAdmin(admin);
	}

	@Override
	public long count() {
		return userRepository.count();
	}
}
