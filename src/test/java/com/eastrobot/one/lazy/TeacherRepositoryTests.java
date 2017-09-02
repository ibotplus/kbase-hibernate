/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.one.lazy;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.junit4.SpringRunner;

import com.eastrobot.one.lazy.entity.StudentLazy;
import com.eastrobot.one.lazy.entity.TeacherLazy;
import com.eastrobot.one.lazy.repository.TeacherRepository;
import com.eastrobot.one.lazy.service.TeacherService;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年8月5日 上午11:03:37
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableCaching
public class TeacherRepositoryTests {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass()); 
	
	@Resource
	private TeacherRepository lazyTeacherRepository;
	@Resource
	private TeacherService teacherService;
	
	@Test
	public void createTeacher(){
		//Hibernate: insert into t_teacher (age, name, id) values (?, ?, ?)
		TeacherLazy teacher = new TeacherLazy();
		teacher.setName("李老师");
		teacher.setAge(40);
		
		lazyTeacherRepository.save(teacher);
	}
	
	@Test
	public void updateTeacher(){
//		Hibernate: select teacherlaz0_.id as id1_1_0_, teacherlaz0_.age as age2_1_0_, teacherlaz0_.name as name3_1_0_ from t_teacher teacherlaz0_ where teacherlaz0_.id=?
//		Hibernate: select teacherlaz0_.id as id1_1_1_, teacherlaz0_.age as age2_1_1_, teacherlaz0_.name as name3_1_1_, student1_.teacher_id as teacher_4_0_3_, student1_.id as id1_0_3_, student1_.id as id1_0_0_, student1_.age as age2_0_0_, student1_.name as name3_0_0_, student1_.teacher_id as teacher_4_0_0_ from t_teacher teacherlaz0_ left outer join t_student student1_ on teacherlaz0_.id=student1_.teacher_id where teacherlaz0_.id=?
//		Hibernate: insert into t_student (age, name, teacher_id, id) values (?, ?, ?, ?)
//		Hibernate: insert into t_student (age, name, teacher_id, id) values (?, ?, ?, ?)
//		Hibernate: insert into t_student (age, name, teacher_id, id) values (?, ?, ?, ?)
//		Hibernate: update t_teacher set age=?, name=? where id=?
		TeacherLazy teacher = lazyTeacherRepository.findOne("4028ed815ddfad88015ddfad92bd0000");
		if (teacher!=null){
//			org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role:
//			com.eastrobot.lazy.entity.TeacherLazy.student, could not initialize proxy - no Session
			
			teacher.setAge(31);
			
			//设置学生
			Set<StudentLazy> set = new HashSet<StudentLazy>();
			StudentLazy s0 = new StudentLazy("小明同学", 6, teacher);
			set.add(s0);
			StudentLazy s1 = new StudentLazy("xiaoming", 16, teacher);
			set.add(s1);
			StudentLazy s2 = new StudentLazy("小明童鞋", 23, teacher);
			set.add(s2);
			
			teacher.setStudent(set);
			lazyTeacherRepository.save(teacher);
		}
	}
	
	@Test
	public void findByName(){
		//select teacherlaz0_.id as id1_1_, teacherlaz0_.age as age2_1_, teacherlaz0_.name as name3_1_ from t_teacher teacherlaz0_ where teacherlaz0_.name=?
		TeacherLazy teacher = lazyTeacherRepository.findByName("李老师");
		LOGGER.debug("{} 的年龄是 {} 岁", teacher.getName(), teacher.getAge());
		
		boolean initialized = Hibernate.isInitialized(teacher.getStudent());
		LOGGER.debug(String.valueOf(initialized));
		//懒加载模式下如何直接获取 teacher 下关联的学生？
		//org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: 
		//com.eastrobot.lazy.entity.TeacherLazy.student, could not initialize proxy - no Session

		LOGGER.debug("{} 的 学生人数是 {}", teacher.getName(), teacher.getStudent().size());
	}
	
	/**
	 * left join fetch
	 * @author eko.zhan at 2017年8月14日 下午2:56:45
	 */
	@Test
	public void findByNameForLazy(){
//		Hibernate: select teacherlaz0_.id as id1_1_0_, student1_.id as id1_0_1_, teacherlaz0_.age as age2_1_0_, teacherlaz0_.name as name3_1_0_, student1_.age as age2_0_1_, student1_.name as name3_0_1_, student1_.teacher_id as teacher_4_0_1_, student1_.teacher_id as teacher_4_0_0__, student1_.id as id1_0_0__ from t_teacher teacherlaz0_ left outer join t_student student1_ on teacherlaz0_.id=student1_.teacher_id where teacherlaz0_.name=? order by student1_.name desc
		TeacherLazy teacher = lazyTeacherRepository.findByNameForLazy("李老师");
		LOGGER.debug("{} 的年龄是 {} 岁", teacher.getName(), teacher.getAge());
		LOGGER.debug("{} 的 学生人数是 {}", teacher.getName(), teacher.getStudent().size());
		teacher.getStudent().forEach(student -> LOGGER.debug("学生{}的年龄是{}岁", student.getName(), student.getAge()));
	}
	
	/**
	 * initailize
	 * @author eko.zhan at 2017年8月14日 下午2:57:06
	 */
	@Test
	public void findByNameWithInitailize(){
		TeacherLazy teacher = teacherService.findByName("李老师");
		if (teacher!=null){
			LOGGER.debug("{} 的年龄是 {} 岁", teacher.getName(), teacher.getAge());
			LOGGER.debug("{} 的 学生人数是 {}", teacher.getName(), teacher.getStudent().size());
			teacher.getStudent().forEach(student -> LOGGER.debug("学生{}的年龄是{}岁", student.getName(), student.getAge()));
		}
	}
	
	@Test
	public void delete(){
//		Hibernate: select teacherlaz0_.id as id1_1_0_, teacherlaz0_.age as age2_1_0_, teacherlaz0_.name as name3_1_0_ from t_teacher teacherlaz0_ where teacherlaz0_.id=?
//		Hibernate: select teacherlaz0_.id as id1_1_1_, teacherlaz0_.age as age2_1_1_, teacherlaz0_.name as name3_1_1_, student1_.teacher_id as teacher_4_0_3_, student1_.id as id1_0_3_, student1_.id as id1_0_0_, student1_.age as age2_0_0_, student1_.name as name3_0_0_, student1_.teacher_id as teacher_4_0_0_ from t_teacher teacherlaz0_ left outer join t_student student1_ on teacherlaz0_.id=student1_.teacher_id where teacherlaz0_.id=?
//		Hibernate: delete from t_student where id=?
//		Hibernate: delete from t_student where id=?
//		Hibernate: delete from t_student where id=?
//		Hibernate: delete from t_teacher where id=?
		TeacherLazy teacher = lazyTeacherRepository.findOne("4028ed815ddfad88015ddfad92bd0000");
		if (teacher!=null){
			lazyTeacherRepository.delete(teacher);
		}else{
			LOGGER.warn("teacher is not exist");
		}
	}
	
	@Test
	public void findAll(){
		Iterable<TeacherLazy> itr = lazyTeacherRepository.findAll();
		//懒加载模式下如何直接获取 teacher 下关联的学生？
		//org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: 
		//com.eastrobot.lazy.entity.TeacherLazy.student, could not initialize proxy - no Session
		itr.forEach(teacher -> {LOGGER.debug("{} 的学生共 {} 名，分别是 ", teacher.getName(), teacher.getStudent().size()); teacher.getStudent().forEach(student -> LOGGER.debug(student.getName()));});
	}
	
	/**
	 * 事物测试
	 * @author eko.zhan at 2017年9月2日 上午10:26:28
	 */
	@Test
	public void saveTeacherTransactional(){
		TeacherLazy teacher = new TeacherLazy();
		teacher.setName("美国队长");
		teacher.setAge(119);
		StudentLazy student = new StudentLazy();
		student.setName("斯塔克");
		student.setAge(26);
		
		Set<StudentLazy> set = new HashSet<StudentLazy>();
		set.add(student);
		
		teacher.setStudent(set);
		teacherService.saveTeacher(teacher, student);
	}
	
	/**
	 * 缓存测试
	 */
	@Test
	public void findByCache(){
//		Iterable<TeacherLazy> itr = lazyTeacherRepository.findAll();
//		itr = lazyTeacherRepository.findAll();
//		itr = lazyTeacherRepository.findAll();
		
		Iterable<TeacherLazy> itr = lazyTeacherRepository.findAllByCache();
		itr = lazyTeacherRepository.findAllByCache();
		itr = lazyTeacherRepository.findAllByCache();
		
		
	}
}
