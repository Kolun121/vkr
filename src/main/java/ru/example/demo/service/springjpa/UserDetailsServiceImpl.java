package ru.example.demo.service.springjpa;

import java.io.ByteArrayInputStream;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.example.demo.domain.User;
import ru.example.demo.repository.UserRepository;
import ru.example.demo.config.security.SecurityUser;
import ru.example.demo.service.UserService;


@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
        return SecurityUser.fromUser(user);
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(!userOptional.isPresent()){
            return null;
        }
        
        return userOptional.get();
    }

    @Override
    public Set<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {        
        Optional<User> userOptional = userRepository.findById(id);
        if(!userOptional.isPresent()){
            return null;
        }
        
        return userOptional.get();
    }

    @Override
    public User save(User object) {
        return userRepository.save(object);
    }

    @Override
    public void delete(User object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ByteArrayInputStream loadCSV() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}