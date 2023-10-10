package com.lofominhili.trendchat.mappers;

import com.lofominhili.trendchat.dto.UserDTO;
import com.lofominhili.trendchat.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserEntity toEntity(UserDTO userDTO);

    UserDTO toDto(UserEntity user);

}
