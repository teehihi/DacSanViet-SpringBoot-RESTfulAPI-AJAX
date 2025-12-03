package LapTrinhWeb.SpringBoot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import LapTrinhWeb.SpringBoot.entity.Category;
import LapTrinhWeb.SpringBoot.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	public Optional<Category> findById(Integer id) {
		return categoryRepository.findById(id);
	}

	@Override
	public Category save(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public void deleteById(Integer id) {
		categoryRepository.deleteById(id);
	}

	@Override
	public List<Category> findByCategorynameContaining(String name) {
		return categoryRepository.findByCategorynameContaining(name);
	}

	@Override
	public Page<Category> findByCategorynameContaining(String name, Pageable pageable) {
		return categoryRepository.findByCategorynameContaining(name, pageable);
	}

	@Override
	public List<Category> findByUserUsername(String username) {
		return categoryRepository.findByUserUsername(username);
	}

	@Override
	public long count() {
		return categoryRepository.count();
	}
}
