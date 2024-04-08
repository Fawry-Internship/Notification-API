package com.example.email.mapper;

import com.example.email.entity.Email;
import com.example.email.model.EmailModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EmailMapper {
      EmailMapper INSTANCE = Mappers.getMapper(EmailMapper.class);

      @Mapping(target = "to", source = "to")
      @Mapping(target = "from", expression = "java(\"esraamabrouk126@gmail.com\")")
      Email modelToEntity(EmailModel emailModel);

      EmailModel entityToModel(Email email);
}