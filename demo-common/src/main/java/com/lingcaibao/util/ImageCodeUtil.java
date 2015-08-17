package com.lingcaibao.util;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;
/**
 * @titie :ImageCodeUtil.java
 * @description : 产生图片验证码
 * @author :hongyang
 * @email :hong.yang@lingcaibao.com
 * @time :2015年3月20日 下午3:42:55
 * @version V1.0
 */
public class ImageCodeUtil {
	private static String code;
	/**
	 * 生成的随机码
	 * @return
	 */
	public static String  getCode(){
		return code;
	}
	/**
	 * 生成的随机的图片
	 * @param width
	 * @param height
	 * @return
	 */
	public static BufferedImage getImage(int width,int height){
		  Color[] colors = { Color.BLUE, Color.RED, Color.BLACK}; 
		  char mapTable[]={
					'a','b','c','d','e','h',
					'j','k','m','n','p','r',
					's','t','u','v','w','x',
					'y','z','2','3','4','5',
					'6','7','8','9'};
		  Font[] fonts=new Font[]{new Font("Atlantic Inline", Font.PLAIN,18),new Font("微软雅黑", Font.PLAIN,18),new Font("华为细黑",Font.PLAIN,18)};
		BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
		Graphics g=image.getGraphics();
		//设定背景颜色
		g.setColor(new Color(0xDCDCDC));
		g.fillRect(0, 0, width, height);
		//画边框
		g.setColor(Color.black);
		g.drawRect(0, 0, width, height);
		//随机产生的验证码
		String strEnsure="";
		//4代表4位验证码，如果要生成等多位的验证码 ，则加大数值
		for(int i=0; i<4; i++) {
			strEnsure += mapTable[new Random().nextInt(mapTable.length)];
		}
		code=strEnsure;
		//将验证码显示在图像中，如果要生成更多位的验证码，增加drawString语句
		g.setColor(Color.black);
		String str = strEnsure.substring(0, 1);
		g.setFont(fonts[new Random().nextInt(fonts.length)]);
		g.setColor(colors[new Random().nextInt(colors.length)]);
		g.drawString(str,8, (height-17)/2+17);
		 str = strEnsure.substring(1, 2);
		 g.setFont(fonts[new Random().nextInt(fonts.length)]);
		 g.setColor(colors[new Random().nextInt(colors.length)]);
		 g.drawString(str, 20, (height-15)/2+15);
		 str = strEnsure.substring(2, 3);
		 g.setFont(fonts[new Random().nextInt(fonts.length)]);
		 g.setColor(colors[new Random().nextInt(colors.length)]);
		 g.drawString(str, 35, (height-18)/2+18);
		 g.setFont(fonts[new Random().nextInt(fonts.length)]);
		 g.setColor(colors[new Random().nextInt(colors.length)]);
		 str = strEnsure.substring(3, 4);
		 g.drawString(str, 45, (height-15)/2+15);
		 //随机产生10个干扰点
		 Random random = new Random();
		 for(int i=0; i<10; i++ ) {
			 int x = random.nextInt(60);
			 int y = random.nextInt(40);
			 g.setColor(colors[new Random().nextInt(colors.length)]);
			 g.drawOval(x, y, 1, 1);
		 }
		 // 随机产生20条干扰线，使图象中的认证码不易被其它程序探测到。  
        g.setColor(Color.BLACK);  
        for (int i = 0; i < 10; i++) {  
            int x = random.nextInt(width);  
            int y = random.nextInt(height);  
            int xl = random.nextInt(12);  
            int yl = random.nextInt(12);  
            g.setColor(colors[new Random().nextInt(colors.length)]);
            g.drawLine(x, y, x + xl, y + yl);  
        }  
		//释放图形上下文
		 g.dispose();
		 //输出图像到页面
	     return image;
	}
}

