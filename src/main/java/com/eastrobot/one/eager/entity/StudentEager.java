/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.one.eager.entity;

import java.io.Serializable;

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
//Note: 为什么不使用 lombok 的 @DATA 生成 Getter/Setter 原因在此 http://blog.csdn.net/code_du/article/details/37809567
public class StudentEager implements Serializable {

	@Id
	@GenericGenerator(name="systemUUID", strategy="uuid")
	@GeneratedValue(generator="systemUUID")
	private String id;
	private String name;
	private Integer age;
	@ManyToOne(targetEntity=TeacherEager.class)
	private TeacherEager teacher;
	
	public StudentEager(){}
	
	public StudentEager(String name, Integer age, TeacherEager teacher){
		this.name = name;
		this.age = age;
		this.teacher = teacher;
	}
}
