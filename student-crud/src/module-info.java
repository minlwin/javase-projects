module com.jdc.students {
	exports com.jdc.student;
	
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.graphics;
	
	opens com.jdc.student.views to javafx.fxml;
	opens com.jdc.student.model to javafx.base;
}