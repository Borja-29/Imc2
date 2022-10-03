import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class App extends Application {

	private Label pesoLabel;
	private Label kgLabel;
	private Label alturaLabel;
	private Label cmLabel;
	private Label imcLabel;
	private Label valorImc;
	private Label condicionLabel;

	private TextField pesoField;
	private TextField alturaField;

	private HBox pesoBox;
	private HBox alturaBox;
	private HBox imcBox;

	private VBox root;

	private StringProperty strPeso = new SimpleStringProperty();
	private StringProperty strAltura = new SimpleStringProperty();
	private StringProperty strImc = new SimpleStringProperty();
	private StringProperty strCondicion = new SimpleStringProperty();

	private DoubleProperty cantPeso = new SimpleDoubleProperty(0);
	private DoubleProperty cantAltura = new SimpleDoubleProperty(0);
	private DoubleProperty cantImc = new SimpleDoubleProperty(0);

	@Override
	public void start(Stage primaryStage) throws Exception {

		pesoLabel = new Label();
		pesoLabel.setText("Peso:");

		alturaLabel = new Label();
		alturaLabel.setText("Altura:");

		kgLabel = new Label();
		kgLabel.setText("kg");

		cmLabel = new Label();
		cmLabel.setText("cm");

		imcLabel = new Label();
		imcLabel.setText("IMC: ");

		valorImc = new Label();

		condicionLabel = new Label();
		condicionLabel.setText("Condición");

		pesoField = new TextField();
		pesoField.setMaxWidth(100);

		alturaField = new TextField();
		alturaField.setMaxWidth(100);

		pesoBox = new HBox(5);
		pesoBox.setAlignment(Pos.CENTER);
		pesoBox.getChildren().addAll(pesoLabel, pesoField, kgLabel);

		alturaBox = new HBox(5);
		alturaBox.setAlignment(Pos.CENTER);
		alturaBox.getChildren().addAll(alturaLabel, alturaField, cmLabel);

		imcBox = new HBox();
		imcBox.setAlignment(Pos.CENTER);
		imcBox.getChildren().addAll(imcLabel, valorImc);

		root = new VBox();
		root.setAlignment(Pos.CENTER);
		root.setSpacing(5);
		root.setFillWidth(false);
		root.getChildren().addAll(pesoBox, alturaBox, imcBox, condicionLabel);

		Scene scene = new Scene(root, 320, 200);
		primaryStage.setScene(scene);
		primaryStage.setTitle("IMC");
		primaryStage.show();

		// bindings

		pesoField.textProperty().bindBidirectional(strPeso);
		alturaField.textProperty().bindBidirectional(strAltura);
		valorImc.textProperty().bindBidirectional(strImc);
		condicionLabel.textProperty().bindBidirectional(strCondicion);

		strPeso.bindBidirectional(cantPeso, new NumberStringConverter());
		strAltura.bindBidirectional(cantAltura, new NumberStringConverter());
		strImc.bindBidirectional(cantImc, new NumberStringConverter());
		cantImc.bind(cantPeso.divide(((cantAltura.divide(100)).multiply((cantAltura.divide(100))))));
		cantImc.addListener((o, ov, nv) -> {
			double i = nv.doubleValue();
			if (i < 18.5) {
				strCondicion.set("Bajo Peso");
			} else if (i >= 18.5 && i < 25) {
				strCondicion.set("Normal");
			} else if (i >= 25 && i < 30) {
				strCondicion.set("Sobrepeso");
			} else {
				strCondicion.set("Obeso");
			}
		});

	}

	public static void main(String[] args) {
		launch(args);
	}

}
