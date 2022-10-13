package com.usrmgmt.bindings;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	private String fullName;
	private String email;
	private String gender;
	private Long ssn;
	private Long mobile;
	private LocalDate dob;

}
