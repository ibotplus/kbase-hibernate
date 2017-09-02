/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.one.lazy.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.eastrobot.one.lazy.entity.StudentLazy;
import com.eastrobot.one.lazy.entity.TeacherLazy;
import com.eastrobot.one.lazy.service.TeacherService;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年8月6日 下午5:23:02
 * @version 1.0
 */
@Service
public class TeacherServiceImpl implements TeacherService {

	@Resource
	private HibernateTemplate hibernateTemplate;
	@Resource
	private SessionFactory sessionFactory;
	
	@Override
	public TeacherLazy findByName(String name){
		Session session = sessionFactory.openSession();
		try{
			Query query = session.createQuery("from TeacherLazy where name=?");
			query.setString(0, name);
			List<TeacherLazy> list = query.list();
			for (TeacherLazy teacher : list){
				Hibernate.initialize(teacher.getStudent());
				return teacher;
			}
		}finally{
			if (session!=null){
				session.close();
			}
		}
		
		
//		
		return null;
	}
	
	/**
	 * 请思考用 hibernateTemplate 能否 initalize 处理 懒加载的问题，如果不能，为什么？
	 * @author eko.zhan at 2017年8月6日 下午6:30:10
	 * @param name
	 * @return
	 */
	private TeacherLazy findByName_0(String name){
		List<TeacherLazy> list = (List<TeacherLazy>) hibernateTemplate.find("from TeacherLazy where name=?", name);
		for (TeacherLazy teacher : list){
			Hibernate.initialize(teacher);
			Hibernate.initialize(teacher.getStudent());
			return teacher;
		}
		return null;
	}

	/**
	 * 1、不加事物会报错
	 * 2、加事物并且开启报错信息
	 * 3、加事物不开启报错信息
	 * 
	 * 
	<bean id="transactionManager" class="org.springframework.orm.hibernate3.HibernateTransactionManager">
		<property name="sessionFactory" ref="sessionFactory" />
	</bean>
	
	<tx:advice id="txAdvice" transaction-manager="transactionManager">
		<tx:attributes>
			<tx:method name="save*" propagation="REQUIRED" read-only="false" />
			<tx:method name="update*" propagation="REQUIRED" read-only="false" />
			<tx:method name="del*" propagation="REQUIRED" read-only="false" />
			<tx:method name="*" read-only="true" propagation="NOT_SUPPORTED" />
		</tx:attributes>
	</tx:advice>

	<aop:config>
		<aop:pointcut id="serviceMethod" expression="execution(* com.zokee.*.service.impl.*.*(..))" />
		<aop:advisor pointcut-ref="serviceMethod" advice-ref="txAdvice" />
	</aop:config>
	 */
	@Transactional
	@Override
	public void saveTeacher(TeacherLazy teacher, StudentLazy student) {
		hibernateTemplate.saveOrUpdate(teacher);
//		int i = 1/0;
//		System.out.println(i);
		hibernateTemplate.saveOrUpdate(student);
	}

}
