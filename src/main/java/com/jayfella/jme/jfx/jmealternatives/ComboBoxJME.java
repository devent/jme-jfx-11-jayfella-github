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
package com.jayfella.jme.jfx.jmealternatives;

import com.jayfella.jme.jfx.JavaFxUI;
import javafx.geometry.Bounds;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

/**
 * This is a JME alternative for the ComboBox.
 *
 * It behaves broadly the same way as the core ComboBox, but is not feature complete.
 *
 * In particular:
 * It doesn't fully support keyboard selection (a mouse click is needed to close it)
 * It doesn't support sizing based on number of entries, only by pixels
 * It can go "offscreen" when it tries to open near the bottom of the screen
 *
 * @param <T>
 */
public class ComboBoxJME<T> extends ComboBox<T> {

    Runnable removeListPopup;

    int maxHeight = 200;

    @Override
    public void show() {
        Bounds boundsInScene = localToScene(getBoundsInLocal());

        ListView<T> items = new ListView<>();
        items.setItems(this.getItems());
        items.setMinWidth(boundsInScene.getWidth());
        items.setMaxHeight(maxHeight);

        removeListPopup = JavaFxUI.getInstance().attachPopup(items, boundsInScene.getMinX(), boundsInScene.getMaxY());

        items.setOnMousePressed(event -> {
            getSelectionModel().select(items.getSelectionModel().getSelectedItem());
            removeListPopup.run();
        });
    }

    @Override
    public void hide() {
        //do nothing, we're handling our own open/close (although keyboard based selection might need this?)
    }

    /**
     * Sets the height of the combobox when it opens (in pixels)
     * @param height
     */
    public void setListHeight(int height){
        this.maxHeight = height;
    }

}
