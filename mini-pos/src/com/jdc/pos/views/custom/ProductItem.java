package com.jdc.pos.views.custom;

import java.text.DecimalFormat;
import java.util.function.Consumer;

import com.jdc.pos.dto.Product;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

public class ProductItem extends HBox{

	private static String IMAGE_CONTENT = "M8.286 12.107v-6.393h-2.857v6.393c0 0.313 0.259 0.464 0.571 0.464h1.714c0.312 0 0.571-0.152 0.571-0.464zM4.214 4.571h1.741l-1.125-1.438c-0.098-0.116-0.313-0.277-0.616-0.277-0.473 0-0.857 0.384-0.857 0.857s0.384 0.857 0.857 0.857zM10.357 3.714c0-0.473-0.384-0.857-0.857-0.857-0.304 0-0.518 0.161-0.616 0.277l-1.116 1.438h1.732c0.473 0 0.857-0.384 0.857-0.857zM13.714 6v2.857c0 0.161-0.125 0.286-0.286 0.286h-0.857v3.714c0 0.473-0.384 0.857-0.857 0.857h-9.714c-0.473 0-0.857-0.384-0.857-0.857v-3.714h-0.857c-0.161 0-0.286-0.125-0.286-0.286v-2.857c0-0.161 0.125-0.286 0.286-0.286h3.929c-1.107 0-2-0.893-2-2s0.893-2 2-2c0.598 0 1.152 0.25 1.5 0.688l1.143 1.473 1.143-1.473c0.348-0.438 0.902-0.688 1.5-0.688 1.107 0 2 0.893 2 2s-0.893 2-2 2h3.929c0.161 0 0.286 0.125 0.286 0.286zM8.286 12.107v-6.393h-2.857v6.393c0 0.313 0.259 0.464 0.571 0.464h1.714c0.312 0 0.571-0.152 0.571-0.464zM4.214 4.571h1.741l-1.125-1.438c-0.098-0.116-0.313-0.277-0.616-0.277-0.473 0-0.857 0.384-0.857 0.857s0.384 0.857 0.857 0.857zM10.357 3.714c0-0.473-0.384-0.857-0.857-0.857-0.304 0-0.518 0.161-0.616 0.277l-1.116 1.438h1.732c0.473 0 0.857-0.384 0.857-0.857zM13.714 6v2.857c0 0.161-0.125 0.286-0.286 0.286h-0.857v3.714c0 0.473-0.384 0.857-0.857 0.857h-9.714c-0.473 0-0.857-0.384-0.857-0.857v-3.714h-0.857c-0.161 0-0.286-0.125-0.286-0.286v-2.857c0-0.161 0.125-0.286 0.286-0.286h3.929c-1.107 0-2-0.893-2-2s0.893-2 2-2c0.598 0 1.152 0.25 1.5 0.688l1.143 1.473 1.143-1.473c0.348-0.438 0.902-0.688 1.5-0.688 1.107 0 2 0.893 2 2s-0.893 2-2 2h3.929c0.161 0 0.286 0.125 0.286 0.286z";
	private static DecimalFormat DF = new DecimalFormat("#,##0");
	
	public ProductItem(Product product, Consumer<Product> clickListener) {
		
		getStyleClass().add("product-card");
		
		VBox iconBox = new VBox();
		iconBox.getStyleClass().add("icon-box");
		
		SVGPath icon = new SVGPath();
		icon.setContent(IMAGE_CONTENT);
		iconBox.getChildren().add(icon);
		
		getChildren().add(icon);
		
		VBox dataBox = new VBox();
		dataBox.getChildren().add(new Label(product.getProduct()));
		dataBox.getChildren().add(new Label(product.getCategory()));
		dataBox.getChildren().add(new Label(String.format("%s MMK", DF.format(product.getPrice()))));
		
		getChildren().add(dataBox);
		
		setOnMouseClicked(event -> {
			
			if(event.getClickCount() == 2) {
				clickListener.accept(product);
			}
		});
	}
}
