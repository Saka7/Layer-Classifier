package edu.lc.beans;

import javafx.beans.property.SimpleIntegerProperty;

/** Training Data Table */
public class TrainingLayerFeatures extends RealLayerFeatures {

  protected final SimpleIntegerProperty type;

  public TrainingLayerFeatures() {
    this(0, 0.0, 0.0, 0.0, 0.0, 0);
  }

  public TrainingLayerFeatures(int number, double sponginess, double amountOfClay,
      double amountOfCarbonate, double vPAmplitude, int type) {
    super(number, sponginess, amountOfCarbonate, amountOfClay, vPAmplitude);
    this.type = new SimpleIntegerProperty(type);
  }
  
  public int getType() {
    return type.get();
  }

  public void setType(int type) {
    this.type.set(type);
  }

  public SimpleIntegerProperty getTypeProperty() {
    return type;
  }

  @Override
  public String toString() {
    return String.format("%d,%f,%f,%f,%f,%d\n",
        super.getNumber(), super.getSponginess(), super.getAmountOfClay(),
        super.getAmountOfCarbonate(), super.getVPAmplitude(), getType());
  }
}