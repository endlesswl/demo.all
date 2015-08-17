package com.lingcaibao.flow.oufei;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.collect.Lists;
public class SPXmlUtil
{
	private static Logger	logger	= LoggerFactory.getLogger(SPXmlUtil.class);

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
}
