/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.one.cascade.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eastrobot.one.cascade.entity.StudentCascade;

/**
 * 
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年8月5日 上午10:22:01
 * @version 1.0
 */
@Repository("cascadeStudentRepository")
public interface StudentRepository extends CrudRepository<StudentCascade, Serializable>{

	@Query("from StudentCascade where name=:name")
	public StudentCascade findByName(@Param("name") String name);
	
}
