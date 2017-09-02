/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.many.lazy.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eastrobot.many.lazy.entity.UserLazy;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年8月19日 上午10:42:15
 * @version 1.0
 */
@Repository("lazyUserRepository")
public interface UserRepository extends CrudRepository<UserLazy, Serializable> {

	@Query("from UserLazy user left join fetch user.roles role where user.name=:userName order by role.name asc")
	public List<UserLazy> findByUserName(@Param("userName") String userName);
	
}
