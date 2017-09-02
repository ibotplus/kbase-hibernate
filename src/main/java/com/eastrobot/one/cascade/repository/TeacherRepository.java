/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.one.cascade.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eastrobot.one.cascade.entity.TeacherCascade;

/**
 * 
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年8月5日 上午10:22:01
 * @version 1.0
 */
@Repository("cascadeTeacherRepository")
public interface TeacherRepository extends CrudRepository<TeacherCascade, Serializable>{

	@Query("from TeacherCascade where name=:name")
	public TeacherCascade findByName(@Param("name") String name);
	
	/**
	 * 请思考：如果将 left join 改为 inner join 而且 name 没有学生时，会发生什么？
	 * @author eko.zhan at 2017年8月6日 下午5:00:58
	 * @param name
	 * @return
	 */
	@Query("from TeacherCascade t left join fetch t.student s where t.name=:name order by s.name desc")
	public TeacherCascade findByNameForLazy(@Param("name") String name);
	
}
