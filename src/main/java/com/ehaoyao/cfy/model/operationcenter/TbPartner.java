package com.ehaoyao.cfy.model.operationcenter;

public class TbPartner {
    private Long tbPartnerId;

    private String fdUuid;

    private String partnerKey;

    private String status;

    public Long getTbPartnerId() {
        return tbPartnerId;
    }

    public void setTbPartnerId(Long tbPartnerId) {
        this.tbPartnerId = tbPartnerId;
    }

    public String getFdUuid() {
        return fdUuid;
    }

    public void setFdUuid(String fdUuid) {
        this.fdUuid = fdUuid == null ? null : fdUuid.trim();
    }

    public String getPartnerKey() {
        return partnerKey;
    }

    public void setPartnerKey(String partnerKey) {
        this.partnerKey = partnerKey == null ? null : partnerKey.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}