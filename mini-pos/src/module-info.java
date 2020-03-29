module com.jdc.pos {
	
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.graphics;
	requires java.sql;
	
	opens com.jdc.pos.views to javafx.fxml;
	opens com.jdc.pos.dto to javafx.base;
	exports com.jdc.pos;
}