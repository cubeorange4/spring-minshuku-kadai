package com.example.samuraitravel.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.samuraitravel.entity.Review;
import com.example.samuraitravel.entity.User;
import com.example.samuraitravel.form.ReviewEditForm;
import com.example.samuraitravel.form.ReviewRegisterForm;
import com.example.samuraitravel.repository.HouseRepository;
import com.example.samuraitravel.repository.ReviewRepository;
import com.example.samuraitravel.security.UserDetailsImpl;
import com.example.samuraitravel.service.ReviewService;

@Controller
@RequestMapping("/houses/{houseId}/review")
public class ReviewController {
	private final ReviewRepository reviewRepository;
	private final ReviewService reviewService;
	private final HouseRepository houseRepository;
	
	public ReviewController(ReviewRepository reviewRepository, ReviewService reviewService, HouseRepository houseRepository) {
		this.reviewRepository = reviewRepository;
		this.reviewService = reviewService;
		this.houseRepository = houseRepository;
	}
	
	@GetMapping
	public String index(@PathVariable(name = "houseId") Integer houseId, @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable, Model model) {
		Page<Review> reviewPage = reviewRepository.findAll(pageable);
		
		model.addAttribute("reviewPage", reviewPage);
		
		return "houses/"+houseId+"/review/index";
	}
	
	@GetMapping("/register")
	public String register(@PathVariable(name = "houseId") Integer houseId, Model model) {
		model.addAttribute("reviewRegisterForm", new ReviewRegisterForm());
		return "houses/"+houseId+"/review/register";
	}
	
	@PostMapping("/create")
	public String create(@PathVariable(name = "houseId") Integer houseId,
						   @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
						   @ModelAttribute @Validated ReviewRegisterForm reviewRegisterForm,
						   BindingResult bindindResult,
						   RedirectAttributes redirectAttributeds,
					       Model model) {
		if(bindindResult.hasErrors()) {
			return "houses/"+houseId+"/review/register";
		}
		User user = userDetailsImpl.getUser();
		
		reviewService.create(user, houseId, reviewRegisterForm);
		redirectAttributeds.addFlashAttribute("seccessMessage", "レビューを投稿しました");
		
		return "redirect:/houses/"+houseId+"/review";
	}
	
	@PostMapping("/{reviewId}/edit")
	public String edit(@PathVariable(name = "houseId") Integer houseId,@PathVariable(name = "reviewId") Integer reviewId, Model model) {
		Review review = reviewRepository.getReferenceById(reviewId);
		ReviewEditForm reviewEditForm = new ReviewEditForm(review.getHouseId(), review.getContent(), review.getScore());
		
		model.addAttribute("reviewEditForm", reviewEditForm);
		
		return "houses/review/"+houseId+"/edit";
	}
	
	@PostMapping("/{reviewId}/update")
	public String update(@PathVariable(name = "houseId")Integer houseId,
			             @PathVariable(name = "reviewId")Integer reviewId,
			             @ModelAttribute @Validated ReviewEditForm reviewEditForm,
			             BindingResult bindingResult,
			             RedirectAttributes redirectAttributes,
			             Model model) {
		if(bindingResult.hasErrors()) {
			return "/houses/"+houseId+"/review/edit";
		}
		reviewService.update(reviewEditForm);
		redirectAttributes.addFlashAttribute("seccessMessage", "レビューを編集しました");
		
		return "redirect:/houses/review";
	}
	
	@PostMapping("/{reviewId}/delete")
	public String delete(@PathVariable(name = "reviewId")Integer reviewId, RedirectAttributes redirectAttributes) {
		reviewRepository.deleteById(reviewId);
		redirectAttributes.addFlashAttribute("seccessMessage", "レビューを削除しました");
		
		return  "redirect:/houses/review";
	}
}
