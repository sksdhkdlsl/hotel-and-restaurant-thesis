package hu.unideb.inf.thesis.hotel.service.impl;

import hu.unideb.inf.thesis.hotel.client.api.service.UserService;
import hu.unideb.inf.thesis.hotel.client.api.vo.RoleVo;
import hu.unideb.inf.thesis.hotel.client.api.vo.UserVo;
import hu.unideb.inf.thesis.hotel.core.entitiy.RoleEntity;
import hu.unideb.inf.thesis.hotel.core.entitiy.UserEntity;
import hu.unideb.inf.thesis.hotel.core.repository.RoleRepository;
import hu.unideb.inf.thesis.hotel.core.repository.UserRepository;
import hu.unideb.inf.thesis.hotel.service.mapper.RoleMapper;
import hu.unideb.inf.thesis.hotel.service.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import javax.ejb.*;
import javax.interceptor.Interceptors;
import java.util.ArrayList;
import java.util.List;

@Stateless(name = "UserService", mappedName = "UserService")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
@Local(UserService.class)
@Interceptors({SpringBeanAutowiringInterceptor.class})
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    public static final String DEFAULT_USER_ROLE = "ROLE_USER";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<UserVo> getUsers() {
        return UserMapper.toVo(userRepository.findAll());
    }

    @Override
    public UserVo saveUser(UserVo userVo) {
        UserEntity userEntity = userRepository.findOne(userVo.getId());

        if (userEntity == null) {
            userEntity = new UserEntity();
            UserMapper.toEntity(userVo, userEntity);
        }

        return UserMapper.toVo(userRepository.save(userEntity));
    }

    @Override
    public UserVo getUserById(Long id) {
        return UserMapper.toVo(userRepository.findOne(id));
    }

    @Override
    public UserVo getUserByName(String name) {
        return UserMapper.toVo(userRepository.findByName(name));
    }

    @Override
    public UserVo getUserByEmail(String email) {
        return UserMapper.toVo(userRepository.findByEmail(email));
    }

    @Override
    public Long countUsers() {
        return userRepository.count();
    }

    @Override
    public void addRoleToUserByName(String name, RoleVo roleVo) {
        boolean contains = false;

        for (RoleEntity role : userRepository.findByName(name).getRoles()) {
            contains = role.getName().equals(roleVo.getName());
            if (contains) {
                break;
            }
        }

        if (!contains) {
            userRepository.findByName(name).getRoles().add(RoleMapper.toEntity(roleVo));
        }
    }

    @Override
    public void removeRoleFromUserByName(String name, RoleVo roleVo) {
        List<RoleEntity> newRoles = new ArrayList<RoleEntity>();

        for (RoleEntity role : userRepository.findByName(name).getRoles()) {
            if (!(role.getName().equals(roleVo.getName()))) {
                newRoles.add(role);
            }
        }
        userRepository.findByName(name).setRoles(newRoles);
    }

    @Override
    public void setUserActivityByName(String name, boolean activity) {
        userRepository.findByName(name).setActive(activity);
    }

    @Override
    public void registerUser(UserVo user) {

        UserEntity userEntity = UserMapper.toEntity(user);
        if (userEntity.getRoles() == null) {
            userEntity.setRoles(new ArrayList<>(1));
        }

        addDefaultRole(userEntity);
        userRepository.save(userEntity);
    }

    private void addDefaultRole(UserEntity userEntity) {
        RoleEntity role = roleRepository.findByName(DEFAULT_USER_ROLE);
        userEntity.getRoles().add(role);
    }

}
