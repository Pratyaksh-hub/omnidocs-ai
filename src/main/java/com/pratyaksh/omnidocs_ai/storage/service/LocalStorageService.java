package com.pratyaksh.omnidocs_ai.storage.service;

import com.pratyaksh.omnidocs_ai.storage.StorageProperties;
import com.pratyaksh.omnidocs_ai.storage.exception.StorageException;
import com.pratyaksh.omnidocs_ai.storage.model.StorageRequest;
import com.pratyaksh.omnidocs_ai.storage.model.StoredFileMetadata;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocalStorageService implements StorageService {

  private final StorageProperties storageProperties;

  @Override
  public StoredFileMetadata store(StorageRequest request) {

    validateRequest(request);

    String originalFilename = StringUtils.cleanPath(request.getOriginalFilename());
    String storedFileName = generateStoredFileName(originalFilename);

    try {
      Path storageRoot = getStorageRoot();

      Files.createDirectories(storageRoot);

      Path destination = storageRoot.resolve(storedFileName).normalize();

      MessageDigest digest = MessageDigest.getInstance("SHA-256");

      try (DigestInputStream digestInputStream =
          new DigestInputStream(request.getInputStream(), digest)) {

        Files.copy(
            digestInputStream,
            destination,
            StandardCopyOption.REPLACE_EXISTING
        );
      }

      String checksum = HexFormat.of().formatHex(digest.digest());

      log.info(
          "File stored successfully. originalFileName={}, storedFileName={}, size={} bytes",
          originalFilename,
          storedFileName,
          request.getFileSize()
      );

      return StoredFileMetadata.builder()
          .storedFileName(storedFileName)
          .checksum(checksum)
          .fileSize(request.getFileSize())
          .build();

    } catch (IOException ex) {

      throw new StorageException(
          "Failed to store file: " + originalFilename,
          ex
      );

    } catch (NoSuchAlgorithmException ex) {

      throw new IllegalStateException(
          "SHA-256 algorithm is not available.",
          ex
      );
    }
  }

  private String generateStoredFileName(String originalFilename) {

    String extension = "";

    int index = originalFilename.lastIndexOf('.');

    if (index != -1) {
      extension = originalFilename.substring(index);
    }

    return LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE)
        + "_"
        + UUID.randomUUID()
        + extension;
  }

  @Override
  public Resource load(String storageKey) {

    try {

      Path file = getStorageRoot()
          .resolve(storageKey)
          .normalize();

      Resource resource = new UrlResource(file.toUri());

      if (!resource.exists() || !resource.isReadable()) {
        throw new StorageException("File not found: " + storageKey);
      }

      return resource;

    } catch (MalformedURLException ex) {

      throw new StorageException(
          "Unable to load file: " + storageKey,
          ex
      );
    }
  }

  @Override
  public void delete(String storageKey) {

    try {

      Files.deleteIfExists(
          getStorageRoot()
              .resolve(storageKey)
              .normalize()
      );

      log.info("Deleted stored file [{}]", storageKey);

    } catch (IOException ex) {

      throw new StorageException(
          "Failed to delete file: " + storageKey,
          ex
      );
    }
  }

  @Override
  public boolean exists(String storageKey) {

    return Files.exists(
        getStorageRoot()
            .resolve(storageKey)
            .normalize()
    );
  }

  private Path getStorageRoot() {

    return Paths.get(storageProperties.getLocation())
        .toAbsolutePath()
        .normalize();
  }

  private void validateRequest(StorageRequest request) {

    if (request == null) {
      throw new StorageException("Storage request cannot be null.");
    }

    if (request.getInputStream() == null) {
      throw new StorageException("Input stream cannot be null.");
    }

    if (!StringUtils.hasText(request.getOriginalFilename())) {
      throw new StorageException("Original filename cannot be empty.");
    }

    String cleanedFilename = StringUtils.cleanPath(request.getOriginalFilename());

    if (cleanedFilename.contains("..")) {
      throw new StorageException("Invalid filename.");
    }

    if (request.getFileSize() < 0) {
      throw new StorageException("Invalid file size.");
    }
  }
}