package com.utn.ProgIII.security;

import com.utn.ProgIII.model.Credential.Credential;
import com.utn.ProgIII.repository.CredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private CredentialRepository credentialRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Credential credential = credentialRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("El usuario no existe")
        );

        GrantedAuthority authorities = new SimpleGrantedAuthority(credential.getRole().name());

        return org.springframework.security.core.userdetails.User.builder()
                .username(credential.getUsername())
                .password(credential.getPassword())
                .roles(credential.getRole().name())
                .build();
    }
}
