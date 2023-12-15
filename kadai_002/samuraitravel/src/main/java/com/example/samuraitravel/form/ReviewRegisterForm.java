package com.example.samuraitravel.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewRegisterForm {
	
	@NotBlank
	private String content;
	
	@NotNull
	@Min (value = 1)
	@Max (value = 5)
	private Integer score;
}
