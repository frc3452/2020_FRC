package frc.robot.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Camera extends SubsystemBase {

    private UsbCamera camera1;
    private UsbCamera camera2;
    private boolean cam1 = true;
    VideoSink server;


    public Camera(boolean skipConstruction) {
        if (skipConstruction) {
            return;
        }
        camera1 = CameraServer.getInstance().startAutomaticCapture(0);
        camera2 = CameraServer.getInstance().startAutomaticCapture(1);
        server = CameraServer.getInstance().getServer();
        camera1.setFPS(30);
        camera1.setResolution(320, 160);
        camera2.setFPS(30);
        camera2.setResolution(320, 160);
    }


    public void changeCameras() {

        if (cam1) {
            System.out.println("Switching to camera 2");
            server.setSource(camera2);
            cam1 = false;
        } else {
            System.out.println("Switching to camera 1");
            server.setSource(camera1);
            cam1 = true;
        }
    }

}