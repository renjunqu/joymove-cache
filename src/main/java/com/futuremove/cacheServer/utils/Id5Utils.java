package com.futuremove.cacheServer.utils;

import cn.id5.gboss.businesses.validator.service.app.*;

import java.io.ByteArrayInputStream;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import com.futuremove.cacheServer.xml.Entity;
import com.futuremove.cacheServer.xml.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
/**
 * Created by qurj on 15/6/9.
 */
public class Id5Utils {
    final static Logger logger = LoggerFactory.getLogger(Id5Utils.class);

    public static final String key = "12345678";
    public  static String username = "feichijk";
    public  static String password = "888888";
    public  static String type = "1A020201";
    static {
        try {
            System.setProperty("sun.net.client.defaultReadTimeout", "9000");
            System.setProperty("sun.net.client.defaultConnectTimeout", "6000");
            username = encode(key, username.getBytes("gbk"));
            password = encode(key,password.getBytes("gbk"));
            type = encode(key,type.getBytes("gbk"));
        } catch (Exception e) {

        }
    }



    public static String encode(String key, byte[] data) throws Exception {
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // key的长度不能够小于8位字节
        Key secretKey = keyFactory.generateSecret(desKeySpec);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());// 向量
        AlgorithmParameterSpec paramSpec = iv;
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
        byte[] bytes = cipher.doFinal(data);
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(bytes);
    }

    public static byte[] decode(String key, String data) throws Exception {
        try {
            SecureRandom sr = new SecureRandom();
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key 的长度不能够小于 的长度不能够小于 8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");//ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec("12345678".getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
            BASE64Decoder decoder = new BASE64Decoder();
            return cipher.doFinal(decoder.decodeBuffer(data));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }



    public static Map<String,String> checkXmlResult(String xml) throws  Exception {
        Map<String,String> infoMap = null;
        Entity xmlEntity = Transformer.transformXmlToEntity(xml);
        Integer processStatus = xmlEntity.dfsFind("status")==null?0:Integer.parseInt(xmlEntity.dfsFind("status").content);

        if(processStatus==0) {
            Entity policeCheckInfo = xmlEntity.dfsFind("policeCheckInfo");
            Integer pcStatus = Integer.parseInt(policeCheckInfo.dfsFind("status").content);
            Integer compStatus = Integer.parseInt(policeCheckInfo.dfsFind("compStatus").content);
            if(pcStatus==0 && compStatus==3) {
                infoMap = new HashMap<String,String>();
                String sex2 = policeCheckInfo.dfsFind("sex2").content;
                String idNo = policeCheckInfo.dfsFind("identitycard").content;
                String idName = policeCheckInfo.dfsFind("name").content;
                logger.error("check ok");
                //logger.error(sex2);
                infoMap.put("sex",sex2);
                infoMap.put("idNo",idNo);
                infoMap.put("idName",idName);
            } else {
                // 比较结果出错
                logger.error("comp failed");
            }
        } else {
            //process Status not 0
            System.out.print("called web service error");
        }
        return infoMap;
    }

    public  static Map<String,String>  Id5Check(String idName,String idNo)throws  Exception{
        QueryValidatorServicesService serviceProxy = new QueryValidatorServicesService();
        QueryValidatorServices port = serviceProxy.getPort(QueryValidatorServices.class);
        String resultXml = new String(decode(key,port.querySingle(username, password, type,
                encode(key, (idName + "," + idNo).getBytes("gbk"))
        )),"gbk");
        logger.error(resultXml);
        return checkXmlResult(resultXml);
    }


        public static void main(String [] args) throws  Exception {
            Id5Check(args[0],args[1]);
           // Id5Check("sdfsdf","sdfsdf");
        }
}
