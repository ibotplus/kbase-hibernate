/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.many.lazy.entity;

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
 * @date 2017年8月19日 上午9:43:15
 * @version 1.0
 */
@Entity
@Table(name="T_USER")
@Getter
@Setter
public class UserLazy {
	
	@Id
	@GenericGenerator(name="systemUUID", strategy="uuid")
	@GeneratedValue(generator="systemUUID")
	private String id;
	private String name;
	private Integer age;
	//双向控制
	//多对多比较不好处理的就是删除，例如
//	角色管理员下面有 占钊 刘万鹏 李彬 三个用户，
//	占钊有两个角色 管理员 和 座席，
//	急加载的情况:此时如果删除角色管理员，那么会相应的删除角色下 占钊/刘万鹏/李彬三个用户，但是由于占钊对应了两个角色，又会删除掉占钊对应的其他角色，loop
	@ManyToMany(targetEntity=RoleLazy.class, fetch=FetchType.LAZY, cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinTable(
		name="T_ROLE_USER_MAPPING",
		joinColumns={@JoinColumn(name="USER_ID")},
		inverseJoinColumns={@JoinColumn(name="ROLE_ID")}
	)
	//单向控制（给用户类增加角色，然后保存不会更新关联的角色数据，比如用户占钊之前的角色是座席，增加角色管理员，此时不会自动保存管理员）
//	@ManyToMany(fetch=FetchType.LAZY, mappedBy="users")
	private Set<RoleLazy> roles;
	
	public UserLazy(){}
	
	public UserLazy(String name, Integer age){
		this.name = name;
		this.age = age;
	}
}
