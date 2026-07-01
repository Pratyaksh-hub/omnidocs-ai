package com.pratyaksh.omnidocs_ai.document.mapper;

import com.pratyaksh.omnidocs_ai.common.mapper.MapperConfiguration;
import com.pratyaksh.omnidocs_ai.document.response.DocumentResponse;
import com.pratyaksh.omnidocs_ai.document.response.DocumentSummaryResponse;
import com.pratyaksh.omnidocs_ai.document.response.UploadDocumentResponse;
import com.pratyaksh.omnidocs_ai.document.entity.Document;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfiguration.class)
public interface DocumentMapper {

  @Mapping(target = "documentUuid", source = "uuid")
  UploadDocumentResponse toResponse(Document document);

  @Mapping(target = "documentUuid", source = "uuid")
  @Mapping(target = "workspaceUuid", source = "workspace.uuid")
  @Mapping(target = "fileSize", source = "storedFile.fileSize")
  DocumentResponse toDocumentResponse(Document document);

  @Mapping(target = "documentUuid", source = "uuid")
  @Mapping(target = "fileSize", source = "storedFile.fileSize")
  DocumentSummaryResponse toSummaryResponse(Document document);
}