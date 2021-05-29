package com.jayfella.jme.jfx;

import java.io.IOException;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.app.state.ConstantVerifierState;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Erwin Müller {@literal <erwin@mullerlpublic.de}
 */
@Slf4j
public class JavaFxUIFXMLTest extends SimpleApplication {

    public static void main(String[] args) {
        var app = new JavaFxUIFXMLTest();
        app.start();
    }

    private VBox panel;

    private TestController controller;

    public JavaFxUIFXMLTest() {
        super(new StatsAppState(), new DebugKeysAppState(), new ConstantVerifierState());
    }

    public void setupUi() throws IOException {
        var loader = new FXMLLoader();
        this.panel = (VBox) loader.load(getClass().getResourceAsStream("/test.fxml"));
        log.debug("Load test.fxml panel: {}", panel);
        this.controller = (TestController) loader.getController();
        log.debug("Load test.fxml controller: {}", controller);
    }

    @Override
    public void simpleInitApp() {
        JavaFxUI.initialize(this, getClass().getResource("/test.css").toString());
        JavaFxUI.getInstance().runInJavaFxThread(() -> {
            try {
                setupUi();
                JavaFxUI.getInstance().attachChild(panel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
