package org.taskspfe.pfe.security.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.taskspfe.pfe.exceptions.ResourceNotFoundException;
import org.taskspfe.pfe.repository.UserEntityRepository;

@Service
public class CustomUserDetailsService  implements UserDetailsService {

    private  final UserEntityRepository userEntityRepository;
    @Autowired
    public CustomUserDetailsService(UserEntityRepository userEntityRepository)
    {
        this.userEntityRepository = userEntityRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userEntityRepository.fetchUserWithEmail(email).orElseThrow(()-> new ResourceNotFoundException("The email address provided could not be found in our system."));
    }
}
