package com.pratyaksh.omnidocs_ai.document.repository;

import com.pratyaksh.omnidocs_ai.document.entity.StoredFile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoredFileRepository extends JpaRepository<StoredFile, Long> {

  Optional<StoredFile> findByChecksum(String checksum);

  boolean existsByChecksum(String checksum);

}
