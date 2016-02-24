package com.model.base;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
/**
 * 
 * @author 袁培朋
 * @MappedSupperclass:  用在父类上面。当这个类肯定是父类时，加此标注。
 */
@MappedSuperclass
public class BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7778214615367617011L;
	
	//主键id
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private int id;


	public BaseEntity() {
		super();
	}
	
	
	
	public BaseEntity(int id) {
		super();
		this.id = id;
	}



	/**
	 * 主键id
	 * @return
	 */
	
	public Integer getId() {
		return this.id;
	}

	/**
	 * 主键id
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}


}
