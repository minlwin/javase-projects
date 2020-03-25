module com.jdc.pos {
	
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.graphics;
	requires java.sql;
	
	opens com.jdc.pos.views to javafx.fxml;
	exports com.jdc.pos;
}