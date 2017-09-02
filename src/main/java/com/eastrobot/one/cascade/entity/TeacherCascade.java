/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.one.cascade.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.engine.internal.Cascade;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年8月5日 上午10:12:18
 * @version 1.0
 */
@Entity
@Table(name="T_TEACHER")
@Getter
@Setter
public class TeacherCascade implements Serializable{

	@Id
	@GenericGenerator(name="systemUUID", strategy="uuid")
	@GeneratedValue(generator="systemUUID")
	private String id;
	private String name;
	private Integer age;
	
	@Column(name="KEY_")
	private String key;
	//一对多需要配置 mappedBy 
	//The field that owns the relationship. Required unless the relationship is unidirectional
	/*
	 * CascadeType.ALL 
	 * CascadeType.DETACH 
	 * CascadeType.MERGE ☆
	 * CascadeType.PERSIST
	 * CascadeType.REFRESH
	 * CascadeType.REMOVE ☆
	 */
	/*
	 * 1. 先不用级联
	 * 2. CascadeType.PERSIST
	 * 3. CascadeType.REMOVE
	 */
	//@OneToMany(mappedBy="teacher", fetch=FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE})
	@OneToMany(mappedBy="teacher", fetch=FetchType.LAZY, cascade={CascadeType.MERGE})
	private Set<StudentCascade> student;
	
	@Transient
	private String course;
	
//	@Formula("(SELECT UI.USER_CHINESE_NAME FROM DEV_USER U INNER JOIN DEV_USER_INFO UI ON U.USER_INFO_ID=UI.ID WHERE U.ID=CREATE_USER )")
//	private String nameCN;
}
