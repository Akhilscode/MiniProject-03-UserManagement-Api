package com.usrmgmt.bindings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivateUser {
	
	private String email;
	private String temPassword;
	private String newPassword;
	private String confirmPassword;
	

}
