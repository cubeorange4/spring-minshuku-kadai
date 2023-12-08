package com.example.samuraitravel.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.form.ReviewForm;
import com.example.samuraitravel.repository.HouseRepository;
import com.example.samuraitravel.repository.ReviewRepository;
import com.example.samuraitravel.repository.UserRepository;

@Service
public class ReviewService {
	private final HouseRepository houseRepository;
	private final ReviewRepository reviewRepository;
	private final UserRepository userRepository;
	
	public ReviewService(HouseRepository houseRepository, ReviewRepository reviewRepository, UserRepository userRepository) {
		this.houseRepository = houseRepository;
		this.reviewRepository = reviewRepository;
		this.userRepository = userRepository;
	}
	
	@Transactional
	public void create(ReviewForm reviewForm) {
		Review review = new Review();
		House house = houseRepository.getReferenceById(reviewForm.getHouseId());
		User user = userRepository.getReferenceById(reviewForm.getUserName());
		
		review.setHouse(house);
		review.setUser(user);
		review.setContent(reviewForm.getContent());
		review.setScore(reviewForm.getScore());
		
		reviewRepository.save(review);
	}
}
