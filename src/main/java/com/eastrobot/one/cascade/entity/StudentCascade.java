/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.one.cascade.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年8月5日 上午10:14:14
 * @version 1.0
 */
@Entity
@Table(name="T_STUDENT")
@Getter
@Setter
public class StudentCascade implements Serializable {

	@Id
	@GenericGenerator(name="systemUUID", strategy="uuid")
	@GeneratedValue(generator="systemUUID")
	private String id;
	private String name;
	private Integer age;
	@ManyToOne(targetEntity=TeacherCascade.class)
	private TeacherCascade teacher;
	@Column(name="KEY_")
	private Integer key;
	
	public StudentCascade(){}
	
	public StudentCascade(String name, Integer age, TeacherCascade teacher){
		this.name = name;
		this.age = age;
		this.teacher = teacher;
	}
}
