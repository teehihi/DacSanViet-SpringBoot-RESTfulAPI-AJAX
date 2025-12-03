package LapTrinhWeb.SpringBoot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import LapTrinhWeb.SpringBoot.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	List<User> findByFullnameContaining(String name);
	Page<User> findByFullnameContaining(String name, Pageable pageable);
	List<User> findByAdmin(Boolean admin);
}
