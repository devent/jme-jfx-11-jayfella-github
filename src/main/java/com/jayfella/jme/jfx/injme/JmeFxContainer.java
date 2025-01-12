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
package com.jayfella.jme.jfx.injme;

import com.jme3.app.Application;
import com.jayfella.jme.jfx.injme.cursor.CursorDisplayProvider;
import com.jme3.scene.Node;
import javafx.scene.Group;
import javafx.scene.Scene;

/**
 * The interface to work with container of javaFX UI.
 *
 * @author JavaSaBr
 */
public interface JmeFxContainer {

    /**
     * Build the JavaFX container for the application.
     *
     * @param application the application.
     * @param guiNode     the GUI node.
     * @return the javaFX container.
     */
    static JmeFxContainer install(final Application application, final Node guiNode) {
        return JmeFxContainerImpl.install(application, guiNode);
    }

    /**
     * Build the JavaFX container for the application.
     *
     * @param application    the application.
     * @param guiNode        the GUI node.
     * @param cursorProvider the cursor provider.
     * @return the javaFX container.
     */
    static JmeFxContainer install(final Application application, final Node guiNode,
                                  final CursorDisplayProvider cursorProvider) {
        return JmeFxContainerImpl.install(application, guiNode, cursorProvider);
    }

    /**
     * Checks of existing waiting frames.
     *
     * @return true if need to write javaFx frame.
     */
    boolean isNeedWriteToJme();

    /**
     * Write javaFX frame to jME texture.
     */
    Void writeToJme();

    /**
     * Set a new scene to this container.
     *
     * @param newScene the new scene or null.
     * @param rootNode the new root of the scene.
     */
    void setScene(Scene newScene, Group rootNode);

    /**
     * Gets the current cursor provider.
     *
     * @return the current cursor provider.
     */
    CursorDisplayProvider getCursorProvider();

    /**
     * Gets the current scene.
     *
     * @return the current scene.
     */
    Scene getScene();

    /**
     * Gets the root UI node.
     *
     * @return the root UI node.
     */
    Group getRootNode();
}
