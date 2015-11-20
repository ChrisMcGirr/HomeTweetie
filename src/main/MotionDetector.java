package main;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import actions.Receiver;

public class MotionDetector {
	private Receiver rvc =  null;

    final private GpioController gpio = GpioFactory.getInstance();
    final private GpioPinDigitalInput myButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_11, PinPullResistance.PULL_UP);
	
	public MotionDetector(Receiver input){
		
		rvc = input;
		
        // create and register gpio pin listener
        myButton.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                // display pin state on console
            	if(event.getState().equals(event.getState().HIGH)){
            		System.out.println("Motion Detected");
            		rvc.getWebCamImage("Motion has been Detected in your home");
            	}  
                try {
					Thread.currentThread();
					Thread.sleep(5*60000); //put to sleep for 5 minutes
				} catch (InterruptedException e) {
					System.out.println("Putting Motion Detector Thread To Sleep Failed");
					e.printStackTrace();
				}
            }
            
        });
        // stop all GPIO activity/threads by shutting down the GPIO controller
        // (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
        // gpio.shutdown();   <--- implement this method call if you wish to terminate the Pi4J GPIO controller
	}

}
