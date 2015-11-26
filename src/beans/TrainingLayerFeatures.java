package beans;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class TrainingLayerFeatures extends RealLayerFeatures {

	protected final SimpleIntegerProperty type;

	public TrainingLayerFeatures() {
		this(0, 0.0, 0.0, 0.0, 0.0, 0);
	}

	public TrainingLayerFeatures(int number, double sponginess, double amountOfClay, double amountOfCarbonate,
			double vPAmplitude, int type) {
		this.number = new SimpleIntegerProperty(number);
		this.sponginess = new SimpleDoubleProperty(sponginess);
		this.amountOfCarbonate = new SimpleDoubleProperty(amountOfCarbonate);
		this.amountOfClay = new SimpleDoubleProperty(amountOfClay);
		this.vPAmplitude = new SimpleDoubleProperty(vPAmplitude);
		this.type = new SimpleIntegerProperty(type);
	}

	// Types's property accessory
	public int getType() {
		return type.get();
	}

	public void setType(int type) {
		this.type.set(type);
	}

	public SimpleIntegerProperty getTypeProperty() {
		return type;
	}
}
