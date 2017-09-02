/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.one.lazy.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年8月5日 上午10:14:14
 * @version 1.0
 */
@Entity
@Table(name="T_STUDENT")
public @Data class StudentLazy implements Serializable {

	@Id
	@GenericGenerator(name="systemUUID", strategy="uuid")
	@GeneratedValue(generator="systemUUID")
	private String id;
	private String name;
	private Integer age;
	@ManyToOne(targetEntity=TeacherLazy.class)
	private TeacherLazy teacher;
	
	public StudentLazy(){}
	
	public StudentLazy(String name, Integer age, TeacherLazy teacher){
		this.name = name;
		this.age = age;
		this.teacher = teacher;
	}
}
