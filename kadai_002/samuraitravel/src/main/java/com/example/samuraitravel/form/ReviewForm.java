package com.example.samuraitravel.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewForm {
	private Integer userName;
	
	private Integer houseId;
	
	@NotBlank
	private String content;
	
	@NotBlank
	@Min (value = 1)
	@Max (value = 5)
	private Integer score;
}
