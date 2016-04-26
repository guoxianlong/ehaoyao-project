package com.ehaoyao.logistics.yto.utils;

import java.io.Reader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

public class JaxbUtil {

	static Logger logger = Logger.getLogger(JaxbUtil.class);

	/**
	 * 传入调用接口返回的xml，转化为StringReader(Reader reader = new
	 * StringReader(xmlString);)，和该接口对应的java bean 返回�?��Object对象,进行强转即可
	 * 
	 * @param xmlString
	 * @param obj
	 * @return
	 */
	
	public static Object createXMLToBean(Reader reader, Class<?> clax) {
		Object object = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(clax);
			// marshaller是类到XML 的转化，那么 unmashaller是XML到类的转化�?
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			object = jaxbUnmarshaller.unmarshal(reader);
		} catch (Exception e) {
//			logger.error("jaxb将xml转换为javabean出现异常:" + e.getMessage());
//			e.printStackTrace();
		}
		return object;
	}

	/**
	 * 传入�?��有数据的Object实体类， 返回 <request> 接口�?��的xml格式的字符串
	 * 
	 * @param object
	 * @return
	 */
	public static String sendBeanToXml(Object object) {
		String xmlString = "";
		try {
			JAXBContext context = JAXBContext.newInstance(object.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
			StringWriter swriter = new StringWriter();
			marshaller.marshal(object, swriter); // 将XML内容打印为字符串
			xmlString = swriter.getBuffer().toString();
		} catch (JAXBException e) {
			logger.error("JavaBean转换xml出现异常:" + e.getMessage());
		}
		return xmlString;
	}
}
