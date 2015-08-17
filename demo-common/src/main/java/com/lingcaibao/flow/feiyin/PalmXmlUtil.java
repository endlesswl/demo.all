package com.lingcaibao.flow.feiyin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
/**
 * <p>标题：流量平台XML报文工具 </p>
 * <p>功能： </p>
 * <p>版权： Copyright (c) 2015</p>
 * <p>公司: 北京零彩宝网络技术有限公司 </p>
 * <p>创建日期：2015年5月12日 下午8:04:13</p>
 * <p>类全名：com.lingcaibao.flow.PalmXmlUtil</p>
 * <p>作者：JIJI </p>
 * <p>@version 1.0</p>
 */
public class PalmXmlUtil
{
	private static Logger		logger			= LoggerFactory.getLogger(PalmXmlUtil.class);
	final static private String	DEFAULT_CHARSET	= "GBK";

	static public Document obj2Xml(Object obj)
	{
		if (obj != null)
		{
			Class<?> cls = obj.getClass();
			Field[] fields = cls.getDeclaredFields();
			Document doc = DocumentHelper.createDocument();
			doc.setName(cls.getName());
			doc.setXMLEncoding(DEFAULT_CHARSET);
			Element root = doc.addElement(cls.getSimpleName());
			Map<String,String> params = Maps.newHashMap();
			for (Field field : fields)
			{
				try
				{
					String name = field.getName();
					String strGet = "get" + name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
					Method methodGet = cls.getDeclaredMethod(strGet);
					Object object = methodGet.invoke(obj);
					String node = name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
					root.addElement(node).setText(object.toString());
					params.put(node, object.toString());
				} catch (Exception ex)
				{
					ex.printStackTrace();
					logger.error("------------ obj to xml err -----------:{}", obj);
				}
			}
			root.addElement("Sign").setText(PalmSignUtil.signAES(params, DEFAULT_CHARSET));
			return doc;
		}
		return null;
	}

	static public <T> T xml2Obj(Document doc, Class<T> obj)
	{
		T t = null;
		if (doc != null)
		{
			try
			{
				t = obj.newInstance();
				Element root = doc.getRootElement();
				List<Element> list = root.elements();
				for (Element node : list)
				{
					String nodeName = node.getName().toLowerCase().replace("_", "");
					Field f = obj.getDeclaredField(nodeName);
					if (f.getType() == List.class)
					{
						List<Object> l = Lists.newArrayList();
						List<Element> datas = node.elements();
						for (Element data : datas)
						{
							String dataName = data.getName();
							Class<?> c = Class.forName(obj.getPackage().getName() + "." + dataName);
							Object o = c.newInstance();
							List<Element> attrs = data.elements();
							for (Element attr : attrs)
							{
								String attrName = attr.getName().toLowerCase();
								String strSet = "set" + attrName.substring(0, 1).toUpperCase() + attrName.substring(1, attrName.length());
								Method methodSet = c.getDeclaredMethod(strSet, String.class);
								methodSet.invoke(o, attr.getText());
							}
							l.add(o);
						}
						String strSet = "set" + nodeName.substring(0, 1).toUpperCase() + nodeName.substring(1, nodeName.length());
						Method methodSet = obj.getDeclaredMethod(strSet, f.getType());
						methodSet.invoke(t, l);
					}
					// 其他信息
					else
					{
						String nodeValue = node.getText();
						System.err.println(nodeName + " = " + nodeValue);
						if (StringUtils.isNotEmpty(nodeValue))
						{
							String strSet = "set" + nodeName.substring(0, 1).toUpperCase() + nodeName.substring(1, nodeName.length());
							Method methodSet = obj.getDeclaredMethod(strSet, String.class);
							methodSet.invoke(t, node.getText());
						}
					}
				}
			} catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return t;
	}

	public List<String>	list;

	public static void main(String[] args) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException
	{
		Document document = DocumentHelper.createDocument();
		document.setName("test");
		document.setXMLEncoding("GBK");
		Element root = document.addElement("OpenCardRsp");
		root.addElement("TransactionId").setText("交易流水号");
		root.addElement("ResultMsg").setText("交易结果响应码");
		root.addElement("ResultCode").setText("应答说明");
		root.addElement("OperatorId").setText("忽略此信息，不用处理");
		Element list = root.addElement("CardInfoList");
		Element card = list.addElement("CardInfo");
		card.addElement("CardCode").setText("卡品编号");
		card.addElement("CardName").setText("卡品名称");
		card.addElement("FlowNumber").setText("流量值");
		card.addElement("CardValue").setText("卡品面值");
		card.addElement("SaledProvinceCode").setText("可销售省代码");
		card.addElement("DiffCount").setText("账户余额");
		card.addElement("OperatorNo").setText("运营商代码标识");
		System.err.println(document.asXML());
		QuerySaleCardRsp rsp = PalmXmlUtil.xml2Obj(document, QuerySaleCardRsp.class);
		System.err.println(JSON.toJSONString(rsp));
	}
}
