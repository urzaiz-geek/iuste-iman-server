package com.urzaizcoding.iusteimanserver.utils;

import java.awt.Color;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class UWritingZoneImpl implements UWritingZone {
	private ZoneContentType zoneContentType;
	private String text;
	private int textMaxLength;
	private Alignment alignment;
	private ImageZone pdImageXObject;
	private boolean multiline;
	private float xOffset;
	private float yOffset;
	private float fontSize;
	private float leading;
	private Color color;
	private PDFont pdfont;

	public UWritingZoneImpl(ZoneContentType zoneContentType, String text, int textMaxLength, Alignment alignment,
			ImageZone pdImageXObject, boolean multiline, float xOffset, float yOffset, float fontSize, float leading,
			Color color, PDFont pdfont) {
		super();
		this.zoneContentType = zoneContentType;
		this.text = text;
		this.textMaxLength = textMaxLength;
		this.alignment = alignment;
		this.pdImageXObject = pdImageXObject;
		this.multiline = multiline;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.fontSize = fontSize == 0? 10:fontSize;
		this.leading = leading;
		this.color = color == null? Color.BLACK:color;
		this.pdfont = pdfont == null? PDType1Font.COURIER:pdfont;
	}

	public String getText() {
		return text;
	}

	public ImageZone getImageObject() {
		return pdImageXObject;
	}

	public float getXOffset() {
		return xOffset;
	}

	public float getYOffset() {
		return yOffset;
	}

	public PDFont getFont() {
		return pdfont;
	}

	public Color getColor() {
		return color;
	}

	public boolean iMultiLined() {
		return multiline;
	}

	public Alignment getTextAlignment() {
		return alignment;
	}

	public int getTextMaxLineLength() {
		return textMaxLength;
	}

	public float getLeading() {
		// TODO Auto-generated method stub
		return leading;
	}

	public ZoneContentType getZoneType() {
		return zoneContentType;
	}

	public float getFontSize() {
		return fontSize;
	}

	public final void setZoneContentType(ZoneContentType zoneContentType) {
		this.zoneContentType = zoneContentType;
	}

	public final void setText(String text) {
		this.text = text;
	}

	public final void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}

	public final void setPdImageXObject(ImageZone pdImageZone) {
		this.pdImageXObject = pdImageZone;
	}

	public final void setxOffset(float xOffset) {
		this.xOffset = xOffset;
	}

	public final void setyOffset(float yOffset) {
		this.yOffset = yOffset;
	}

	public final void setColor(Color color) {
		this.color = color;
	}

	public void setFont(PDFont font) {
		this.pdfont = font;
	}

	public final void setTextMaxLength(int textMaxLength) {
		this.textMaxLength = textMaxLength;
	}

	public static UWrittingZoneImplBuilder builder() {
		return new UWrittingZoneImplBuilder();
	}

	public static class UWrittingZoneImplBuilder {

		private ZoneContentType zoneContentType;
		private String text;
		private int textMaxLength;
		private Alignment alignment;
		private ImageZone pdImageXObject;
		private float xOffset;
		private float yOffset;
		private float fontSize;
		private float leading;
		private Color color;
		private PDFont pdfont;
		private boolean multiline;

		public UWritingZoneImpl build() throws IllegalArgumentException {

			if (zoneContentType == null) {
				throw new IllegalAccessError("The content type is null");
			} else {
				if (zoneContentType == ZoneContentType.TEXT && text == null) {
					throw new IllegalAccessError("Arguments does not describe any state text is null");
				}

				if (zoneContentType == ZoneContentType.IMAGE && pdImageXObject == null) {
					throw new IllegalAccessError("Arguments does not describe any state text is null");
				}
			}

			return new UWritingZoneImpl(zoneContentType, text, textMaxLength, alignment, pdImageXObject, multiline,
					xOffset, yOffset, fontSize, leading, color, pdfont);

		}

		public UWrittingZoneImplBuilder zoneContentType(ZoneContentType type) {
			this.zoneContentType = type;
			return this;
		}

		public UWrittingZoneImplBuilder text(String text) {
			this.text = text;
			return this;
		}

		public UWrittingZoneImplBuilder textMaxLength(int max) {
			if (max < 0) {
				return this;
			}
			this.textMaxLength = max;
			return this;
		}

		public UWrittingZoneImplBuilder alignment(Alignment alignment) {
			this.alignment = alignment;
			return this;
		}

		public UWrittingZoneImplBuilder leading(float leading) {
			if (leading < 0) {
				return this;
			}

			this.leading = leading;
			return this;
		}

		public UWrittingZoneImplBuilder pdImageZone(ImageZone image) {
			this.pdImageXObject = image;
			return this;
		}

		public UWrittingZoneImplBuilder xOffset(float offset) {
			this.xOffset = offset;
			return this;
		}

		public UWrittingZoneImplBuilder yOffset(float offset) {
			this.yOffset = offset;
			return this;
		}

		public UWrittingZoneImplBuilder color(Color color) {
			this.color = color == null ? Color.BLACK : color;
			return this;
		}

		public UWrittingZoneImplBuilder pdfont(PDFont font) {
			this.pdfont = font == null ? PDType1Font.COURIER : font;
			return this;
		}

		public UWrittingZoneImplBuilder fontSize(float size) {
			this.fontSize = size;
			return this;
		}

		public UWrittingZoneImplBuilder isMultiline(boolean multi) {
			this.multiline = multi;
			return this;
		}

	}

	@Override
	public String toString() {
		return "UWritingZoneImpl [zoneContentType=" + zoneContentType + ", text=" + text + ", textMaxLength="
				+ textMaxLength + ", alignment=" + alignment + ", pdImageXObject=" + pdImageXObject + ", multiline="
				+ multiline + ", xOffset=" + xOffset + ", yOffset=" + yOffset + ", fontSize=" + fontSize + ", leading="
				+ leading + ", color=" + color + ", pdfont=" + pdfont + "]";
	}
	

}
