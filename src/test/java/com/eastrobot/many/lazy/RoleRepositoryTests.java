/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.many.lazy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.eastrobot.many.lazy.entity.RoleLazy;
import com.eastrobot.many.lazy.entity.UserLazy;
import com.eastrobot.many.lazy.repository.RoleRepository;
import com.eastrobot.many.lazy.repository.UserRepository;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年8月19日 上午10:43:22
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleRepositoryTests {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Resource
	private RoleRepository lazyRoleRepository;
	@Resource
	private UserRepository lazyUserRepository;
	
	@Test
	public void createRole(){
//		Hibernate: insert into t_role (name, remark, id) values (?, ?, ?)
//		Hibernate: insert into t_user (age, name, id) values (?, ?, ?)
//		Hibernate: insert into t_user (age, name, id) values (?, ?, ?)
//		Hibernate: insert into t_user (age, name, id) values (?, ?, ?)
//		Hibernate: insert into t_role_user_mapping (role_id, user_id) values (?, ?)
//		Hibernate: insert into t_role_user_mapping (role_id, user_id) values (?, ?)
//		Hibernate: insert into t_role_user_mapping (role_id, user_id) values (?, ?)
		RoleLazy role0 = new RoleLazy("系统管理员", "");
		
		UserLazy user0 = new UserLazy("占钊", 18);
		UserLazy user1 = new UserLazy("李彬", 28);
		UserLazy user2 = new UserLazy("刘万鹏", 19);
		
		Set<UserLazy> users = new HashSet<UserLazy>();
		users.add(user0);
		users.add(user1);
		users.add(user2);
		
		role0.setUsers(users);
		
		lazyRoleRepository.save(role0);
	}
	
	@Test
	public void updateUser(){
//		Hibernate: select userlazy0_.id as id1_4_0_, rolelazy2_.id as id1_0_1_, userlazy0_.age as age2_4_0_, userlazy0_.name as name3_4_0_, rolelazy2_.name as name2_0_1_, rolelazy2_.remark as remark3_0_1_, roles1_.user_id as user_id1_1_0__, roles1_.role_id as role_id2_1_0__ from t_user userlazy0_ left outer join t_role_user_mapping roles1_ on userlazy0_.id=roles1_.user_id left outer join t_role rolelazy2_ on roles1_.role_id=rolelazy2_.id where userlazy0_.name=? order by rolelazy2_.name asc
//		Hibernate: select userlazy0_.id as id1_4_1_, userlazy0_.age as age2_4_1_, userlazy0_.name as name3_4_1_, roles1_.user_id as user_id1_1_3_, rolelazy2_.id as role_id2_1_3_, rolelazy2_.id as id1_0_0_, rolelazy2_.name as name2_0_0_, rolelazy2_.remark as remark3_0_0_ from t_user userlazy0_ left outer join t_role_user_mapping roles1_ on userlazy0_.id=roles1_.user_id left outer join t_role rolelazy2_ on roles1_.role_id=rolelazy2_.id where userlazy0_.id=?
//		Hibernate: insert into t_role (name, remark, id) values (?, ?, ?)
//		Hibernate: insert into t_role_user_mapping (user_id, role_id) values (?, ?)
		List<UserLazy> list = lazyUserRepository.findByUserName("占钊");
		if (list.size()>0){
			UserLazy user = list.get(0);
			RoleLazy role0 = new RoleLazy("甲方座席", "");
			
			Set<RoleLazy> roles = user.getRoles();
			roles.add(role0);
			user.setRoles(roles);
			
			lazyUserRepository.save(user);
		}
	}
	
	@Test
	public void findByRole(){
		List<RoleLazy> list = lazyRoleRepository.findByRoleName("系统管理员");
		LOGGER.debug(list.size() + "");
		if (list.size()>0){
			RoleLazy role = list.get(0);
			LOGGER.debug("{} 下面的用户数是 {}", role.getName(), role.getUsers().size());
			role.getUsers().forEach(user -> {LOGGER.debug(user.getName());});
		}
	}
	
	@Test
	public void findByUser(){
		List<UserLazy> list = lazyUserRepository.findByUserName("占钊");
		LOGGER.debug(list.size() + "");
		if (list.size()>0){
			UserLazy user = list.get(0);
			LOGGER.debug("用户 {} 角色数是 {}", user.getName(), user.getRoles().size());
			user.getRoles().forEach(role -> {LOGGER.debug(role.getName());});
		}
	}
	
	@Test
	public void deleteByRole(){
/* 请思考为什么？
Hibernate: select RoleLazy0_.id as id1_0_0_, RoleLazy0_.name as name2_0_0_, RoleLazy0_.remark as remark3_0_0_, users1_.role_id as role_id2_1_1_, UserLazy2_.id as user_id1_1_1_, UserLazy2_.id as id1_4_2_, UserLazy2_.age as age2_4_2_, UserLazy2_.name as name3_4_2_ from t_role RoleLazy0_ left outer join t_role_user_mapping users1_ on RoleLazy0_.id=users1_.role_id left outer join t_user UserLazy2_ on users1_.user_id=UserLazy2_.id where RoleLazy0_.id=?
Hibernate: select roles0_.user_id as user_id1_1_0_, roles0_.role_id as role_id2_1_0_, RoleLazy1_.id as id1_0_1_, RoleLazy1_.name as name2_0_1_, RoleLazy1_.remark as remark3_0_1_ from t_role_user_mapping roles0_ inner join t_role RoleLazy1_ on roles0_.role_id=RoleLazy1_.id where roles0_.user_id=?
Hibernate: select roles0_.user_id as user_id1_1_0_, roles0_.role_id as role_id2_1_0_, RoleLazy1_.id as id1_0_1_, RoleLazy1_.name as name2_0_1_, RoleLazy1_.remark as remark3_0_1_ from t_role_user_mapping roles0_ inner join t_role RoleLazy1_ on roles0_.role_id=RoleLazy1_.id where roles0_.user_id=?
Hibernate: select roles0_.user_id as user_id1_1_0_, roles0_.role_id as role_id2_1_0_, RoleLazy1_.id as id1_0_1_, RoleLazy1_.name as name2_0_1_, RoleLazy1_.remark as remark3_0_1_ from t_role_user_mapping roles0_ inner join t_role RoleLazy1_ on roles0_.role_id=RoleLazy1_.id where roles0_.user_id=?
Hibernate: select users0_.role_id as role_id2_1_0_, users0_.user_id as user_id1_1_0_, UserLazy1_.id as id1_4_1_, UserLazy1_.age as age2_4_1_, UserLazy1_.name as name3_4_1_ from t_role_user_mapping users0_ inner join t_user UserLazy1_ on users0_.user_id=UserLazy1_.id where users0_.role_id=?
Hibernate: select RoleLazy0_.id as id1_0_1_, RoleLazy0_.name as name2_0_1_, RoleLazy0_.remark as remark3_0_1_, users1_.role_id as role_id2_1_3_, UserLazy2_.id as user_id1_1_3_, UserLazy2_.id as id1_4_0_, UserLazy2_.age as age2_4_0_, UserLazy2_.name as name3_4_0_ from t_role RoleLazy0_ left outer join t_role_user_mapping users1_ on RoleLazy0_.id=users1_.role_id left outer join t_user UserLazy2_ on users1_.user_id=UserLazy2_.id where RoleLazy0_.id=?
Hibernate: select roles0_.user_id as user_id1_1_0_, roles0_.role_id as role_id2_1_0_, RoleLazy1_.id as id1_0_1_, RoleLazy1_.name as name2_0_1_, RoleLazy1_.remark as remark3_0_1_ from t_role_user_mapping roles0_ inner join t_role RoleLazy1_ on roles0_.role_id=RoleLazy1_.id where roles0_.user_id=?
Hibernate: select roles0_.user_id as user_id1_1_0_, roles0_.role_id as role_id2_1_0_, RoleLazy1_.id as id1_0_1_, RoleLazy1_.name as name2_0_1_, RoleLazy1_.remark as remark3_0_1_ from t_role_user_mapping roles0_ inner join t_role RoleLazy1_ on roles0_.role_id=RoleLazy1_.id where roles0_.user_id=?
Hibernate: select roles0_.user_id as user_id1_1_0_, roles0_.role_id as role_id2_1_0_, RoleLazy1_.id as id1_0_1_, RoleLazy1_.name as name2_0_1_, RoleLazy1_.remark as remark3_0_1_ from t_role_user_mapping roles0_ inner join t_role RoleLazy1_ on roles0_.role_id=RoleLazy1_.id where roles0_.user_id=?
Hibernate: select users0_.role_id as role_id2_1_0_, users0_.user_id as user_id1_1_0_, UserLazy1_.id as id1_4_1_, UserLazy1_.age as age2_4_1_, UserLazy1_.name as name3_4_1_ from t_role_user_mapping users0_ inner join t_user UserLazy1_ on users0_.user_id=UserLazy1_.id where users0_.role_id=?
Hibernate: delete from t_role_user_mapping where user_id=?
Hibernate: delete from t_role_user_mapping where user_id=?
Hibernate: delete from t_role_user_mapping where user_id=?
Hibernate: delete from t_role_user_mapping where role_id=?
Hibernate: delete from t_role_user_mapping where role_id=?
Hibernate: delete from t_user where id=?
Hibernate: delete from t_role where id=?
Hibernate: delete from t_user where id=?
Hibernate: delete from t_user where id=?
Hibernate: delete from t_role where id=?
 */
		
//		List<RoleLazy> list = lazyRoleRepository.findByRoleName("系统管理员");
//		lazyRoleRepository.delete(list);
		
		
/*
 * Hibernate: select rolelazy0_.id as id1_0_0_, rolelazy0_.name as name2_0_0_, rolelazy0_.remark as remark3_0_0_ from t_role rolelazy0_ where rolelazy0_.id=?
Hibernate: select rolelazy0_.id as id1_0_1_, rolelazy0_.name as name2_0_1_, rolelazy0_.remark as remark3_0_1_, users1_.role_id as role_id2_1_3_, userlazy2_.id as user_id1_1_3_, userlazy2_.id as id1_4_0_, userlazy2_.age as age2_4_0_, userlazy2_.name as name3_4_0_ from t_role rolelazy0_ left outer join t_role_user_mapping users1_ on rolelazy0_.id=users1_.role_id left outer join t_user userlazy2_ on users1_.user_id=userlazy2_.id where rolelazy0_.id=?
Hibernate: delete from t_role_user_mapping where role_id=?
Hibernate: delete from t_role where id=?
 */
		RoleLazy role = lazyRoleRepository.findOne("4028ed815e227ebd015e227ec74b0000");
		lazyRoleRepository.delete(role);
	}
	
	@Test
	public void deleteByUser(){
		UserLazy user = lazyUserRepository.findOne("4028ed815e229c14015e229c1fa30003");
		lazyUserRepository.delete(user);
	}
}
