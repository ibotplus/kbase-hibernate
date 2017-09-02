/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.one.lazy.service;

import com.eastrobot.one.lazy.entity.StudentLazy;
import com.eastrobot.one.lazy.entity.TeacherLazy;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年8月6日 下午5:38:16
 * @version 1.0
 */
public interface TeacherService {

	/**
	 * 根据指定的教师名称获取教师
	 * @author eko.zhan at 2017年8月6日 下午1:19:10
	 * @param name
	 * @return
	 */
	public TeacherLazy findByName(String name);
	
	/**
	 * 保存老师和学生
	 * @author eko.zhan at 2017年8月28日 下午1:20:19
	 * @param teacher
	 * @param student
	 */
	public void saveTeacher(TeacherLazy teacher, StudentLazy student);
}
