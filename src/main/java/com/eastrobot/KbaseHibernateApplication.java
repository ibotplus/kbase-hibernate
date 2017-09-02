package com.eastrobot;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@EnableCaching
public class KbaseHibernateApplication {
	
	/**
	 * 注入 SessionFactory
	 * @author eko.zhan at 2017年8月6日 下午6:32:48
	 * @param hemf
	 * @return
	 */
	@Bean
	public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf){  
	    return hemf.getSessionFactory();
	}
	/**
	 * 注入 HibernateTemplate
	 * @author eko.zhan at 2017年8月6日 下午6:32:59
	 * @param hemf
	 * @return
	 */
	@Bean
    public HibernateTemplate hibernateTemplate(HibernateEntityManagerFactory hemf) {
        return new HibernateTemplate(sessionFactory(hemf));
    }

	public static void main(String[] args) {
		SpringApplication.run(KbaseHibernateApplication.class, args);
	}
}
