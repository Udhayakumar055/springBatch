package com.springbatch.ReadFileFromDatabaseAndWriteToCsv.entity;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

//@Component
//@Entity
public class Student {
	
	
	private Long id;
	
	private String name;
	
	private String dept;
	
	private Long phoneNumber;
	
	

	public Student(Long id, String name, String dept, Long phoneNumber) {
		super();
		this.id = id;
		this.name = name;
		this.dept = dept;
		this.phoneNumber = phoneNumber;
	}
	

	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
