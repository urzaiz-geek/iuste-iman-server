package com.urzaizcoding.iusteimanserver.utils.pdf;

public class UMarker {
	
	private float markerX;
	private float markerY;
	
	
	
	public UMarker(float markerX, float markerY) {
		super();
		this.markerX = markerX;
		this.markerY = markerY;
	}
	
	

	public UMarker() {
		super();
	}


	public float getX() {
		return markerX;
	}

	public float getY() {
		return markerY;
	}
	
	public void setX(float x) {
		if(x >= 0) {
			this.markerX = x;
		}
	}
	
	public void setY(float y) {
		if(y >= 0) {
			this.markerY = y;
		}
	}



	@Override
	public String toString() {
		return "UMarker [markerX=" + markerX + ", markerY=" + markerY + "]";
	}
}
