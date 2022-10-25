package com.todo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ToDoRestController {

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	TasksRepo taskRepo;

	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@GetMapping("/")
	public String index() {
		String s = "<br><center><h1>Application is running but it is only back end.</center></h1><br><br>"
				+ "<br>(Using Basic Auth)"
				+ "<br>@PostMapping(\"/newUser\") with User json input containing \"name\",\"email\" and \"password\".\"\r\n"
				+ "<br>@PutMapping(\"/change_pass\") to change User's Password, password should be passed in raw text format."
				+ "<br>@PostMapping(\"/new\") with Task json input containing \"date\",\"time\" and \"userremarks\".\"\r\n"
				+ "<br>@PutMapping(\"/update\") with Task json input containing \\\"date\\\",\\\"time\\\" and \\\"userremarks\\\".\\\"\\r to update a task."
				+ "<br>@DeleteMapping(\"/delete\") with Task json input containing \\\"date\\\",\\\"time\\\" and \\\"userremarks\\\".\\\"\\r to delete a task."
				+ "<br>@GetMapping(\"/myTasks\") to see all own task."
				+ "<br>@GetMapping(\"/myTasks/{date}\") to see all tasks on a particular date. Date format is YYYY-MM-DD."
				+ "<br>@GetMapping(\"/old\") to see old tasks of own."
				+ "<br>@GetMapping(\"/upcoming\") to see upcoming tasks of own."
				+ "<br>@GetMapping(\"/logout\") to logout.";
		return s;
	}
	
	
	@PostMapping("/newUser")
	public String newuser(@RequestBody Users user) {
		try {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userRepo.save(user);
			return user.toString()+" added.";
		} catch (Exception e) {
			System.out.println(e);
			return user.toString()+" is not valid.";
		}
	}
	
	@PutMapping("/change_pass")
	public String change_pass(@RequestBody String pass) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String email = auth.getName();
			Users temp = userRepo.findByEmail(email);
			temp.setPassword(passwordEncoder.encode(pass));
			userRepo.save(temp);
			return "Password changed.";
			}
			catch(Exception e) {
				System.out.println(e);
			}
		return "Password change e exception.";
	}
	
	@PostMapping("/new")
	public String newTask(@RequestBody Tasks task) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String email = auth.getName();
			task.setUt_id(userRepo.findByEmail(email).getUser_id());
				taskRepo.save(task);
				return task+"is Saved.";
			}
			catch(Exception e) {
			  System.out.println(e);
			  return "New Task Exception.";
			}
	}
	
	@PutMapping("/update")
	public String updateTask(@RequestBody Tasks task) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String email = auth.getName();
			int id = userRepo.findByEmail(email).getUser_id();
			if(task.getUt_id()==id) {
				taskRepo.save(task);
				return task+"is Updated.";
			}
			else {
				System.out.println("You cant change anyone else's task.");
				return task+"is not Updated.";
			}
			}
			catch(Exception e) {
			  System.out.println(e);
			  return "Delete Task Exception.";
			}
	}
	
	@DeleteMapping("/delete")
	public String delete(@RequestBody Tasks task) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String email = auth.getName();
			int id = userRepo.findByEmail(email).getUser_id();
			if(task.getUt_id()==id) {
				taskRepo.delete(task);
				return task+"is Deleted.";
			}
			else {
				System.out.println("You cant change anyone else's task.");
				return task+"is not Deleted.";
			}
			}
			catch(Exception e) {
			  System.out.println(e);
			  return "Delete Task Exception.";
			}
	}
	
	@GetMapping("/myTasks")
	public List<Tasks> myTask() {
		List<Tasks> myList = new ArrayList<Tasks>();
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			int id = userRepo.findByEmail(auth.getName()).getUser_id();
			return taskRepo.findAllById(id);
			}
			catch(Exception e) {
			System.out.println(e);
			  return myList;
			}
	}
	
	@GetMapping("/myTasks/{date}")
	public List<Tasks> myTasks(@PathVariable Date date){
		List<Tasks> myList = new ArrayList<Tasks>();
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			int id = userRepo.findByEmail(auth.getName()).getUser_id();
			return taskRepo.findAllByIdDate(id,date);
			}
			catch(Exception e) {
			  System.out.println(e);
			  return myList;
			}
	}
	
	@GetMapping("/old")
	public List<Tasks> old() {
		List<Tasks> myList = new ArrayList<Tasks>();
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			int id = userRepo.findByEmail(auth.getName()).getUser_id();
			return taskRepo.findPast(id);
			}
			catch(Exception e) {
			  System.out.println(e);
			  return myList;
			}
	}
	
	@GetMapping("/upcoming")
	public List<Tasks> upcoming() {
		List<Tasks> myList = new ArrayList<Tasks>();
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			int id = userRepo.findByEmail(auth.getName()).getUser_id();
			return taskRepo.findFuture(id);
			}
			catch(Exception e) {
			  System.out.println(e);
			  return myList;
			}
	}
	
//	@GetMapping("/alltasks")
//	public List<Tasks> alltasks() {
//		return taskRepo.findAll();
//	}
//	
	@GetMapping("/allusers")
	public List<Users> allusers() {
		return userRepo.findAll();
	}
}
