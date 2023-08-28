package com.example.poi.entity;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
//	@ElementCollection
	private String userName;
	@NotEmpty(message = "invalid Email!!")
	@Column(length = 2000)
	private String userEmail;
	@Max(value = 9999999999l, message = "Phone Number cannot be more than 9999999999 !!")
	@Min(value = 6000000000l, message = "Phone Number Cannot start with `6`!!")
	private long userPhNo;
}



