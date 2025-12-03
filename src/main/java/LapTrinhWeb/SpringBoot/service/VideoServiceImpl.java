package LapTrinhWeb.SpringBoot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import LapTrinhWeb.SpringBoot.entity.Video;
import LapTrinhWeb.SpringBoot.repository.VideoRepository;

@Service
public class VideoServiceImpl implements VideoService {
	@Autowired
	VideoRepository videoRepository;

	@Override
	public List<Video> findAll() {
		return videoRepository.findAll();
	}

	@Override
	public Optional<Video> findById(Integer id) {
		return videoRepository.findById(id);
	}

	@Override
	public Video save(Video video) {
		return videoRepository.save(video);
	}

	@Override
	public void deleteById(Integer id) {
		videoRepository.deleteById(id);
	}

	@Override
	public List<Video> findByTitleContaining(String title) {
		return videoRepository.findByTitleContaining(title);
	}

	@Override
	public Page<Video> findByTitleContaining(String title, Pageable pageable) {
		return videoRepository.findByTitleContaining(title, pageable);
	}

	@Override
	public List<Video> findByCategoryCategoryId(Integer categoryId) {
		return videoRepository.findByCategoryCategoryId(categoryId);
	}

	@Override
	public long count() {
		return videoRepository.count();
	}
}
