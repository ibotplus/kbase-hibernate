## Hibernate 一对多，多对多，懒加载，级联技术点解析

### Hibernate Proxy 三种状态
* 临时态
* 持久态
* 游离态

### 解决 Hibernate 懒加载的四种方法
* 采用急加载
* web工程使用spring提供的openSessionView #spring.jpa.open-in-view=true
* Hibernate.initailize com.eastrobot.one.lazy.service.impl.TeacherServiceImpl.findByName
* inner join fetch, left join fetch, right join fetch 

### hibernate自动建表，外键约束手动删除会遇到的问题

	spring.jpa.hibernate.ddl-auto=update
	hibernate.hbm2ddl_auto=update

* a foreign key constraint ...	
* No row with the given identifier exists 
* hibernateTemplate.findUnique 返回多条数据的问题 ，如用户管理 
* org.hibernate.NonUniqueObjectException: a different object with the same identifier value was already associated with the session
* org.hibernate.NonUniqueResultException: query did not return a unique result: 2
* 优先删除关联表中的数据，再删除主数据

### 一对多
* 急加载
* 懒加载
* 级联

### 多对多
* 急加载
* 懒加载（以用户和角色为例）
  * 双控：更新用户可同时更新角色/更新角色可同时更新用户
  * 主控和被控: 只从其中一个对象控制另一个
* 级联

### 常用注解
1. @Column
2. @Foumula
3. @OrderBy
4. @Transient
5. @Where
6. @JoinTable
7. @JoinColumn

### 表设计原理和映射对象
1. 哪些是需要保存的字段，哪些不需要保存？
 * 表单字段一定要保存
 * 列表页面需要显示的也应该保存，避免过多的join查询，如部门路径，用户中文名
 * 每个bean都应该继承 BaseBean，用于记录数据增删改查

2. 字段对应类型
 * 能够知道固定长度的字段避免用可变长度的类型
 * 序号类 Integer ， 1, 10, 11, 2, 3, 4...
 * 时间类 Timstamp 
 * 财务类数字 Decimal
 * 基本数据类型 int 和 Integer 的区别， 为什么Hibernate推荐映射为 Integer？
 * 表名和字段名大写
 * CLOB
 
### 索引与缓存
* 索引失效
* 缓存用例：TeacherRepositoryTests.findByCache

### 事务
* TeacherService.saveTeacher
 
### 资料
http://blog.csdn.net/deniro_li/article/details/70242801

http://blog.csdn.net/qq_32953079/article/details/53858728

http://www.cnblogs.com/e206842/p/6550605.html

http://blog.csdn.net/a67474506/article/details/52608855

http://blog.csdn.net/defonds/article/details/2308972

http://www.jianshu.com/p/64f684bd0ce9

### 其他
* 缺一个多对多拆成两个一对多的用例