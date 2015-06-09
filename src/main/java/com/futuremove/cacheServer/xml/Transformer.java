package com.futuremove.cacheServer.xml;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.Attribute;
import org.w3c.dom.Attr;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;


/**
 * Created by jessie on 2015/6/9.
 */
public class Transformer {
     public static Entity transformXmlToEntity(String xml) throws Exception {
         SAXReader saxReader = new SAXReader();
         Document document = saxReader.read(new ByteArrayInputStream(xml.getBytes("utf8")));
         Element rootEle = document.getRootElement();
         return parseElement(rootEle,0);
     }

    public static Entity parseElement(Element ele,int level){
        Entity entity = new Entity();
        entity.name = ele.getName();
        entity.content = ele.getTextTrim();
        entity.level = level;
        if(ele.attributeCount()>0) {
            entity.attributes = new HashMap<String, String>();
            for(int i=0;i<ele.attributeCount();i++) {
                Attribute attr = ele.attribute(i);
                entity.attributes.put(attr.getName(), attr.getText());
            }
        }
        List<Element> cEs = ele.elements();
        if(cEs.size()>0) {
            entity.children = new HashMap<String, Entity>();
            for(int i=0;i<cEs.size();i++) {
                entity.children.put(cEs.get(i).getName(),parseElement(cEs.get(i),level+1));
            }
        }
        return entity;
    }
    public static void main(String [] args) throws  Exception {
       String testXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
               "<data>\n" +
               "  <message>\n" +
               "    <status>0</status>\n" +
               "    <value>处理成功</value>\n" +
               "  </message>\n" +
               "  <policeCheckInfos>\n" +
               "    <policeCheckInfo name=\"彭权艺\" id=\"431227199305110028\">\n" +
               "      <message>\n" +
               "        <status>0</status>\n" +
               "        <value>查询成功</value>\n" +
               "      </message>\n" +
               "      <name desc=\"姓名\">彭权艺</name>\n" +
               "      <identitycard desc=\"身份证号\">431227199305110028</identitycard>\n" +
               "      <compStatus desc=\"比对状态\">3</compStatus>\n" +
               "      <compResult desc=\"比对结果\">一致</compResult>\n" +
               "      <policeadd desc=\"原始发证地\">湖南省新晃侗族自治县</policeadd>\n" +
               "      <birthday2 desc=\"出生日期2\">19930511</birthday2>\n" +
               "      <sex2 desc=\"性别2\">女</sex2>\n" +
               "    </policeCheckInfo>\n" +
               "  </policeCheckInfos>\n" +
               "</data>";
        Entity reEntity = transformXmlToEntity(testXml);
        System.out.println(reEntity.dfsFind("policeCheckInfo"));
        return ;
    }
}
