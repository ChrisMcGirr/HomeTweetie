package actions;

import java.util.Iterator;
import java.util.List;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResourcesCache;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;

import twitter4j.Twitter;

public class lightBulb {
	private final static PHHueSDK phHueSDK = PHHueSDK.getInstance();
	private static PHLight myLight= null;
	
	/*Template Code for the Phillips Hue Light Bulb was taken from the developers page and adapted for our use
	 * http://www.developers.meethue.com/documentation/getting-started
	 * */
	static PHSDKListener  listener = new PHSDKListener() {
			@Override
			public void onAccessPointsFound(List accessPoint) {
				// Handle your bridge search results here.  
				//Typically if multiple results are returned you will want to display them in a list 
	             // and let the user select their bridge.   If one is
			//	found you may opt to connect automatically to that bridge.   
				 System.out.println(" The Number of access points available are here");
				
			}

			@Override
			public void onAuthenticationRequired(PHAccessPoint accessPoint) {
				
			 phHueSDK.startPushlinkAuthentication(accessPoint);
			 //accessPoint.setIpAddress(accessPoint.getIpAddress());
			// phHueSDK.connect(accessPoint);
			  // Arriving here indicates that Pushlinking is required (to prove the User has physical 
			 //access to the bridge).  Typically here
	         // you will display a pushlink image (with a timer) indicating to to the user they need to 
			 //push the button on their bridge within 30 seconds.
			 System.out.println(" In this network, there is requirement for the authentication");
				
				
			}			

			@Override
			public void onConnectionLost(PHAccessPoint accessPoint) {
				// Here you will handle the loss of the connection
				System.out.println("Connection Lost with Phillips Hue Bridge");
			}

			@Override
			public void onConnectionResumed(PHBridge bridge ) {
				// do something 
				System.out.println("Connection Resumed with Phillips Hue Bridge");
			}

			@Override
			public void onError(int code , String message ) {
				// // Here you can handle events such as Bridge Not Responding, 
				//Authentication Failed and Bridge Not Found
				 System.out.println(" The error code"+code+" "+message );
				 //System.out.println(message);
			}

			@Override
			public void onBridgeConnected(PHBridge b ) {
				// here it may say something after connecting to the bridge 
				phHueSDK.setSelectedBridge(b);
				phHueSDK.enableHeartbeat(b, PHHueSDK.HB_INTERVAL);
				System.out.println("Connected to Phillips Hue Bridge");
				
			}

			@Override
			public void onCacheUpdated(int arg0, PHBridge arg1) {
				// here it may some on cache update
				PHBridgeResourcesCache cache= arg1.getResourceCache();
				
				Iterator<PHLight> iterator = cache.getAllLights().iterator();
				
				myLight = (PHLight) iterator.next();
				while(!myLight.getIdentifier().equals("2")){
					myLight = (PHLight) iterator.next();
				}
				System.out.println(myLight);
				System.out.println("Phillips Hue Bridge Cache updated");
				
			}
		 }; 	
	
	/*
	 * Attempts to establish a connection with the phillips hue bridge
	 */
	public static void init_me ()
	{
		phHueSDK.setDeviceName("HomeTweetie");
		phHueSDK.getNotificationManager().registerSDKListener(listener);
		PHAccessPoint accessPoint = new PHAccessPoint();
		accessPoint.setIpAddress("192.168.0.100");
		accessPoint.setUsername("3ae5bbe319f86c4f3db140db1ced93c7");
		phHueSDK.connect(accessPoint);
		/*Have to wait 5 seconds*/
		long count = 0;
		long start = System.currentTimeMillis();
		while((System.currentTimeMillis()-start) < 5000){
			count++;
		}
		System.out.println("Phillips Hue Light Bulb has been Initiated");
	}

	/*Changes the sate of one of the light bulbs to on*/
	public  static void TurnOnLights() {
		// TODO Auto-generated method stub
		PHBridge bridge = PHHueSDK.getInstance().getSelectedBridge();
		PHLightState lightState = new PHLightState(); 
		lightState.setOn(true);
		bridge.updateLightState(myLight, lightState);
		
	}
	/*Changes the sate of one of the light bulbs to off*/
	public static void TurnOffLights(){
		PHBridge bridge = PHHueSDK.getInstance().getSelectedBridge();
		PHLightState lightState = new PHLightState(); 
		lightState.setOn(false);
		
		bridge.updateLightState(myLight, lightState);
}
	

}
