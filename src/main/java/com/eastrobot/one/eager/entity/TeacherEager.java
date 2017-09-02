/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.one.eager.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Where;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年8月5日 上午10:12:18
 * @version 1.0
 */
@Entity
@Table(name="T_TEACHER")
@Getter
@Setter
public class TeacherEager implements Serializable{

	@Id
	@GenericGenerator(name="systemUUID", strategy="uuid")
	@GeneratedValue(generator="systemUUID")
	private String id;
	private String name;
	private Integer age;
	//一对多需要配置 mappedBy 
	//The field that owns the relationship. Required unless the relationship is unidirectional
	@OneToMany(mappedBy="teacher", fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@OrderBy(clause="name desc")	//排序约束
//	@Where(clause="name like '%eko%'") //用于条件判断，在一些逻辑删除的表中使用广泛，比如 DEL_FLAG=0，clause 中配置的字段是数据库字段
	private Set<StudentEager> student;
}
