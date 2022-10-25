package com.todo;


import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="Tasks")
public class Tasks {
	
	Tasks() {
		
	}

	@Id
	@Column(name="task_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int task_id;
	
	public int getTask_id() {
		return task_id;
	}

	public void setTask_id(int task_id) {
		this.task_id = task_id;
	}

	@Column(name="date")
	private Date date;
	
	@Column(name="time")
	private Time time;
	
	@Column(name="userremarks")
	private String userremarks;
	
	//user task id
	@Column(name="ut_id")
	private int ut_id;

	

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public String getUserremarks() {
		return userremarks;
	}

	public void setUserremarks(String userremarks) {
		this.userremarks = userremarks;
	}

	public int getUt_id() {
		return ut_id;
	}

	public void setUt_id(int ut_id) {
		this.ut_id = ut_id;
	}


	public Tasks(Date date, Time time, String userremarks) {
		super();
		this.date = date;
		this.time = time;
		this.userremarks = userremarks;
	}

	@Override
	public String toString() {
		return "Tasks [task_id=" + task_id + ", date=" + date + ", time=" + time + ", userremarks=" + userremarks
				+ ", ut_id=" + ut_id + "]";
	}


}
