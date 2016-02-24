package com.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.model.base.BaseEntity;

/**
 * @author 袁培朋
 * @Entity:则继承后，多个类继承，只会生成一个表，而不是多个继承，生成多个表
 * @Table: 数据库映射表的名称
 * @Column: 数据设置字段名称
 */
@Entity
@Table(name = "person")
public class Person extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

   	public Person(Integer id) {
		super(id);
	}
	
	public Person() {
		super();
	}

	/* 用户名 */
	@Column(name = "name")
	private String userName;
	/* 密码 */
	@Column(name = "password")
	private String passWord;


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

}
