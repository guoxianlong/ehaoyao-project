package com.haoyao.express.yuantong.javabean.successed;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


@XmlRegistry
public class ObjectFactory {

    private final static QName _UploadTime_QNAME = new QName("", "Upload_Time");
    private final static QName _WaybillNo_QNAME = new QName("", "Waybill_No");
    private final static QName _ProcessInfo_QNAME = new QName("", "ProcessInfo");

   
    public ObjectFactory() {
    }

   
    public Result createResult() {
        return new Result();
    }

    
    public WaybillProcessInfo createWaybillProcessInfo() {
        return new WaybillProcessInfo();
    }

  
    public Ufinterface createUfinterface() {
        return new Ufinterface();
    }

    
    @XmlElementDecl(namespace = "", name = "Upload_Time")
    public JAXBElement<String> createUploadTime(String value) {
        return new JAXBElement<String>(_UploadTime_QNAME, String.class, null, value);
    }

    @XmlElementDecl(namespace = "", name = "Waybill_No")
    public JAXBElement<String> createWaybillNo(String value) {
        return new JAXBElement<String>(_WaybillNo_QNAME, String.class, null, value);
    }

   
    @XmlElementDecl(namespace = "", name = "ProcessInfo")
    public JAXBElement<String> createProcessInfo(String value) {
        return new JAXBElement<String>(_ProcessInfo_QNAME, String.class, null, value);
    }

}
