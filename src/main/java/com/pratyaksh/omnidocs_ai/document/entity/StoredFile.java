package com.pratyaksh.omnidocs_ai.document.entity;

import com.pratyaksh.omnidocs_ai.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
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
  @Builder.Default
  private long referenceCount = 0L;

}