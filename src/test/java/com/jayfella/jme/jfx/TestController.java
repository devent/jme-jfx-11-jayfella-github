package com.jayfella.jme.jfx;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Controller for {@code /test.fxml}
 *
 * @author Erwin Müller {@literal <erwin@mullerlpublic.de}
 */
public class TestController {

    @FXML
    public Label label1;

    @FXML
    public Label label2;

    @FXML
    public Button button1;

    @FXML
    public void buttonClicked(Event e) {
        System.out.println("Button clicked");
    }
}
