package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class AccessoryElectronics {
    private PowerDistributionPanel pdp;

    public AccessoryElectronics() {
        pdp = new PowerDistributionPanel();
    }

    public PowerDistributionPanel getPDP() {
        return pdp;
    }
}
