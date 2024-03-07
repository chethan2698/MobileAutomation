package Android.javaclient8;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class MobileActions {

	public void Tap(WebElement element, AndroidDriver driver) {

		Point location = element.getLocation();
		Dimension size = element.getSize();
		Point centerOfElement = getCenterOfElement(location, size);

		PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "finger1");

		Sequence sequence = new Sequence(finger1, 1);
		sequence.addAction(finger1.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerOfElement));
		sequence.addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		sequence.addAction(new Pause(finger1, Duration.ofMillis(200)));
		sequence.addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

		driver.perform(Collections.singletonList(sequence));
		
	}
	
	public void TapGesture(WebElement element, AndroidDriver driver) {

		driver.executeScript("mobile: clickGesture", ImmutableMap.of("elementId", ((RemoteWebElement) element).getId()));
	}
	
	public void doubleTap(WebElement element, AndroidDriver driver) {

		Point location = element.getLocation();
		Dimension size = element.getSize();
		Point centerOfElement = getCenterOfElement(location, size);

		PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "finger1");

		Sequence sequence = new Sequence(finger1, 1);
		sequence.addAction(finger1.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerOfElement));
		sequence.addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		sequence.addAction(new Pause(finger1, Duration.ofMillis(100)));
		sequence.addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
		sequence.addAction(new Pause(finger1, Duration.ofMillis(100)));
		sequence.addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		sequence.addAction(new Pause(finger1, Duration.ofMillis(100)));
		sequence.addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

		driver.perform(Collections.singletonList(sequence));
	}
	
	public void DoubleClickGesture(WebElement element, AndroidDriver driver) {

		((JavascriptExecutor) driver).executeScript("mobile: doubleClickGesture", ImmutableMap.of("elementId", ((RemoteWebElement) element).getId()
			));
	}
	
	public void longPress(WebElement element, AndroidDriver driver) {

		Point location = element.getLocation();
		Dimension size = element.getSize();
		Point centerOfElement = getCenterOfElement(location, size);

		PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "finger1");

		Sequence sequence = new Sequence(finger1, 1);
		sequence.addAction(finger1.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerOfElement));
		sequence.addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		sequence.addAction(new Pause(finger1, Duration.ofSeconds(2)));
		sequence.addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

		driver.perform(Collections.singletonList(sequence));
	}
	
	public void longPressGesture(WebElement element, AndroidDriver driver) {

		((JavascriptExecutor) driver).executeScript("mobile: longClickGesture", ImmutableMap.of(
			    "elementId", ((RemoteWebElement) element).getId()
			));
		
	}
			
	public void ScrollDown(AndroidDriver driver) {

		Dimension size = driver.manage().window().getSize();
		int srartX = size.getWidth() / 2 ;
		int srartY = size.getHeight() / 2 ;
		
		int endX = srartX ;
		int endY = (int) (size.getHeight() * 0.25 ) ;
		
		PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "finger1");

		Sequence sequence = new Sequence(finger1, 1);
		sequence.addAction(finger1.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), srartX, srartY));
		sequence.addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		sequence.addAction(new Pause(finger1, Duration.ofMillis(200)));
		sequence.addAction(finger1.createPointerMove(Duration.ofMillis(100), PointerInput.Origin.viewport(), endX, endY));
		sequence.addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

		driver.perform(Collections.singletonList(sequence));
	}
	
	public void ScrollGesture(AndroidDriver driver, String value) {

		String x = "new UiScrollable(new UiSelector()).scrollIntoView(text(\"ElementName\"));";
		String element = "";
		if(x.contains("ElementName")) {
			element = x.replace("ElementName", value);
		}
		driver.findElement(AppiumBy.androidUIAutomator(element));
		
	}
	
	public void Scroll(AndroidDriver driver, String value, double length) {
		// Java
		boolean canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
		    "left", 100, "top", 100, "width", 200, "height", 200,
		    "direction", value,
		    "percent", length
		));
	}
			
	public void DragAndDrop(Point source, Point target , AndroidDriver driver) {


		PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "finger1");

		Sequence sequence = new Sequence(finger1, 1);
		sequence.addAction(finger1.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), source));
		sequence.addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		sequence.addAction(new Pause(finger1, Duration.ofMillis(500)));
		sequence.addAction(finger1.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), target));
		sequence.addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
		
		driver.perform(Collections.singletonList(sequence));
	}
	
	public void zoom(Point centerOfElement, AndroidDriver driver) {

		PointerInput finger1 = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
		PointerInput finger2 = new PointerInput(PointerInput.Kind.TOUCH, "finger2");

		Sequence sequence1 = new Sequence(finger1, 1);
		sequence1.addAction(finger1.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerOfElement));
		sequence1.addAction(finger1.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		sequence1.addAction(new Pause(finger1, Duration.ofSeconds(2)));
		sequence1.addAction(finger1.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerOfElement.getX() + 100,
				centerOfElement.getY() - 100));		
		sequence1.addAction(finger1.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
		
		Sequence sequence2 = new Sequence(finger2, 1);
		sequence2.addAction(finger2.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerOfElement));
		sequence2.addAction(finger2.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
		sequence2.addAction(new Pause(finger2, Duration.ofSeconds(2)));
		sequence2.addAction(finger2.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerOfElement.getX() - 100,
				centerOfElement.getY() + 100));		
		sequence2.addAction(finger2.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
		

		driver.perform(Arrays.asList(sequence1, sequence2));
	}
	
	public void swipeLeft(WebElement element, AndroidDriver driver) {
		
	    int centerY = element.getRect().y + (element.getSize().height/2);
        double startX = element.getRect().x + (element.getSize().width * 0.9);
        double endX = element.getRect().x + (element.getSize().width * 0.1);
        
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH,"finger");
        Sequence swipe = new Sequence(finger,1);
        swipe.addAction(finger.createPointerMove(Duration.ofSeconds(0),PointerInput.Origin.viewport(),(int)startX,centerY));
        swipe.addAction(finger.createPointerDown(0));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(700),PointerInput.Origin.viewport(),(int)endX,centerY)); 
        swipe.addAction(finger.createPointerUp(0));

        //Perform the actions
        driver.perform(Arrays.asList(swipe));

	}
	
	public void swipeRight(WebElement element, AndroidDriver driver) {
				
	    int centerY = element.getRect().y + (element.getSize().height/2);
        double startX = element.getRect().x + (element.getSize().width * 0.1);
        double endX = element.getRect().x + (element.getSize().width * 0.9);
        
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH,"finger");
        Sequence swipe = new Sequence(finger,1);
        swipe.addAction(finger.createPointerMove(Duration.ofSeconds(0),PointerInput.Origin.viewport(),(int)startX,centerY));
        swipe.addAction(finger.createPointerDown(0));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(),(int)endX,centerY));
        swipe.addAction(finger.createPointerUp(0));

        //Perform the actions
        driver.perform(Arrays.asList(swipe));

	}
	
	public void SwipeGesture(WebElement element, AndroidDriver driver, String direction, double percentage) {

		((JavascriptExecutor) driver).executeScript("mobile: swipeGesture", ImmutableMap.of(
			    "elementId", ((RemoteWebElement) element).getId(),
			    "direction", direction,
			    "percent", percentage
			));
		
	}

	public Point getCenterOfElement(Point location, Dimension size) {

		return new Point(location.getX() + size.getWidth() / 2, location.getY() + size.getHeight() / 2);

	}

}
