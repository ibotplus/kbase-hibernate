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

import com.eastrobot.many.lazy.entity.RoleLazy;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年8月19日 上午10:42:15
 * @version 1.0
 */
@Repository("lazyRoleRepository")
public interface RoleRepository extends CrudRepository<RoleLazy, Serializable> {

	@Query("from RoleLazy role left join fetch role.users user where role.name=:roleName order by user.name asc")
	public List<RoleLazy> findByRoleName(@Param("roleName") String roleName);
}
