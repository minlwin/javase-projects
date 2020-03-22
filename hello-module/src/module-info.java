module javafx.hello {
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.graphics;
	
	opens com.jdc.hello to javafx.fxml;
	exports com.jdc.hello;
}