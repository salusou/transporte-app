package com.genesisoft.transporte.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.genesisoft.transporte.domain.VehicleControlAttachments} entity.
 */
@ApiModel(description = "Vehicle Control Attachments.\nThis class list all attachments.\n@author Samuel Souza")
public class VehicleControlAttachmentsDTO implements Serializable {

    private Long id;

    /**
     * Attach Image will only receive the file.\n@type ImageBlob
     */
    @ApiModelProperty(value = "Attach Image will only receive the file.\n@type ImageBlob")
    @Lob
    private byte[] attachImage;

    private String attachImageContentType;

    /**
     * Id generate in cloudinary.\n@type String
     */
    @NotNull
    @ApiModelProperty(value = "Id generate in cloudinary.\n@type String", required = true)
    private String attachUrl;

    /**
     * description about the file attached.\n@type String
     */
    @NotNull
    @ApiModelProperty(value = "description about the file attached.\n@type String", required = true)
    private String attachDescription;

    /**
     * created date.\n@type String
     */
    @NotNull
    @ApiModelProperty(value = "created date.\n@type String", required = true)
    private ZonedDateTime attachedDate;

    private VehicleControlsDTO vehicleControls;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getAttachImage() {
        return attachImage;
    }

    public void setAttachImage(byte[] attachImage) {
        this.attachImage = attachImage;
    }

    public String getAttachImageContentType() {
        return attachImageContentType;
    }

    public void setAttachImageContentType(String attachImageContentType) {
        this.attachImageContentType = attachImageContentType;
    }

    public String getAttachUrl() {
        return attachUrl;
    }

    public void setAttachUrl(String attachUrl) {
        this.attachUrl = attachUrl;
    }

    public String getAttachDescription() {
        return attachDescription;
    }

    public void setAttachDescription(String attachDescription) {
        this.attachDescription = attachDescription;
    }

    public ZonedDateTime getAttachedDate() {
        return attachedDate;
    }

    public void setAttachedDate(ZonedDateTime attachedDate) {
        this.attachedDate = attachedDate;
    }

    public VehicleControlsDTO getVehicleControls() {
        return vehicleControls;
    }

    public void setVehicleControls(VehicleControlsDTO vehicleControls) {
        this.vehicleControls = vehicleControls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehicleControlAttachmentsDTO)) {
            return false;
        }

        VehicleControlAttachmentsDTO vehicleControlAttachmentsDTO = (VehicleControlAttachmentsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vehicleControlAttachmentsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleControlAttachmentsDTO{" +
            "id=" + getId() +
            ", attachImage='" + getAttachImage() + "'" +
            ", attachUrl='" + getAttachUrl() + "'" +
            ", attachDescription='" + getAttachDescription() + "'" +
            ", attachedDate='" + getAttachedDate() + "'" +
            ", vehicleControls=" + getVehicleControls() +
            "}";
    }
}
