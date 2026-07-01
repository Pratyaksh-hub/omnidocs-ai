package com.pratyaksh.omnidocs_ai.document.mapper;

import com.pratyaksh.omnidocs_ai.common.mapper.MapperConfiguration;
import com.pratyaksh.omnidocs_ai.document.dto.UploadDocumentResponse;
import com.pratyaksh.omnidocs_ai.document.entity.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfiguration.class)
public interface DocumentMapper {
  @Mapping(target = "documentUuid", source = "uuid")
  UploadDocumentResponse toResponse(Document document);

}