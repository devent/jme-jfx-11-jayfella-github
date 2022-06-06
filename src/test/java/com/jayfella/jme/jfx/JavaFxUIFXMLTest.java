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
