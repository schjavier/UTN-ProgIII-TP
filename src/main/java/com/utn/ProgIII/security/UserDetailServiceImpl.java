package com.utn.ProgIII.security;

import com.utn.ProgIII.exceptions.CredentialNotFoundException;
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
public class UserDetailServiceImpl implements UserDetailsService {

    private final CredentialRepository credentialRepository;

    public UserDetailServiceImpl(CredentialRepository credentialRepository){
        this.credentialRepository = credentialRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Credential credential = credentialRepository.findByUsername(username).orElseThrow(
                () -> new CredentialNotFoundException("El usuario no se encuentra en el Sistema")
        );

        GrantedAuthority authorities = new SimpleGrantedAuthority(credential.getRole().name());

        return org.springframework.security.core.userdetails.User.builder()
                .username(credential.getUsername())
                .password(credential.getPassword())
                .roles(credential.getRole().name())
                .build();
    }
}
