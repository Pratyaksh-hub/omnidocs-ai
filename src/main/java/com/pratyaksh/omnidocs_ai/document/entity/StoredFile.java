package com.pratyaksh.omnidocs_ai.document.entity;

import com.pratyaksh.omnidocs_ai.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "stored_files",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_stored_file_checksum",
            columnNames = "checksum"
        )
    },
    indexes = {
        @Index(name = "idx_checksum", columnList = "checksum")
    }
)
public class StoredFile extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 64)
  private String checksum;

  @Column(nullable = false, length = 255)
  private String storedFileName;

  @Column(nullable = false)
  private Long fileSize;

  @Column(nullable = false)
  private long referenceCount = 0L;

  public static StoredFile create(
      String checksum,
      String storedFileName,
      Long fileSize
  ) {

    StoredFile storedFile = new StoredFile();

    storedFile.checksum = checksum;
    storedFile.storedFileName = storedFileName;
    storedFile.fileSize = fileSize;
    storedFile.referenceCount = 1L;

    return storedFile;
  }

  public void incrementReferenceCount() {
    this.referenceCount++;
  }

  public void decrementReferenceCount() {

    if (referenceCount <= 0) {
      throw new IllegalStateException(
          "Reference count cannot be negative.");
    }

    this.referenceCount--;
  }

  public boolean isOrphan() {
    return referenceCount == 0;
  }

}