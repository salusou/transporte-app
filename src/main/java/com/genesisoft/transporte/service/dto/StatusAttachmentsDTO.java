package com.genesisoft.transporte.service.dto;

import com.genesisoft.transporte.domain.enumeration.AttachmentType;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.StatusAttachments} entity.
 */
@ApiModel(description = "Status Attachments\nThis class list all status for all screens.\n@author Samuel Souza")
public class StatusAttachmentsDTO implements Serializable {

    private Long id;

    @NotNull
    private String statusName;

    private String statusDescriptions;

    private String statusObs;

    private AttachmentType attachmentType;

    private AffiliatesDTO affiliates;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusDescriptions() {
        return statusDescriptions;
    }

    public void setStatusDescriptions(String statusDescriptions) {
        this.statusDescriptions = statusDescriptions;
    }

    public String getStatusObs() {
        return statusObs;
    }

    public void setStatusObs(String statusObs) {
        this.statusObs = statusObs;
    }

    public AttachmentType getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(AttachmentType attachmentType) {
        this.attachmentType = attachmentType;
    }

    public AffiliatesDTO getAffiliates() {
        return affiliates;
    }

    public void setAffiliates(AffiliatesDTO affiliates) {
        this.affiliates = affiliates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StatusAttachmentsDTO)) {
            return false;
        }

        StatusAttachmentsDTO statusAttachmentsDTO = (StatusAttachmentsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, statusAttachmentsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatusAttachmentsDTO{" +
            "id=" + getId() +
            ", statusName='" + getStatusName() + "'" +
            ", statusDescriptions='" + getStatusDescriptions() + "'" +
            ", statusObs='" + getStatusObs() + "'" +
            ", attachmentType='" + getAttachmentType() + "'" +
            ", affiliates=" + getAffiliates() +
            "}";
    }
}
