/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.one.cascade;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.eastrobot.one.cascade.entity.StudentCascade;
import com.eastrobot.one.cascade.repository.StudentRepository;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年8月5日 上午11:03:37
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentRepositoryTests {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass()); 
	
	@Resource
	private StudentRepository cascadeStudentRepository;
	
	@Test
	public void findByName(){
		StudentCascade student = cascadeStudentRepository.findByName("李白同学");
		LOGGER.debug("{} 的年龄是 {} , 老师是 {} ", student.getName(), student.getAge(), student.getTeacher().getName());
	}
}
