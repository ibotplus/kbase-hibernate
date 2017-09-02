/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.one.eager;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.eastrobot.one.cascade.entity.TeacherCascade;
import com.eastrobot.one.eager.entity.StudentEager;
import com.eastrobot.one.eager.entity.TeacherEager;
import com.eastrobot.one.eager.repository.TeacherRepository;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年8月5日 上午11:03:37
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TeacherRepositoryTests {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass()); 
	
	@Resource
	private TeacherRepository eagerTeacherRepository;
	
	@Test
	public void createTeacher(){
		//Hibernate: insert into t_teacher (age, name, id) values (?, ?, ?)
		TeacherEager teacher = new TeacherEager();
		teacher.setName("詹老师");
		teacher.setAge(32);
		
		eagerTeacherRepository.save(teacher);
	}
	
	@Test
	public void findOne(){
//		Hibernate: select teachereag0_.id as id1_1_0_, teachereag0_.age as age2_1_0_, teachereag0_.name as name3_1_0_, student1_.teacher_id as teacher_4_0_1_, student1_.id as id1_0_1_, student1_.id as id1_0_2_, student1_.age as age2_0_2_, student1_.name as name3_0_2_, student1_.teacher_id as teacher_4_0_2_ from t_teacher teachereag0_ left outer join t_student student1_ on teachereag0_.id=student1_.teacher_id where teachereag0_.id=?
		TeacherEager teacher = eagerTeacherRepository.findOne("4028ed815db690d4015db690dae20000");
		LOGGER.debug("{} 的年龄是 {}", teacher.getName(), teacher.getAge());
	}
	
	@Test
	public void updateTeacher(){
//		Hibernate: select teachereag0_.id as id1_1_0_, teachereag0_.age as age2_1_0_, teachereag0_.name as name3_1_0_, student1_.teacher_id as teacher_4_0_1_, student1_.id as id1_0_1_, student1_.id as id1_0_2_, student1_.age as age2_0_2_, student1_.name as name3_0_2_, student1_.teacher_id as teacher_4_0_2_ from t_teacher teachereag0_ left outer join t_student student1_ on teachereag0_.id=student1_.teacher_id where teachereag0_.id=?

//		Hibernate: select teachereag0_.id as id1_1_1_, teachereag0_.age as age2_1_1_, teachereag0_.name as name3_1_1_, student1_.teacher_id as teacher_4_0_3_, student1_.id as id1_0_3_, student1_.id as id1_0_0_, student1_.age as age2_0_0_, student1_.name as name3_0_0_, student1_.teacher_id as teacher_4_0_0_ from t_teacher teachereag0_ left outer join t_student student1_ on teachereag0_.id=student1_.teacher_id where teachereag0_.id=?
//		Hibernate: insert into t_student (age, name, teacher_id, id) values (?, ?, ?, ?)
//		Hibernate: insert into t_student (age, name, teacher_id, id) values (?, ?, ?, ?)
//		Hibernate: insert into t_student (age, name, teacher_id, id) values (?, ?, ?, ?)
//		Hibernate: update t_teacher set age=?, name=? where id=?
		TeacherEager teacher = eagerTeacherRepository.findOne("4028ed815db690d4015db690dae20000");
		if (teacher!=null){
			teacher.setAge(31);
			
			//设置学生
			Set<StudentEager> set = new HashSet<StudentEager>();
			StudentEager s0 = new StudentEager("占钊", 6, teacher);
			set.add(s0);
			StudentEager s1 = new StudentEager("ekozhan", 16, teacher);
			set.add(s1);
			StudentEager s2 = new StudentEager("zhanzhao", 21, teacher);
			set.add(s2);
			
			teacher.setStudent(set);
			eagerTeacherRepository.save(teacher);
		}
	}
	
	@Test
	public void findByName(){
//		Hibernate: select teachereag0_.id as id1_1_, teachereag0_.age as age2_1_, teachereag0_.name as name3_1_ from t_teacher teachereag0_ where teachereag0_.name=?
//		Hibernate: select student0_.teacher_id as teacher_4_0_0_, student0_.id as id1_0_0_, student0_.id as id1_0_1_, student0_.age as age2_0_1_, student0_.name as name3_0_1_, student0_.teacher_id as teacher_4_0_1_ from t_student student0_ where student0_.teacher_id=?
		TeacherEager teacher = eagerTeacherRepository.findByName("詹老师");
		if (teacher!=null){
			LOGGER.debug("{} 的年龄是 {} 岁", teacher.getName(), teacher.getAge());
			LOGGER.debug("{} 的 学生人数是 {} 人", teacher.getName(), teacher.getStudent().size());
		}
	}
	
	/**
	 * 问题：hibernate 一对多，能否通过操作一的一方，做到只删除多的一方
	 * @author eko.zhan at 2017年8月19日 上午9:05:47
	 */
	@Test
	public void deleteStudent(){
		TeacherEager teacher = eagerTeacherRepository.findByName("詹老师");
		if (teacher!=null){
			LOGGER.debug("{} 的 学生人数是 {}", teacher.getName(), teacher.getStudent().size());
			teacher.getStudent().forEach(student -> {student.setTeacher(null);});
			teacher.setStudent(null);
			LOGGER.debug("{} 的 学生人数是 {}", teacher.getName(), teacher.getStudent()==null?0:teacher.getStudent().size());
			
			eagerTeacherRepository.save(teacher);
		}else{
			LOGGER.debug("teacher is not exsit");
		}
	}
	
	@Test
	public void delete(){
//		Hibernate: select teachereag0_.id as id1_1_, teachereag0_.age as age2_1_, teachereag0_.name as name3_1_ from t_teacher teachereag0_ where teachereag0_.name=?
//		Hibernate: select student0_.teacher_id as teacher_4_0_0_, student0_.id as id1_0_0_, student0_.id as id1_0_1_, student0_.age as age2_0_1_, student0_.name as name3_0_1_, student0_.teacher_id as teacher_4_0_1_ from t_student student0_ where student0_.teacher_id=?

//		Hibernate: select teachereag0_.id as id1_1_1_, teachereag0_.age as age2_1_1_, teachereag0_.name as name3_1_1_, student1_.teacher_id as teacher_4_0_3_, student1_.id as id1_0_3_, student1_.id as id1_0_0_, student1_.age as age2_0_0_, student1_.name as name3_0_0_, student1_.teacher_id as teacher_4_0_0_ from t_teacher teachereag0_ left outer join t_student student1_ on teachereag0_.id=student1_.teacher_id where teachereag0_.id=?
//		Hibernate: delete from t_student where id=?
//		Hibernate: delete from t_student where id=?
//		Hibernate: delete from t_student where id=?
//		Hibernate: delete from t_teacher where id=?
		TeacherEager teacher = eagerTeacherRepository.findByName("詹老师");
		eagerTeacherRepository.delete(teacher);
	}
	
	@Test
	public void findAll(){
//		Hibernate: select teachereag0_.id as id1_1_, teachereag0_.age as age2_1_, teachereag0_.name as name3_1_ from t_teacher teachereag0_
//		Hibernate: select student0_.teacher_id as teacher_4_0_0_, student0_.id as id1_0_0_, student0_.id as id1_0_1_, student0_.age as age2_0_1_, student0_.name as name3_0_1_, student0_.teacher_id as teacher_4_0_1_ from t_student student0_ where student0_.teacher_id=?
		Iterable<TeacherEager> itr = eagerTeacherRepository.findAll();
		//输出学生按照 name desc 排序
		itr.forEach(teacher -> {LOGGER.debug("{} 的学生共 {} 名，分别是 ", teacher.getName(), teacher.getStudent().size()); teacher.getStudent().forEach(student -> LOGGER.debug(student.getName()));});
	}
}
