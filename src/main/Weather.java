package main;

import java.io.IOException;

import org.json.JSONException;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.CurrentWeather.Main;
import net.aksingh.owmjapis.OpenWeatherMap;

public class Weather {

	private static float threshold = 18;
	private static Main weather = null;
	
	public Weather(String City, float threshold){
		this.threshold = (threshold*9)/5+32;
        // declaring object of "OpenWeatherMap" class
       OpenWeatherMap owm = new OpenWeatherMap("");
       owm.setApiKey("ffccc8ed983eb48e66409bf5564f9adc");
       CurrentWeather cwd =null;
       try {
           cwd = owm.currentWeatherByCityName(City);
       } catch (JSONException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       } catch (IOException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
       weather = cwd.getMainInstance();
	}
	
	public static boolean isCold(){
       if(weather.getTemperature() <= threshold){
    	   return true;
       }
       else{
    	   return false;
       }
	}
}
