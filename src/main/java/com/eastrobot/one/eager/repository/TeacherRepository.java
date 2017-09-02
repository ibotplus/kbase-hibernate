/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.one.eager.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eastrobot.one.eager.entity.TeacherEager;

/**
 * 
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年8月5日 上午10:22:01
 * @version 1.0
 */
@Repository("eagerTeacherRepository")
public interface TeacherRepository extends CrudRepository<TeacherEager, Serializable>{

	@Query("from TeacherEager where name=:name")
	public TeacherEager findByName(@Param("name") String name);
	
}
