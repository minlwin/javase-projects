module com.jdc.hellofx {
	
	exports com.jdc.hellofx;
	
	opens com.jdc.hellofx.controller to javafx.fxml;
	
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.graphics;
}