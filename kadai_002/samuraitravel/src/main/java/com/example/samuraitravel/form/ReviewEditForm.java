package com.example.samuraitravel.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewEditForm {
	
	@NotNull
	private Integer id;
	
	@NotNull
	@Min (value = 1)
	@Max (value = 5)
	private Integer score;
	
	@NotBlank
	private String content;
}