package hu.unideb.inf.thesis.hotel.client.api.service;

import hu.unideb.inf.thesis.hotel.client.api.vo.RoleVo;
import hu.unideb.inf.thesis.hotel.client.api.vo.RoomReserveVo;
import hu.unideb.inf.thesis.hotel.client.api.vo.UserVo;

import java.util.List;

public interface UserService {

    UserVo saveUser(UserVo userVo);

    UserVo getUserById(Long id);

    UserVo getUserByName(String name);

    UserVo getUserByEmail(String email);

    List<UserVo> getUsers();

    Long countUsers();

    void addRoomReserveToUser(UserVo userVo, RoomReserveVo roomReserveVo);

    void addRoleToUserByName(String name, RoleVo roleVo);

    void removeRoleFromUserByName(String name, RoleVo roleVo);

    void setUserActivityByName(String name, boolean activity);

    void registerUser(UserVo user);
}
