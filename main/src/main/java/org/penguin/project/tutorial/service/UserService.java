package org.penguin.project.tutorial.service;

import io.reactivex.Completable;
import io.reactivex.Single;
import org.penguin.project.tutorial.exception.EntityExistException;
import org.penguin.project.tutorial.exception.EntityNotFoundException;
import org.penguin.project.tutorial.mapper.RoleMapper;
import org.penguin.project.tutorial.mapper.UserMapper;
import org.penguin.project.tutorial.domain.Role;
import org.penguin.project.tutorial.domain.User;
import org.penguin.project.tutorial.security.SecurityUserDetails;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Autowired
    private PasswordEncoder bcryptPasswordEncoder;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        String userId = user.getId();
        Set<Role> roleList = roleMapper.findByUserId(userId);
        user.setRoles(roleList);
        return SecurityUserDetails.build(user);
    }

    public List<User> findAll() {
        return userMapper.findAll();
    }

    public int insert(User user) {
        user.setEncryptedPassword(bcryptPasswordEncoder.encode(user.getEncryptedPassword()));
        return userMapper.insert(user);
    }

    public Optional<User> findByEmail(String email) {
        return userMapper.findByEmail(email);
        // return new ModelMapper().map(userEntity, UserDto.class);
    }

    public Optional<User> findByUsernameOrEmail(String username, String email) {
        return userMapper.findByUsernameOrEmail(username, email);
    }

    public Optional<User> findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    public boolean existByUsername(String username) {
        return userMapper.existByUsername(username);
    }

    public boolean existByEmail(String email) {
        return userMapper.existByEmail(email);
    }

    // For RxJava2
    public Single<String> addUser(User user) {
        return Single.create(singleSubscriber -> {
            Optional<User> optionalAuthor = userMapper.findByUsername(user.getUsername());
//            BeanUtils.copyProperties(source, target);
            if (!optionalAuthor.isPresent())
                singleSubscriber.onError(new EntityExistException());
            else {
                int insertRowCount = userMapper.insert(user);
                if (insertRowCount == 1) {
                    singleSubscriber.onSuccess(user.getId());
                }
            }
        });
    }

    public Completable updateUser(User user) {
        return Completable.create(completableSubscriber -> {
            Optional<User> optionalBook = userMapper.findByUsername(user.getUsername());
            if (!optionalBook.isPresent())
                completableSubscriber.onError(new EntityNotFoundException());
            else {
                User book = optionalBook.get();
                book.setFullname(user.getFullname());
                book.setDescription(user.getDescription());
//                userMapper.save(book);
                completableSubscriber.onComplete();
            }
        });
    }

    public Single<List<User>> getAllUsers(int limit, int page) {
        return Single.create(singleSubscriber -> {
            List<User> users = userMapper.findAll();
            singleSubscriber.onSuccess(users);
        });
    }

    public Completable deleteUser(String username) {
        return Completable.create(completableSubscriber -> {
            Optional<User> optionalUser = userMapper.findByUsername(username);
            if (!optionalUser.isPresent())
                completableSubscriber.onError(new EntityNotFoundException());
            else {
//                userMapper.delete(optionalUser.get());
                completableSubscriber.onComplete();
            }
        });
    }

    public Single<User> getUserDetail(String username) {
        return Single.create(singleSubscriber -> {
            Optional<User> optionalUser = userMapper.findByUsername(username);
            if (!optionalUser.isPresent())
                singleSubscriber.onError(new EntityNotFoundException());
            else {
                singleSubscriber.onSuccess(optionalUser.get());
            }
        });
    }
}
