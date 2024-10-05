package au.jade.revlibsim;

import com.revrobotics.REVLibError;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.hal.SimBoolean;
import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.hal.SimInt;
import edu.wpi.first.hal.SimDevice.Direction;

public class SimEncoder implements RelativeEncoder {
  private SimDevice impl;

  private SimBoolean inverted;
  private SimDouble position;
  private SimDouble velocity;
  private SimInt averageDepth;
  private SimInt measurementPeriod;
  private SimInt countsPerRevolution;

  // conversion factors
  private SimDouble positionConversionFactor;
  private SimDouble velocityConversionFactor;

  public SimEncoder(int port) {
    impl = SimDevice.create("REVLib:CANSparkMax:Encoder", port);

    inverted = impl.createBoolean("inverted", Direction.kBidir, false);
    position = impl.createDouble("position", Direction.kBidir, 0);
    velocity = impl.createDouble("velocity", Direction.kOutput, 0);
    averageDepth = impl.createInt("averageDepth", Direction.kBidir, 0);
    measurementPeriod = impl.createInt("measurementPeriod", Direction.kBidir, 20);
    countsPerRevolution = impl.createInt("countsPerRevolution", Direction.kOutput, 42);

    positionConversionFactor = impl.createDouble("positionConversionFactor", Direction.kBidir, 1);
    velocityConversionFactor = impl.createDouble("velocityConversionFactor", Direction.kBidir, 1);
  }

  @Override
  public boolean getInverted() {
    return inverted.get();
  }

  @Override
  public REVLibError setInverted(boolean inverted) {
    this.inverted.set(inverted);
    return REVLibError.kOk;
  }

  @Override
  public double getPosition() {
    return position.get();
  }

  @Override
  public REVLibError setPosition(double position) {
    this.position.set(position);
    return REVLibError.kOk;
  }

  @Override
  public double getVelocity() {
    return velocity.get();
  }

  @Override
  public int getAverageDepth() {
    return averageDepth.get();
  }

  @Override
  public REVLibError setAverageDepth(int depth) {
    averageDepth.set(depth);
    return REVLibError.kOk;
  }

  @Override
  public int getMeasurementPeriod() {
    return measurementPeriod.get();
  }

  @Override
  public REVLibError setMeasurementPeriod(int period_ms) {
    measurementPeriod.set(period_ms);
    return REVLibError.kOk;
  }

  @Override
  public int getCountsPerRevolution() {
    return countsPerRevolution.get();
  }

  @Override
  public double getPositionConversionFactor() {
    return positionConversionFactor.get();
  }

  @Override
  public REVLibError setPositionConversionFactor(double factor) {
    positionConversionFactor.set(factor);
    return REVLibError.kOk;
  }

  @Override
  public double getVelocityConversionFactor() {
    return velocityConversionFactor.get();
  }

  @Override
  public REVLibError setVelocityConversionFactor(double factor) {
    velocityConversionFactor.set(factor);
    return REVLibError.kOk;
  }
}
