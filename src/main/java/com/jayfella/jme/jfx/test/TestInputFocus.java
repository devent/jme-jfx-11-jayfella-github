/*
 * Copyright © 2019-2022, jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jayfella.jme.jfx.test;

import com.jayfella.jme.jfx.JavaFxUI;
import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.system.AppSettings;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * Tests key up and down events.
 * If an up event occurs in JME, JavaFX should pass the event to JME too.
 */
public class TestInputFocus extends SimpleApplication implements ActionListener {

    public static void main(String... args) {

        TestInputFocus testInputFocus = new TestInputFocus();
        AppSettings appSettings = new AppSettings(true);
        appSettings.setFrameRate(120);

        testInputFocus.setSettings(appSettings);
        testInputFocus.start();

    }

    @Override
    public void simpleInitApp() {

        JavaFxUI.initialize(this);

        VBox vBox = new VBox();

        TextField textField = new TextField("");
        vBox.getChildren().add(textField);

        // This usecase is for when you want to click a button and instantly put the input focus back to JME.
        // For example a boost button in a vehicle game would want this behavior.
        Button button_1 = new Button("This button instantly passed the focus back to JME");
        button_1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // do something
                JavaFxUI.getInstance().loseFocus();
            }
        });
        vBox.getChildren().add(button_1);

        Button button_2 = new Button("This button retains focus.");
        button_2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // do something.
            }
        });
        vBox.getChildren().add(button_2);

        JavaFxUI.getInstance().attachChild(vBox);

        inputManager.addMapping("forward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addListener(this, "forward");

    }

    @Override
    public void simpleUpdate(float tpf) {
        inputManager.setCursorVisible(true);
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        System.out.println("Pressed: " + isPressed);
    }
}
