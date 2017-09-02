/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.many.eager.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年8月19日 上午9:43:05
 * @version 1.0
 */
@Entity
@Table(name="T_ROLE")
@Getter
@Setter
public class RoleEager {

	@Id
	@GenericGenerator(name="systemUUID", strategy="uuid")
	@GeneratedValue(generator="systemUUID")
	private String id;
	private String name;
	private String remark;
	@ManyToMany(targetEntity=UserEager.class, fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(
		name="T_ROLE_USER_MAPPING",
		joinColumns={@JoinColumn(name="ROLE_ID")},
		inverseJoinColumns={@JoinColumn(name="USER_ID")}
	)
	private Set<UserEager> users;
	
	public RoleEager(){}
	
	public RoleEager(String name, String remark){
		this.name = name;
		this.remark = remark;
	}
}
