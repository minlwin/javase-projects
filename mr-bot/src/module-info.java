module com.jdc.bot {
	
	exports com.jdc.bot;
	
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.graphics;
	
	opens com.jdc.bot to javafx.fxml;
}