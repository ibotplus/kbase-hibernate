/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.one.lazy.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年8月5日 上午10:12:18
 * @version 1.0
 */
@Entity
@Table(name="T_TEACHER")
@Getter
@Setter
public class TeacherLazy implements Serializable{

	@Id
	@GenericGenerator(name="systemUUID", strategy="uuid")
	@GeneratedValue(generator="systemUUID")
	private String id;
	private String name;
	private Integer age;
	//一对多需要配置 mappedBy，如果没有配置，将会生成一张新的中间关联表类似于 manytomany
	//The field that owns the relationship. Required unless the relationship is unidirectional
	//Lazy的引入是为了性能提高，如果要获取teacher对象能直接访问student，需要用到 fetch
	@OneToMany(mappedBy="teacher", fetch=FetchType.LAZY, cascade={CascadeType.MERGE, CascadeType.REMOVE})
	private Set<StudentLazy> student;
}
