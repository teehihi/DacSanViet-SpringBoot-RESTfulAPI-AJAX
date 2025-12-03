package LapTrinhWeb.SpringBoot.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import LapTrinhWeb.SpringBoot.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	List<Category> findByCategorynameContaining(String name);
	Page<Category> findByCategorynameContaining(String name, Pageable pageable);
	List<Category> findByUserUsername(String username);
}
