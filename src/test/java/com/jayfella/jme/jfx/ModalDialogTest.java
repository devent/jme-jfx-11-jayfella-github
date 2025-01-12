/*
 * Copyright © 2019-2025, jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *    Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *    Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *    Neither the name of the <organization> nor the
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
package com.jayfella.jme.jfx;

import com.jayfella.jme.jfx.util.JfxPlatform;
import com.jme3.app.SimpleApplication;
import com.jme3.input.controls.ActionListener;
import com.jme3.system.AppSettings;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * Test modal dialog.
 *
 * @author Erwin Müller, {@code <erwin@muellerpublic.de>}
 */
public class ModalDialogTest extends SimpleApplication implements ActionListener {

    private Dialog<ButtonType> dialog;

    public static void main(String... args) {
        var dialogTest = new ModalDialogTest();
        var settings = new AppSettings(true);
        settings.setFrameRate(120);
        dialogTest.setSettings(settings);
        dialogTest.start();
    }

    @Override
    public void simpleInitApp() {
        JavaFxUI.initialize(this);

        JfxPlatform.runInFxThread(() -> {
            ButtonType loginButtonType = new ButtonType("Ok", ButtonData.OK_DONE);
            dialog = new Dialog<>();
            dialog.getDialogPane().getButtonTypes().add(loginButtonType);
            boolean disabled = false;
            dialog.getDialogPane().lookupButton(loginButtonType).setDisable(disabled);
        });

        VBox vBox = new VBox();
        TextField textField = new TextField("");
        vBox.getChildren().add(textField);
        Button button_1 = new Button("Dialog");
        button_1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                dialog.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> {
                });
            }
        });
        vBox.getChildren().add(button_1);
        JavaFxUI.getInstance().attachChild(vBox);
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
