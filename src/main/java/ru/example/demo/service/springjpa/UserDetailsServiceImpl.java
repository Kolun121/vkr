package ru.example.demo.service.springjpa;

import java.util.Comparator;
import java.util.List;
import ru.example.demo.domain.User;
import ru.example.demo.repository.UserRepository;
import ru.example.demo.config.security.SecurityUser;
import ru.example.demo.service.UserService;

import java.util.Optional;
import java.util.function.Predicate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.example.demo.exception.NotFoundException;
import ru.example.demo.helper.comparator.UserComparators;
import ru.example.demo.helper.paging.Direction;


@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl extends AbstractJpaFilteringSortingServiceImpl<User, Long, UserRepository> implements UserDetailsService, UserService {


    public UserDetailsServiceImpl(UserRepository rRepository) {
        super(rRepository);
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = rRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
        return SecurityUser.fromUser(user);
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> userOptional = rRepository.findByUsername(username);
        if(!userOptional.isPresent()){
            throw new NotFoundException("Не найден");
        }
        
        return userOptional.get();
    }

    @Override
    public List<User> findAll() {
        return rRepository.findAll();
    }

    @Override
    public User findById(Long id) {        
        Optional<User> userOptional = rRepository.findById(id);
        if(!userOptional.isPresent()){
            throw new NotFoundException("Не найден");
        }
        
        return userOptional.get();
    }

    @Override
    public User save(User object) {
        return rRepository.save(object);
    }

    @Override
    public void delete(User object) {
        rRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        rRepository.deleteById(id);
    }

    @Override
    public void deleteAll(Iterable<User> objects) {
        rRepository.deleteAll(objects);
    }

    @Override
    protected Predicate<User> formTPredicate(String value) {
        Predicate<User> predicate = m -> 
        {
            return m.getUsername().toLowerCase().contains(value);
        };
        
        return predicate;
    }

    @Override
    protected Comparator<User> getTComparator(String name, Direction dir) {
        return UserComparators.getComparator(name, dir);
    }

    @Override
    protected Comparator<User> getTEmptyComparator() {
        return UserComparators.getEmptyComparator();
    }

}