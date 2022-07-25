package com.urzaizcoding.iusteimanserver.utils.pdf;

import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class ImageZone {
	
	private PDImageXObject image;
	private float width;
	private float height;
	
	
	
	public ImageZone(PDImageXObject image, float width, float height) {
		super();
		this.image = image;
		this.width = width;
		this.height = height;
	}
	
	
	PDImageXObject getImage() {
		return image;
	}
	float getWidth() {
		return width;
	}
	float getHeigth() {
		return height;
	}


	@Override
	public String toString() {
		return "ImageZone [image=" + image + ", width=" + width + ", height=" + height + "]";
	}
}
