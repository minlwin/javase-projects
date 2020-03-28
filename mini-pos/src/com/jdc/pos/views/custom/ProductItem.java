package com.jdc.pos.views.custom;

import java.text.DecimalFormat;
import java.util.function.Consumer;

import com.jdc.pos.dto.Product;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

public class ProductItem extends HBox{

	private static final String IMAGE_CONTENT = "M8.286 12.107v-6.393h-2.857v6.393c0 0.313 0.259 0.464 0.571 0.464h1.714c0.312 0 0.571-0.152 0.571-0.464zM4.214 4.571h1.741l-1.125-1.438c-0.098-0.116-0.313-0.277-0.616-0.277-0.473 0-0.857 0.384-0.857 0.857s0.384 0.857 0.857 0.857zM10.357 3.714c0-0.473-0.384-0.857-0.857-0.857-0.304 0-0.518 0.161-0.616 0.277l-1.116 1.438h1.732c0.473 0 0.857-0.384 0.857-0.857zM13.714 6v2.857c0 0.161-0.125 0.286-0.286 0.286h-0.857v3.714c0 0.473-0.384 0.857-0.857 0.857h-9.714c-0.473 0-0.857-0.384-0.857-0.857v-3.714h-0.857c-0.161 0-0.286-0.125-0.286-0.286v-2.857c0-0.161 0.125-0.286 0.286-0.286h3.929c-1.107 0-2-0.893-2-2s0.893-2 2-2c0.598 0 1.152 0.25 1.5 0.688l1.143 1.473 1.143-1.473c0.348-0.438 0.902-0.688 1.5-0.688 1.107 0 2 0.893 2 2s-0.893 2-2 2h3.929c0.161 0 0.286 0.125 0.286 0.286zM8.286 12.107v-6.393h-2.857v6.393c0 0.313 0.259 0.464 0.571 0.464h1.714c0.312 0 0.571-0.152 0.571-0.464zM4.214 4.571h1.741l-1.125-1.438c-0.098-0.116-0.313-0.277-0.616-0.277-0.473 0-0.857 0.384-0.857 0.857s0.384 0.857 0.857 0.857zM10.357 3.714c0-0.473-0.384-0.857-0.857-0.857-0.304 0-0.518 0.161-0.616 0.277l-1.116 1.438h1.732c0.473 0 0.857-0.384 0.857-0.857zM13.714 6v2.857c0 0.161-0.125 0.286-0.286 0.286h-0.857v3.714c0 0.473-0.384 0.857-0.857 0.857h-9.714c-0.473 0-0.857-0.384-0.857-0.857v-3.714h-0.857c-0.161 0-0.286-0.125-0.286-0.286v-2.857c0-0.161 0.125-0.286 0.286-0.286h3.929c-1.107 0-2-0.893-2-2s0.893-2 2-2c0.598 0 1.152 0.25 1.5 0.688l1.143 1.473 1.143-1.473c0.348-0.438 0.902-0.688 1.5-0.688 1.107 0 2 0.893 2 2s-0.893 2-2 2h3.929c0.161 0 0.286 0.125 0.286 0.286z";
	private static final String SOLD_OUT = "M5.714 12.571c0-0.625-0.518-1.143-1.143-1.143s-1.143 0.518-1.143 1.143 0.518 1.143 1.143 1.143 1.143-0.518 1.143-1.143zM2.286 8h3.429v-2.286h-1.411c-0.036 0-0.17 0.054-0.196 0.080l-1.741 1.741c-0.027 0.027-0.080 0.161-0.080 0.196v0.268zM13.714 12.571c0-0.625-0.518-1.143-1.143-1.143s-1.143 0.518-1.143 1.143 0.518 1.143 1.143 1.143 1.143-0.518 1.143-1.143zM16 2.857v9.143c0 0.661-0.696 0.571-1.143 0.571 0 1.259-1.027 2.286-2.286 2.286s-2.286-1.027-2.286-2.286h-3.429c0 1.259-1.027 2.286-2.286 2.286s-2.286-1.027-2.286-2.286h-0.571c-0.446 0-1.143 0.089-1.143-0.571 0-0.313 0.259-0.571 0.571-0.571v-2.857c0-0.634-0.089-1.339 0.402-1.83l1.768-1.768c0.223-0.223 0.652-0.402 0.973-0.402h1.429v-1.714c0-0.313 0.259-0.571 0.571-0.571h9.143c0.313 0 0.571 0.259 0.571 0.571z";
	
	private static DecimalFormat DF = new DecimalFormat("#,##0");
	
	public ProductItem(Product product, Consumer<Product> clickListener) {
		
		getStyleClass().add("product-card");
		
		VBox iconBox = new VBox();
		iconBox.getStyleClass().add("icon-box");
		
		SVGPath icon = new SVGPath();
		icon.setContent(product.isSoldOut() ? SOLD_OUT : IMAGE_CONTENT);
		iconBox.getChildren().add(icon);
		
		getChildren().add(iconBox);
		
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
