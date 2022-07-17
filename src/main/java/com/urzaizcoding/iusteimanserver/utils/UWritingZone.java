package com.urzaizcoding.iusteimanserver.utils;

import java.awt.Color;

import org.apache.pdfbox.pdmodel.font.PDFont;

public interface UWritingZone {
	String getText();
	ImageZone getImageObject();
	float getXOffset();
	float getYOffset();
	int getTextMaxLineLength();
	PDFont getFont();
	float getFontSize();
	Color getColor();
	Alignment getTextAlignment();
	ZoneContentType getZoneType();
	float getLeading();
	boolean iMultiLined();
	
}
