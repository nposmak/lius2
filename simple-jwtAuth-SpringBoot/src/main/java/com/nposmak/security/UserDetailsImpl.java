package com.nposmak.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nposmak.entity.users.Role;
import com.nposmak.entity.users.User;

public class UserDetailsImpl implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	
	private User user;
	
	public UserDetailsImpl(User user) {
        this.user = user;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Role role = user.getRole();
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		return authorities;
	}
	
	public boolean hasRole(String roleName) {
		return this.user.userHasRole(roleName);
	}
	
	public Long getId() {
		return user.getId();
	}
	
	public String getEmail() {
		return user.getEmail();
	}
	
	public String getName() {
		return user.getName();
	}
	
	public String getSname() {
		return user.getSname();
	}
	
	public String getFname() {
		return user.getFname();
	}
	
	//доавим поля с инф. о пользователе для JwtResponse (LoginResponse)
	public String getOffice() {
		return user.getOffice().getOffName();
	}
	public String getDepartment() {
		return user.getDepartment().getDepName();
	}
	public String getPosition() {
		return user.getPosition().getPosName();
	}
	

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
