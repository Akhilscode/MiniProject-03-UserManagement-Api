package com.usrmgmt.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USER_MASTER")
public class UserEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	private String fullName;
	private String email;
	private String gender;
	private Long ssn;
	private Long mobile;
	private LocalDate dob;
	private String createdBy;
	private String updatedBy;
	@Column( updatable = false)
	@CreationTimestamp
	private LocalDate createdDate;
	@Column( insertable = false)
	@UpdateTimestamp
	private LocalDate updatedDate;
	private String password;
	private String activeStatus;
	
}
	
