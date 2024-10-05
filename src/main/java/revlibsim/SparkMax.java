package revlibsim;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel;

import edu.wpi.first.hal.SimBoolean;
import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.hal.SimDevice.Direction;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class SparkMax implements AutoCloseable, MotorController {
  // real motor
  private final CANSparkMax rioimpl;

  // sim values
  private final SimDevice simMotor;
  private final SimDouble simSpeed;
  private final SimBoolean simInverted;

  private final RelativeEncoder encoder;

  /**
   * Create a new SparkMax.
   *
   * @param port The port of the SparkMax.
   * @param type The type of the SparkMax.
   */
  public SparkMax(int port, CANSparkLowLevel.MotorType type) {
    if (RobotBase.isReal()) {
      rioimpl = new CANSparkMax(port, type);
      encoder = rioimpl.getEncoder();
    } else {
      rioimpl = null;
      encoder = new SimEncoder(port);
    }
    simMotor = SimDevice.create("REV:CANSparkMax:Motor", port);
    simSpeed = simMotor.createDouble("speed", Direction.kBidir, 0);
    simInverted = simMotor.createBoolean("inverted", Direction.kBidir, false);

  }

  public double get() {
    if (RobotBase.isReal()) {
      return rioimpl.get();
    }
    return simSpeed.get();
  }

  public double getVoltage() {
    return get() * RobotController.getBatteryVoltage();
  }

  public void set(double speed) {
    if (RobotBase.isReal()) {
      rioimpl.set(simInverted.get() ? -speed : speed);
    }
    simSpeed.set(simInverted.get() ? -speed : speed);
  }

  public void setVoltage(double voltage) {
    set(voltage / RobotController.getBatteryVoltage());
  }

  public boolean getInverted() {
    if (RobotBase.isReal()) {
      return rioimpl.getInverted();
    }
    return simInverted.get();
  }

  public void setInverted(boolean inverted) {
    if (RobotBase.isReal()) {
      rioimpl.setInverted(inverted);
    }
    simInverted.set(inverted);
  }

  public void stopMotor() {
    set(0);
  }

  public void disable() {
    stopMotor();
  }

  public RelativeEncoder getEncoder() {
    return encoder;
  }

  @Override
  public void close() throws Exception {
    rioimpl.close();
    simMotor.close();
  }
}
