package com.example.samuraitravel.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.samuraitravel.entity.Favorite;
import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.repository.FavoriteRepository;
import com.example.samuraitravel.repository.HouseRepository;
import com.example.samuraitravel.security.UserDetailsImpl;
import com.example.samuraitravel.service.FavoriteService;

public class FavoriteController {
	private final FavoriteRepository favoriteRepository;
	private final HouseRepository houseRepository;
	private final FavoriteService favoriteService;
	
	public FavoriteController(FavoriteRepository favoriteRepository, HouseRepository houseRepository, FavoriteService favoriteService) {
		this.favoriteRepository = favoriteRepository;
		this.houseRepository = houseRepository;
		this.favoriteService = favoriteService;
	}
	
	@GetMapping("/favorite")
	public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, Model model) {
		User user = userDetailsImpl.getUser();
		Page<Favorite> favoritePage = favoriteRepository.findByUserOrderByCreatedAtDesc(user, pageable);
		
		model.addAttribute("favoritePage", favoritePage);
		
		return "favorite/index";
	}
	
	@PostMapping("house/{houseId}/favorite/create")
	public String create(@PathVariable(name = "houseId") Integer houseId,
						 @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
						 BindingResult bindingResult,
						 RedirectAttributes redirectAttributes,
						 Model model) 
	{
		House house = houseRepository.getReferenceById(houseId);
		User user = userDetailsImpl.getUser();
		
		if(bindingResult.hasErrors()) {
			return "houses/{houseId}";
		}
		
		favoriteService.create(house, user);
		
		return "redirect:/house/{houseId}";
	}
	
	@PostMapping("house/{houseId}/favorite/{favoriteId}/delete")
	public String delete(@PathVariable(name = "favoriteId") Integer favoriteId, RedirectAttributes redirectAttributes) {
		favoriteRepository.deleteById(favoriteId);
		
		redirectAttributes.addFlashAttribute("successMessage", "お気に入りを削除しました。");
		
		return "redirect:/house/{houseId}";
	}
	
	
}
