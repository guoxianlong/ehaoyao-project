package com.haoyao.express.yuantong.javabean.successed;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "waybillNo",
    "uploadTime",
    "processInfo"
})
@XmlRootElement(name = "WaybillProcessInfo")
public class WaybillProcessInfo {

    @XmlElement(name = "Waybill_No", required = true)
    protected String waybillNo;
    @XmlElement(name = "Upload_Time", required = true)
    protected String uploadTime;
    @XmlElement(name = "ProcessInfo", required = true)
    protected String processInfo;

    
    public String getWaybillNo() {
        return waybillNo;
    }

  
    public void setWaybillNo(String value) {
        this.waybillNo = value;
    }

  
    public String getUploadTime() {
        return uploadTime;
    }

   
    public void setUploadTime(String value) {
        this.uploadTime = value;
    }

    public String getProcessInfo() {
        return processInfo;
    }

   
    public void setProcessInfo(String value) {
        this.processInfo = value;
    }

}
