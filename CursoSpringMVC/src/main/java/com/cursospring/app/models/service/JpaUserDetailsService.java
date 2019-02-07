package com.cursospring.app.models.service;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cursospring.app.models.dao.IUsuarioDao;
import com.cursospring.app.models.entity.Usuario;

@Service
public class JpaUserDetailsService implements UserDetailsService{
	
	@Autowired IUsuarioDao usuarioDao;
	private Logger logger= LoggerFactory.getLogger(getClass());

	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario= usuarioDao.findByUsername(username);
		
		if(usuario==null) {
			logger.error("Error Login: no existe el usuario '"+username +"'");
			throw new UsernameNotFoundException("Usuario: "+username +", no existe");
		}
		
		List<GrantedAuthority> authorities= new ArrayList<GrantedAuthority>();
		/*for (Role role : usuario.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		}*/
		usuario.getRoles().forEach(b-> authorities.add(new SimpleGrantedAuthority(b.getAuthority())));
		
		if(authorities.isEmpty()) {
			logger.error("Error Login: el usuario '"+username +"' no tiene roles asignados");
			throw new UsernameNotFoundException("Usuario: "+username +", no tiene roles");
		}
			
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
	}

}