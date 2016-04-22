package edu.nnc.beans;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/** Table Real Data  */
public class RealLayerFeatures {

	protected SimpleIntegerProperty number;
	protected SimpleDoubleProperty sponginess;
	protected SimpleDoubleProperty amountOfClay;
	protected SimpleDoubleProperty amountOfCarbonate; 
	protected SimpleDoubleProperty vPAmplitude;

	public RealLayerFeatures() {
		this(0, 0.0, 0.0, 0.0, 0.0);
	}

	public RealLayerFeatures(int number, double sponginess, double amountOfClay, double amountOfCarbonate,
			double vPAmplitude) {
		this.number = new SimpleIntegerProperty(number);
		this.sponginess = new SimpleDoubleProperty(sponginess);
		this.amountOfCarbonate = new SimpleDoubleProperty(amountOfCarbonate);
		this.amountOfClay = new SimpleDoubleProperty(amountOfClay);
		this.vPAmplitude = new SimpleDoubleProperty(vPAmplitude);
	}

	public int getNumber() {
		return number.get();
	}

	public void setNumber(int number) {
		this.number.set(number);
	}

	public SimpleIntegerProperty getNumberProperty() {
		return number;
	}

	public double getAmountOfCarbonate() {
		return amountOfCarbonate.get();
	}

	public void setAmountOfCarbonate(double amountOfCarbonate) {
		this.amountOfCarbonate.set(amountOfCarbonate);
	}

	public SimpleDoubleProperty getAmountOfCarbonateProperty() {
		return amountOfCarbonate;
	}

	public double getSponginess() {
		return sponginess.get();
	}

	public void setSponginess(double sponginess) {
		this.sponginess.set(sponginess);
	}

	public SimpleDoubleProperty getSponginessProperty() {
		return sponginess;
	}

	public double getVPAmplitude() {
		return vPAmplitude.get();
	}

	public void setVPAmplitude(double vPAmplitude) {
		this.vPAmplitude.set(vPAmplitude);
	}

	public SimpleDoubleProperty getvPAmplitudeProperty() {
		return vPAmplitude;
	}

	public double getAmountOfClay() {
		return amountOfClay.get();
	}

	public void setAmountOfClay(double amountOfClay) {
		this.amountOfClay.set(amountOfClay);
	}

	public SimpleDoubleProperty getAmountOfClayProperty() {
		return amountOfClay;
	}

	@Override
	public String toString() {
		return String.format("%d,%f,%f,%f,%f\n", getNumber(), getSponginess(), getAmountOfClay(),
				getAmountOfCarbonate(), getVPAmplitude());
	}
}
