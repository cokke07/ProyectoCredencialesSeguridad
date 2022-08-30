package cl.cokke.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cl.cokke.mapper.UsersMapper;
import cl.cokke.model.Users;

@Service
public class AuthServiceImp implements UserDetailsService {

	@Autowired
	private UsersMapper usersMapper;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Users user =  usersMapper.findByEmail(email);
		
		if (user == null) {
			throw new UsernameNotFoundException(email);
		}
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
		
		return new User(user.getEmail(), user.getPassword(), authorities);
	}

}
