/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.many.eager.repository;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eastrobot.many.eager.entity.RoleEager;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年8月19日 上午10:42:15
 * @version 1.0
 */
@Repository("eagerRoleRepository")
public interface RoleRepository extends CrudRepository<RoleEager, Serializable> {

}
