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

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;

/**
 * Tests horizontal and vertical scrolling using the mouse.
 */
public class TestScroll extends SimpleApplication {

    public static void main(String... args) {
        TestScroll main = new TestScroll();
        main.start();
    }

    @Override
    public void simpleInitApp() {



        JavaFxUI.initialize(this);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefHeight(600);
        scrollPane.setPrefWidth(400);

        Button button = new Button("My Button");
        button.setPrefSize(400, 1300);

        button.setOnScroll(System.err::println);
        // button.addEventHandler(EventType.ROOT, System.err::println);

        scrollPane.setContent(button);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        JavaFxUI.getInstance().attachChild(scrollPane);
    }

    @Override
    public void simpleUpdate(float tpf) {
        inputManager.setCursorVisible(true);
    }

}
