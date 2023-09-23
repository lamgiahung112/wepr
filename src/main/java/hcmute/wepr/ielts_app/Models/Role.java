package hcmute.wepr.ielts_app.Models;

import jakarta.persistence.*;
import jakarta.persistence.GenerationType;

@Entity
@Table(name="roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="role_id")
	private Integer roleId;
	private String authority;
	
	public Role() {
		super();
	}
	
	public Role(String authority) {
		this.authority = authority;
	}
	
	public Role(Integer roleId, String authority) {
		this.roleId = roleId;
		this.authority = authority;
	}
	
	public String getAuthority() {
		return this.authority;
	}
	
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	public Integer getRoleId() {
		return this.roleId;
	}
	
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
}
