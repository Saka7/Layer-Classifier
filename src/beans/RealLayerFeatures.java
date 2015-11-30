package beans;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/** Запис для таблиці Реальні дані */
public class RealLayerFeatures {

	protected SimpleIntegerProperty number; // Номер об'єкта
	protected SimpleDoubleProperty sponginess; // Пористість
	protected SimpleDoubleProperty amountOfClay; // Глинистість
	protected SimpleDoubleProperty amountOfCarbonate; // Карбонатність
	protected SimpleDoubleProperty vPAmplitude; // Амплітуда ВП

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

	// Аксесори для Номеру об'єкта
	public int getNumber() {
		return number.get();
	}

	public void setNumber(int number) {
		this.number.set(number);
	}

	public SimpleIntegerProperty getNumberProperty() {
		return number;
	}

	// Аксесори для Карбонатності
	public double getAmountOfCarbonate() {
		return amountOfCarbonate.get();
	}

	public void setAmountOfCarbonate(double amountOfCarbonate) {
		this.amountOfCarbonate.set(amountOfCarbonate);
	}

	public SimpleDoubleProperty getAmountOfCarbonateProperty() {
		return amountOfCarbonate;
	}

	// Аксесори для Пористості
	public double getSponginess() {
		return sponginess.get();
	}

	public void setSponginess(double sponginess) {
		this.sponginess.set(sponginess);
	}

	public SimpleDoubleProperty getSponginessProperty() {
		return sponginess;
	}

	// Аксесори для Амплітуди ВП
	public double getVPAmplitude() {
		return vPAmplitude.get();
	}

	public void setVPAmplitude(double vPAmplitude) {
		this.vPAmplitude.set(vPAmplitude);
	}

	public SimpleDoubleProperty getvPAmplitudeProperty() {
		return vPAmplitude;
	}

	// Аксесори для Глинистості
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
