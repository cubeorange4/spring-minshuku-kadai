package com.example.samuraitravel.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.samuraitravel.entity.Favorite;

@Service
public class FavoriteService {
	
	@Transactional
	public void create() {
		Favorite favorite = new Favorite();
	}
}
