package com.ehaoyao.logistics.yto.vo;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "waybillProcessInfo"
})
@XmlRootElement(name = "Result")
public class Result {

    @XmlElement(name = "WaybillProcessInfo", required = true)
    protected List<WaybillProcessInfo> waybillProcessInfo;

  
    public List<WaybillProcessInfo> getWaybillProcessInfo() {
        if (waybillProcessInfo == null) {
            waybillProcessInfo = new ArrayList<WaybillProcessInfo>();
        }
        return this.waybillProcessInfo;
    }

}
