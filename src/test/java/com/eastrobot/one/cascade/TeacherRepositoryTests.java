/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.one.cascade;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.eastrobot.one.cascade.entity.StudentCascade;
import com.eastrobot.one.cascade.entity.TeacherCascade;
import com.eastrobot.one.cascade.repository.StudentRepository;
import com.eastrobot.one.cascade.repository.TeacherRepository;

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
	private TeacherRepository cascadeTeacherRepository;
	@Resource
	private StudentRepository cascadeStudentRepository;
	
	@Test
	public void createTeacher(){
		//Hibernate: insert into t_teacher (age, name, id) values (?, ?, ?)
		TeacherCascade teacher = new TeacherCascade();
		teacher.setName("曹老师");
		teacher.setAge(28);
		
		cascadeTeacherRepository.save(teacher);
	}
	
	@Test
	public void updateTeacher(){
/*
 * 1.无CascadeType的情况下，Teacher 的属性不改变的情况下不会 update ，请分析为何会产生两条 Teacher 查询语句
 * Hibernate: select teachercas0_.id as id1_1_0_, teachercas0_.age as age2_1_0_, teachercas0_.name as name3_1_0_ from t_teacher teachercas0_ where teachercas0_.id=?
Hibernate: select teachercas0_.id as id1_1_0_, teachercas0_.age as age2_1_0_, teachercas0_.name as name3_1_0_ from t_teacher teachercas0_ where teachercas0_.id=?
Hibernate: select student0_.teacher_id as teacher_4_0_0_, student0_.id as id1_0_0_, student0_.id as id1_0_1_, student0_.age as age2_0_1_, student0_.name as name3_0_1_, student0_.teacher_id as teacher_4_0_1_ from t_student student0_ where student0_.teacher_id=?
Hibernate: update t_teacher set age=?, name=? where id=?

2. CascadeType.PERSIST, CascadeType.MERGE 需要一起使用
PERSIST 新增
MERGE 保存

 */
		TeacherCascade teacher = cascadeTeacherRepository.findOne("4028ed815df808ca015df808d1f40002");
		if (teacher!=null){
//			org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role:
//			com.eastrobot.lazy.entity.TeacherLazy.student, could not initialize proxy - no Session
			
			teacher.setAge(32);
			
			//设置学生
			Set<StudentCascade> set = new HashSet<StudentCascade>();
			StudentCascade s0 = new StudentCascade("李白同学", 6, teacher);
			s0.setKey(1);
			set.add(s0);
			StudentCascade s1 = new StudentCascade("杜甫同学", 16, teacher);
			s1.setKey(2);
			set.add(s1);
			StudentCascade s2 = new StudentCascade("陆游童鞋", 23, teacher);
			s2.setKey(3);
			set.add(s2);
			
			teacher.setStudent(set);
			cascadeTeacherRepository.save(teacher);
		}else{
			LOGGER.debug("teacher is not exsit");
		}
	}
	
	@Test
	@Transactional
	public void updateStudent(){

		TeacherCascade teacher = cascadeTeacherRepository.findByNameForLazy("曹老师");
		if (teacher!=null){
			teacher.getStudent().forEach(student -> LOGGER.debug("学生{}的年龄是{}岁", student.getName(), student.getAge()));
			
			Set<StudentCascade> set = teacher.getStudent();
			set.forEach(student -> {if (student.getKey()==1) student.setAge(12);});
			cascadeStudentRepository.save(set);
			LOGGER.debug("--------------------------------------------------------");
			teacher.getStudent().forEach(student -> LOGGER.debug("学生{}的年龄是{}岁", student.getName(), student.getAge()));
			
		}else{
			LOGGER.debug("teacher is not exsit");
		}
	}
	
	@Test
	public void deleteStudent(){
/*
Hibernate: select studentcas0_.id as id1_0_0_, studentcas0_.age as age2_0_0_, studentcas0_.key_ as key_3_0_0_, studentcas0_.name as name4_0_0_, studentcas0_.teacher_id as teacher_5_0_0_ from t_student studentcas0_ where studentcas0_.id=?
Hibernate: select teachercas0_.id as id1_1_1_, teachercas0_.age as age2_1_1_, teachercas0_.key_ as key_3_1_1_, teachercas0_.name as name4_1_1_, student1_.teacher_id as teacher_5_0_3_, student1_.id as id1_0_3_, student1_.id as id1_0_0_, student1_.age as age2_0_0_, student1_.key_ as key_3_0_0_, student1_.name as name4_0_0_, student1_.teacher_id as teacher_5_0_0_ from t_teacher teachercas0_ left outer join t_student student1_ on teachercas0_.id=student1_.teacher_id where teachercas0_.id=?
Hibernate: delete from t_student where id=?
Hibernate: delete from t_student where id=?

 */
		TeacherCascade teacher = cascadeTeacherRepository.findByNameForLazy("曹老师");
		if (teacher!=null){
			LOGGER.debug("{} 的 学生人数是 {}", teacher.getName(), teacher.getStudent().size());
			
			cascadeStudentRepository.delete(teacher.getStudent());
			
			//cascadeTeacherRepository.save(teacher);
		}else{
			LOGGER.debug("teacher is not exsit");
		}
	}
	
	/**
	 * left join fetch
	 * @author eko.zhan at 2017年8月14日 下午2:56:45
	 */
	@Test
	public void findByNameForLazy(){
//		Hibernate: select teacherlaz0_.id as id1_1_0_, student1_.id as id1_0_1_, teacherlaz0_.age as age2_1_0_, teacherlaz0_.name as name3_1_0_, student1_.age as age2_0_1_, student1_.name as name3_0_1_, student1_.teacher_id as teacher_4_0_1_, student1_.teacher_id as teacher_4_0_0__, student1_.id as id1_0_0__ from t_teacher teacherlaz0_ left outer join t_student student1_ on teacherlaz0_.id=student1_.teacher_id where teacherlaz0_.name=? order by student1_.name desc
		TeacherCascade teacher = cascadeTeacherRepository.findByNameForLazy("曹老师");
		LOGGER.debug("{} 的年龄是 {} 岁", teacher.getName(), teacher.getAge());
		LOGGER.debug("{} 的 学生人数是 {}", teacher.getName(), teacher.getStudent().size());
		teacher.getStudent().forEach(student -> LOGGER.debug("学生{}的年龄是{}岁", student.getName(), student.getAge()));
	}
	
	
	@Test
	public void delete(){
/*
 * 如果级联不是 REMOVE 或 ALL 会报错
 */
		TeacherCascade teacher = cascadeTeacherRepository.findOne("4028ed815df56845015df5685c750000");
		if (teacher!=null){
			cascadeTeacherRepository.delete(teacher);
		}else{
			LOGGER.warn("teacher is not exist");
		}
	}
	
}
