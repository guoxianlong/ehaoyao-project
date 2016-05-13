package com.ehaoyao.opertioncenter.doctorInHospital.util;


import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
/**
 *	生成二维码工具类
 * @author HuangXiaoxiao
 * @Email huangxiaoxiao@ehaoyao.com
 * @Date 2015年9月17日 下午6:25:03
 * @version V1.0
 */
public class QuickResponseCodeUtil {
	private static final Logger logger = Logger.getLogger(QuickResponseCodeUtil.class);
	 private static final String CHARSET = "utf-8";  
	    private static final String FORMAT_NAME = "JPG";  
	    // 二维码尺寸  
	    private static final int QRCODE_SIZE = 350;  
	    // LOGO宽度  
	    private static final int WIDTH = 60;  
	    // LOGO高度  
	    private static final int HEIGHT = 60;  
	  
	    private static BufferedImage createImage(String content, String imgPath,  
	            boolean needCompress) throws Exception {  
	        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();  
	        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);  
	        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);  
	        hints.put(EncodeHintType.MARGIN, 1);  
	        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,  
	                BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);  
	        int width = bitMatrix.getWidth();  
	        int height = bitMatrix.getHeight();  
	        BufferedImage image = new BufferedImage(width, height,  
	                BufferedImage.TYPE_INT_RGB);  
	        for (int x = 0; x < width; x++) {  
	            for (int y = 0; y < height; y++) {  
	                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000  
	                        : 0xFFFFFFFF);  
	            }  
	        }  
	        if (imgPath == null || "".equals(imgPath)) {  
	            return image;  
	        }  
	        // 插入图片  
	        QuickResponseCodeUtil.insertImage(image, imgPath, needCompress);  
	        return image;  
	    }  
	  
	    /** 
	     * 插入LOGO 
	     *  
	     * @param source 
	     *            二维码图片 
	     * @param imgPath 
	     *            LOGO图片地址 
	     * @param needCompress 
	     *            是否压缩 
	     * @throws Exception 
	     */  
	    private static void insertImage(BufferedImage source, String imgPath,  
	            boolean needCompress) throws Exception {  
	        File file = new File(imgPath);  
	        if (!file.exists()) {  
//	        	logger.error(""+imgPath+" 该文件不存在！");  
//	        	System.out.println(""+imgPath+" 该文件不存在！");  
	            return;  
	        }  
	        Image src = ImageIO.read(new File(imgPath));  
	        int width = src.getWidth(null);  
	        int height = src.getHeight(null);  
	        if (needCompress) { // 压缩LOGO  
	            if (width > WIDTH) {  
	                width = WIDTH;  
	            }  
	            if (height > HEIGHT) {  
	                height = HEIGHT;  
	            }  
	            Image image = src.getScaledInstance(width, height,  
	                    Image.SCALE_SMOOTH);  
	            BufferedImage tag = new BufferedImage(width, height,  
	                    BufferedImage.TYPE_INT_RGB);  
	            Graphics g = tag.getGraphics();  
	            g.drawImage(image, 0, 0, null); // 绘制缩小后的图  
	            g.dispose();  
	            src = image;  
	        }  
	        // 插入LOGO  
	        Graphics2D graph = source.createGraphics();  
	        int x = (QRCODE_SIZE - width) / 2;  
	        int y = (QRCODE_SIZE - height) / 2;  
	        graph.drawImage(src, x, y, width, height, null);  
	        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);  
	        graph.setStroke(new BasicStroke(3f));  
	        graph.draw(shape);  
	        graph.dispose();  
	    }  
	  
	    /** 
	     * 生成二维码(内嵌LOGO) 
	     *  
	     * @param content 
	     *            内容 
	     * @param imgPath 
	     *            LOGO地址 
	     * @param destPath 
	     *            存放目录 
	     * @param needCompress 
	     *            是否压缩LOGO 
	     * @throws Exception 
	     */  
	    public static void encode(String content, String imgPath, String destPath,  
	            boolean needCompress,String name) throws Exception {  
	        BufferedImage image = QuickResponseCodeUtil.createImage(content, imgPath,needCompress);  
	        mkdirs(destPath);  
	        String file = name+".jpg";  
	        ImageIO.write(image, FORMAT_NAME, new File(destPath+"/"+file));  
	    }  
	    
	    public static void main(String[] args) throws Exception {
	    	String content = "http://192.168.98.7:8080/haoyao-core/webrGoodsList.do?method=goodsParticulars&mid=24309&ehyUID=&cyUserState=&userLoginName=&verify=&fd_uuid=mobile&cartkey=a10696f7-2894-4766-b297-30b8a31b9a24&doctorId=1&hospitalId=1";
	    	String imgPath = "E:/encode/123.jpg";
	    	String destPath = "E:/encode";
	    	Boolean needCompress=true;
	    	String name="eg";
	    	System.out.println(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
	    	QuickResponseCodeUtil.encode(content, imgPath, destPath, needCompress,name);
		}
	  
	    /** 
	     * 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常) 
	     * @author lanyuan 
	     * Email: mmm333zzz520@163.com 
	     * @date 2013-12-11 上午10:16:36 
	     * @param destPath 存放目录 
	     */  
	    public static void mkdirs(String destPath) {  
	        File file =new File(destPath);      
	        //当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)  
	        if (!file.exists() && !file.isDirectory()) {  
	            file.mkdirs();  
	        }  
	    }  
	  
	    /** 
	     * 生成二维码(内嵌LOGO) 
	     *  
	     * @param content 
	     *            内容 
	     * @param imgPath 
	     *            LOGO地址 
	     * @param destPath 
	     *            存储地址 
	     * @throws Exception 
	     */  
	    public static void encode(String content, String imgPath, String destPath,String name)  
	            throws Exception {  
	        QuickResponseCodeUtil.encode(content, imgPath, destPath, false,name);  
	    }  
	  
	    /** 
	     * 生成二维码 
	     *  
	     * @param content 
	     *            内容 
	     * @param destPath 
	     *            存储地址 
	     * @param needCompress 
	     *            是否压缩LOGO 
	     * @throws Exception 
	     */  
	    public static void encode(String content, String destPath,  
	            boolean needCompress,String name) throws Exception {  
	        QuickResponseCodeUtil.encode(content, null, destPath, needCompress,name);  
	    }  
	  
	    /** 
	     * 生成二维码 
	     *  
	     * @param content 
	     *            内容 
	     * @param destPath 
	     *            存储地址 
	     * @throws Exception 
	     */  
	    public static void encode(String content, String destPath,String name) throws Exception {  
	        QuickResponseCodeUtil.encode(content, null, destPath, false,name);  
	    }  
	  
	    /** 
	     * 生成二维码(内嵌LOGO) 
	     *  
	     * @param content 
	     *            内容 
	     * @param imgPath 
	     *            LOGO地址 
	     * @param output 
	     *            输出流 
	     * @param needCompress 
	     *            是否压缩LOGO 
	     * @throws Exception 
	     */  
	    public static void encode(String content, String imgPath,  
	            OutputStream output, boolean needCompress) throws Exception {  
	        BufferedImage image = QuickResponseCodeUtil.createImage(content, imgPath,  
	                needCompress);  
	        ImageIO.write(image, FORMAT_NAME, output);  
	    }  
	  
	    /** 
	     * 生成二维码 
	     *  
	     * @param content 
	     *            内容 
	     * @param output 
	     *            输出流 
	     * @throws Exception 
	     */  
	    public static void encode(String content, OutputStream output)  
	            throws Exception {  
	        QuickResponseCodeUtil.encode(content, null, output, false);  
	    }  
	    /**
	     * 生成二维码并返回二维码图片地址
	     * @param content
	     * @return
	     * @throws Exception
	     */
	    public static String encodeUrl(String content,HttpServletRequest request) {
	    	String qrurl="";
	    	try {
	    		
		    	String imgPath = "home/JmeterTest/apache-tomcat-8.0.15/webapps/operation_center/images/doctor/123.jpg";//log地址
		    	String destPath=request.getRealPath("/");
		    	destPath=destPath +File.separator+  "images"+File.separator+"doctor";
//		    	destPath= destPath +  "images"+File.separator+"doctor";//存放目录
		    	logger.info(destPath);
		    	Boolean needCompress=true;//是否压缩
		    	String name=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());//生成图片名字
		    	QuickResponseCodeUtil.encode(content, imgPath, destPath, needCompress,name);
		    	qrurl=name+".jpg";
			
			} catch (Exception e) {
				e.printStackTrace();
			}
	    
	    	return qrurl;
	    }
	    

	   
	
}