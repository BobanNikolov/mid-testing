package hr.abysalto.hiring.mid.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;
import java.time.OffsetDateTime;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class BaseEntity implements Serializable {
  @Version
  @Column(nullable = false)
  private Integer version;

  @Column(name = "creation_time", nullable = false, updatable = false)
  private OffsetDateTime creationTime;

  @Column(name = "modification_time", nullable = false)
  private OffsetDateTime modificationTime;

  @Column(name = "created_by")
  private Long createdBy;

  @Column(name = "modified_by")
  private Long modifiedBy;

  @PreUpdate
  public void preUpdate() {
    modificationTime = OffsetDateTime.now();
    modifiedBy = getCurrentUserIdSafe();
  }

  @PrePersist
  public void prePersist() {
    final OffsetDateTime t = OffsetDateTime.now();
    creationTime = t;
    modificationTime = t;
    createdBy = getCurrentUserIdSafe();
    modifiedBy = getCurrentUserIdSafe();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    BaseEntity that = (BaseEntity) o;

    return new EqualsBuilder().append(version, that.version)
        .append(creationTime, that.creationTime).append(modificationTime, that.modificationTime)
        .append(createdBy, that.createdBy).append(modifiedBy, that.modifiedBy).isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(version).append(creationTime)
        .append(modificationTime)
        .append(createdBy).append(modifiedBy).toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("version", version)
        .append("creationTime", creationTime)
        .append("modificationTime", modificationTime)
        .append("createdBy", createdBy)
        .append("modifiedBy", modifiedBy)
        .toString();
  }

  private Long getCurrentUserIdSafe() {
    if (SecurityContextHolder.getContext().getAuthentication() == null) {
      return null;
    }

    Object principal =
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (principal instanceof UserAccount user) {
      return user.getId();
    }

    return null;
  }
}
