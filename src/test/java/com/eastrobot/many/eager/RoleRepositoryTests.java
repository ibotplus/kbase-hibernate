/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.many.eager;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.eastrobot.many.eager.entity.RoleEager;
import com.eastrobot.many.eager.entity.UserEager;
import com.eastrobot.many.eager.repository.RoleRepository;
import com.eastrobot.many.eager.repository.UserRepository;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年8月19日 上午10:43:22
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleRepositoryTests {
//	Hibernate: insert into t_role (name, remark, id) values (?, ?, ?)
//	Hibernate: insert into t_user (age, name, id) values (?, ?, ?)
//	Hibernate: insert into t_user (age, name, id) values (?, ?, ?)
//	Hibernate: insert into t_user (age, name, id) values (?, ?, ?)
//	Hibernate: insert into t_role_user_mapping (role_id, user_id) values (?, ?)
//	Hibernate: insert into t_role_user_mapping (role_id, user_id) values (?, ?)
//	Hibernate: insert into t_role_user_mapping (role_id, user_id) values (?, ?)
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Resource
	private RoleRepository eagerRoleRepository;
	@Resource
	private UserRepository eagerUserRepository;
	
	@Test
	public void createRole(){
		RoleEager role0 = new RoleEager("系统管理员", "");
		
		UserEager user0 = new UserEager("占钊", 18);
		UserEager user1 = new UserEager("李彬", 28);
		UserEager user2 = new UserEager("刘万鹏", 19);
		
		Set<UserEager> users = new HashSet<UserEager>();
		users.add(user0);
		users.add(user1);
		users.add(user2);
		
		role0.setUsers(users);
		
		eagerRoleRepository.save(role0);
	}
	
	@Test
	public void updateUser(){
/*
Hibernate: select usereager0_.id as id1_4_0_, usereager0_.age as age2_4_0_, usereager0_.name as name3_4_0_, roles1_.user_id as user_id1_1_1_, roleeager2_.id as role_id2_1_1_, roleeager2_.id as id1_0_2_, roleeager2_.name as name2_0_2_, roleeager2_.remark as remark3_0_2_ from t_user usereager0_ left outer join t_role_user_mapping roles1_ on usereager0_.id=roles1_.user_id left outer join t_role roleeager2_ on roles1_.role_id=roleeager2_.id where usereager0_.id=?
Hibernate: select users0_.role_id as role_id2_1_0_, users0_.user_id as user_id1_1_0_, usereager1_.id as id1_4_1_, usereager1_.age as age2_4_1_, usereager1_.name as name3_4_1_ from t_role_user_mapping users0_ inner join t_user usereager1_ on users0_.user_id=usereager1_.id where users0_.role_id=?
Hibernate: select roles0_.user_id as user_id1_1_0_, roles0_.role_id as role_id2_1_0_, roleeager1_.id as id1_0_1_, roleeager1_.name as name2_0_1_, roleeager1_.remark as remark3_0_1_ from t_role_user_mapping roles0_ inner join t_role roleeager1_ on roles0_.role_id=roleeager1_.id where roles0_.user_id=?
Hibernate: select roles0_.user_id as user_id1_1_0_, roles0_.role_id as role_id2_1_0_, roleeager1_.id as id1_0_1_, roleeager1_.name as name2_0_1_, roleeager1_.remark as remark3_0_1_ from t_role_user_mapping roles0_ inner join t_role roleeager1_ on roles0_.role_id=roleeager1_.id where roles0_.user_id=?
Hibernate: select usereager0_.id as id1_4_1_, usereager0_.age as age2_4_1_, usereager0_.name as name3_4_1_, roles1_.user_id as user_id1_1_3_, roleeager2_.id as role_id2_1_3_, roleeager2_.id as id1_0_0_, roleeager2_.name as name2_0_0_, roleeager2_.remark as remark3_0_0_ from t_user usereager0_ left outer join t_role_user_mapping roles1_ on usereager0_.id=roles1_.user_id left outer join t_role roleeager2_ on roles1_.role_id=roleeager2_.id where usereager0_.id=?
Hibernate: select users0_.role_id as role_id2_1_0_, users0_.user_id as user_id1_1_0_, usereager1_.id as id1_4_1_, usereager1_.age as age2_4_1_, usereager1_.name as name3_4_1_ from t_role_user_mapping users0_ inner join t_user usereager1_ on users0_.user_id=usereager1_.id where users0_.role_id=?
Hibernate: select roles0_.user_id as user_id1_1_0_, roles0_.role_id as role_id2_1_0_, roleeager1_.id as id1_0_1_, roleeager1_.name as name2_0_1_, roleeager1_.remark as remark3_0_1_ from t_role_user_mapping roles0_ inner join t_role roleeager1_ on roles0_.role_id=roleeager1_.id where roles0_.user_id=?
Hibernate: select roles0_.user_id as user_id1_1_0_, roles0_.role_id as role_id2_1_0_, roleeager1_.id as id1_0_1_, roleeager1_.name as name2_0_1_, roleeager1_.remark as remark3_0_1_ from t_role_user_mapping roles0_ inner join t_role roleeager1_ on roles0_.role_id=roleeager1_.id where roles0_.user_id=?
Hibernate: insert into t_role (name, remark, id) values (?, ?, ?)
Hibernate: insert into t_role_user_mapping (user_id, role_id) values (?, ?)
*/
		UserEager user = eagerUserRepository.findOne("4028ed815df94288015df94290bc0001");
		RoleEager role0 = new RoleEager("甲方座席", "");
		
		Set<RoleEager> roles = user.getRoles();
		roles.add(role0);
		user.setRoles(roles);
		
		eagerUserRepository.save(user);
		
	}
	
	@Test
	public void findByRole(){
		RoleEager role = eagerRoleRepository.findOne("4028ed815df94288015df942909d0000");
		LOGGER.debug("{} 下面的用户数是 {}", role.getName(), role.getUsers().size());
		role.getUsers().forEach(user -> {LOGGER.debug(user.getName());});
	}
	
	@Test
	public void findByUser(){
		UserEager user = eagerUserRepository.findOne("4028ed815df94288015df94290bc0003");
		LOGGER.debug("用户 {} 角色数是 {}", user.getName(), user.getRoles().size());
		user.getRoles().forEach(role -> {LOGGER.debug(role.getName());});
	}
	
	@Test
	public void deleteByRole(){
/* 请思考为什么？
Hibernate: select roleeager0_.id as id1_0_0_, roleeager0_.name as name2_0_0_, roleeager0_.remark as remark3_0_0_, users1_.role_id as role_id2_1_1_, usereager2_.id as user_id1_1_1_, usereager2_.id as id1_4_2_, usereager2_.age as age2_4_2_, usereager2_.name as name3_4_2_ from t_role roleeager0_ left outer join t_role_user_mapping users1_ on roleeager0_.id=users1_.role_id left outer join t_user usereager2_ on users1_.user_id=usereager2_.id where roleeager0_.id=?
Hibernate: select roles0_.user_id as user_id1_1_0_, roles0_.role_id as role_id2_1_0_, roleeager1_.id as id1_0_1_, roleeager1_.name as name2_0_1_, roleeager1_.remark as remark3_0_1_ from t_role_user_mapping roles0_ inner join t_role roleeager1_ on roles0_.role_id=roleeager1_.id where roles0_.user_id=?
Hibernate: select roles0_.user_id as user_id1_1_0_, roles0_.role_id as role_id2_1_0_, roleeager1_.id as id1_0_1_, roleeager1_.name as name2_0_1_, roleeager1_.remark as remark3_0_1_ from t_role_user_mapping roles0_ inner join t_role roleeager1_ on roles0_.role_id=roleeager1_.id where roles0_.user_id=?
Hibernate: select roles0_.user_id as user_id1_1_0_, roles0_.role_id as role_id2_1_0_, roleeager1_.id as id1_0_1_, roleeager1_.name as name2_0_1_, roleeager1_.remark as remark3_0_1_ from t_role_user_mapping roles0_ inner join t_role roleeager1_ on roles0_.role_id=roleeager1_.id where roles0_.user_id=?
Hibernate: select users0_.role_id as role_id2_1_0_, users0_.user_id as user_id1_1_0_, usereager1_.id as id1_4_1_, usereager1_.age as age2_4_1_, usereager1_.name as name3_4_1_ from t_role_user_mapping users0_ inner join t_user usereager1_ on users0_.user_id=usereager1_.id where users0_.role_id=?
Hibernate: select roleeager0_.id as id1_0_1_, roleeager0_.name as name2_0_1_, roleeager0_.remark as remark3_0_1_, users1_.role_id as role_id2_1_3_, usereager2_.id as user_id1_1_3_, usereager2_.id as id1_4_0_, usereager2_.age as age2_4_0_, usereager2_.name as name3_4_0_ from t_role roleeager0_ left outer join t_role_user_mapping users1_ on roleeager0_.id=users1_.role_id left outer join t_user usereager2_ on users1_.user_id=usereager2_.id where roleeager0_.id=?
Hibernate: select roles0_.user_id as user_id1_1_0_, roles0_.role_id as role_id2_1_0_, roleeager1_.id as id1_0_1_, roleeager1_.name as name2_0_1_, roleeager1_.remark as remark3_0_1_ from t_role_user_mapping roles0_ inner join t_role roleeager1_ on roles0_.role_id=roleeager1_.id where roles0_.user_id=?
Hibernate: select roles0_.user_id as user_id1_1_0_, roles0_.role_id as role_id2_1_0_, roleeager1_.id as id1_0_1_, roleeager1_.name as name2_0_1_, roleeager1_.remark as remark3_0_1_ from t_role_user_mapping roles0_ inner join t_role roleeager1_ on roles0_.role_id=roleeager1_.id where roles0_.user_id=?
Hibernate: select roles0_.user_id as user_id1_1_0_, roles0_.role_id as role_id2_1_0_, roleeager1_.id as id1_0_1_, roleeager1_.name as name2_0_1_, roleeager1_.remark as remark3_0_1_ from t_role_user_mapping roles0_ inner join t_role roleeager1_ on roles0_.role_id=roleeager1_.id where roles0_.user_id=?
Hibernate: select users0_.role_id as role_id2_1_0_, users0_.user_id as user_id1_1_0_, usereager1_.id as id1_4_1_, usereager1_.age as age2_4_1_, usereager1_.name as name3_4_1_ from t_role_user_mapping users0_ inner join t_user usereager1_ on users0_.user_id=usereager1_.id where users0_.role_id=?
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
		RoleEager role = eagerRoleRepository.findOne("4028ed815df94288015df942909d0000");
		eagerRoleRepository.delete(role);
	}
}
