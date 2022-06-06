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
package com.jayfella.jme.jfx.injme;

import com.jme3.app.Application;
import com.jayfella.jme.jfx.injme.input.JmeFXInputListener;
import com.jme3.system.JmeContext;
import com.sun.javafx.cursor.CursorFrame;
import com.sun.javafx.embed.EmbeddedSceneInterface;
import com.sun.javafx.embed.EmbeddedStageInterface;
import com.sun.javafx.stage.EmbeddedWindow;

/**
 * The internal interface to work with container of javaFX UI.
 *
 * @author JavaSaBr
 */
public interface JmeFxContainerInternal extends JmeFxContainer {

    /**
     * Requests the preferred size for UI.
     *
     * @param width  the preferred width.
     * @param height the preferred height.
     */
    void requestPreferredSize(int width, int height);

    /**
     * Requests focus.
     *
     * @return true if it was successful.
     */
    boolean requestFocus();

    /**
     * Gets the jME application.
     *
     * @return the jME application.
     */
    Application getApplication();

    /**
     * Gets the jME context.
     *
     * @return the jME context.
     */
    JmeContext getJmeContext();

    /**
     * Gets the X position.
     *
     * @return the X position.
     */
    int getPositionX();

    /**
     * Sets the X position.
     *
     * @param positionX the X position.
     */
    void setPositionX(int positionX);

    /**
     * Gets the Y position.
     *
     * @return the Y position.
     */
    int getPositionY();

    /**
     * Sets the Y position.
     *
     * @param positionY the Y position.
     */
    void setPositionY(int positionY);

    /**
     * Gets the scene height.
     *
     * @return the scene height.
     */
    int getSceneHeight();

    /**
     * Sets the scene height.
     *
     * @param sceneHeight the scene height.
     */
    void setSceneHeight(int sceneHeight);

    /**
     * Gets the scene width.
     *
     * @return the scene width.
     */
    int getSceneWidth();

    /**
     * Sets the scene width.
     *
     * @param sceneWidth the scene width.
     */
    void setSceneWidth(int sceneWidth);

    /**
     * Gets the target pixel factor.
     *
     * @return the target pixel factor.
     */
    float getPixelScaleFactor();

    /**
     * Gets the current scene interface.
     *
     * @return the current scene interface.
     */
    EmbeddedSceneInterface getSceneInterface();

    /**
     * Sets the current scene interface.
     *
     * @param sceneInterface the current scene interface.
     */
    void setSceneInterface(EmbeddedSceneInterface sceneInterface);

    /**
     * Gets the embedded window.
     *
     * @return the embedded window.
     */
    EmbeddedWindow getEmbeddedWindow();

    /**
     * Sets the embedded window.
     *
     * @param embeddedWindow the embedded window.
     */
    void setEmbeddedWindow(EmbeddedWindow embeddedWindow);

    /**
     * Gets the current stage interface.
     *
     * @return the current stage interface.
     */
    EmbeddedStageInterface getStageInterface();

    /**
     * Sets the current stage interface.
     *
     * @param stageInterface the current stage interface.
     */
    void setStageInterface(EmbeddedStageInterface stageInterface);

    /**
     * Fit scene to window size.
     */
    void fitSceneToWindowSize();

    /**
     * Moves the container to the new position.
     *
     * @param positionX the new X position.
     * @param positionY the new Y position.
     */
    void move(int positionX, int positionY);

    /**
     * Checks the coordinates.
     *
     * @param x the X coordinate.
     * @param y the Y coordinate.
     * @return true if the coordinates is covered.
     */
    boolean isCovered(int x, int y);

    /**
     * Draw new frame of JavaFX to byte buffer.
     */
    void requestRedraw();

    /**
     * Request showing the cursor frame.
     *
     * @param cursorFrame the cursor frame.
     */
    void requestShowingCursor(CursorFrame cursorFrame);

    /**
     * Requests the status of enabled scene.
     *
     * @param enabled the flag of enabling javaFX.
     */
    void requestEnabled(boolean enabled);

    /**
     * @return true if the windows has focused.
     */
    boolean isFocused();

    /**
     * Get focused.
     */
    void grabFocus();

    /**
     * Lose focused.
     */
    void loseFocus();

    /**
     * Gets the user input listener.
     *
     * @return the user input listener.
     */
    JmeFXInputListener getInputListener();
}
