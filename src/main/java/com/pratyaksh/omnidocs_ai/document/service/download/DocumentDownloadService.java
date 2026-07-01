package com.pratyaksh.omnidocs_ai.document.service.download;

import com.pratyaksh.omnidocs_ai.document.response.DownloadDocumentResponse;
import java.util.UUID;

public interface DocumentDownloadService {

  DownloadDocumentResponse download(UUID documentUuid);

}