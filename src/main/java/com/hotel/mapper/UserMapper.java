package com.hotel.mapper;

import com.hotel.dto.UserDTO;
import com.hotel.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(User entity);
    User toEntity(UserDTO dto);
    List<UserDTO> toDtoList(List<User> list);
}
