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
	
    /*
     * Constructor Method initiates and starts the motion detector thread. Simply creating a new object starts the thread.
     * Although this should be a singleton object, it has not be implemented as such. Therefore, do not create more than
     * one object instance. 
     */
	public MotionDetector(Receiver input){
		
		rvc = input;
		
		/*add a listener. This code was adapted from the Pi4J Example Section
		 * http://pi4j.com/example/listener.html since the Pi4J library was used
		 * */
        myButton.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
            	/*When input goes high this means motion has been detected*/
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

        //Can be used to shutdown the use of the GPIOs
        // gpio.shutdown();  
	}

}
