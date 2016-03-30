package com.demo.util;

import java.io.File;
import java.math.BigDecimal;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class ImageUtil {
	/**
	 * 判断是否是图片文件
	 * 
	 * @param o
	 * @return
	 */
	public static boolean isImageFile(File f) {
		if (f.exists()) {
			try {
				ImageInputStream iis = ImageIO.createImageInputStream(f);
				Iterator<?> iter = ImageIO.getImageReaders(iis);
				if (!iter.hasNext()) {
					return false;
				}
				ImageReader reader = (ImageReader) iter.next();
				iis.close();
				String suf = ("" + reader.getFormatName()).toLowerCase();

				String[] sufs = { "jpg", "gif", "bmp", "png", "jpeg" };
				for (String s : sufs) {
					if (s.equals(suf))
						return true;
				}
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
		return false;
	}
	
	/**
	 * 计算分辨率
	 * 
	 * @param args
	 */
	public static Double getRatio(Integer width, Integer height) {
		Double ratio = 0d;
		BigDecimal bd1 = new BigDecimal(Double.toString(width));
		BigDecimal bd2 = new BigDecimal(Double.toString(height));
		ratio = bd1.divide(bd2, 4, BigDecimal.ROUND_HALF_UP).doubleValue();
		return ratio;
	}
}
