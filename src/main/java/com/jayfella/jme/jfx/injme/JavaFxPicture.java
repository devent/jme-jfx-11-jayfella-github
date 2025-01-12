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

import static com.jayfella.jme.jfx.injme.util.JmeWindowUtils.getX;
import static com.jayfella.jme.jfx.injme.util.JmeWindowUtils.getY;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jayfella.jme.jfx.injme.util.JmeWindowUtils;
import com.jayfella.jme.jfx.util.JfxPlatform;
import com.jme3.system.JmeContext;
import com.jme3.ui.Picture;
import com.sun.javafx.embed.EmbeddedStageInterface;

/**
 * The implementation of the {@link Picture} to represent javaFX UI Scene.
 *
 * @author JavaSaBr
 */
public class JavaFxPicture extends Picture {

    private static final Logger LOGGER = LoggerFactory.getLogger(JfxPlatform.class);

    /**
     * The JavaFX container.
     */
    private final JmeFxContainerInternal container;

    public JavaFxPicture(final JmeFxContainerInternal container) {
        super("JavaFxContainer", true);
        this.container = container;
    }

    /**
     * Gets the JavaFX container.
     *
     * @return the JavaFX container.
     */
    private JmeFxContainerInternal getContainer() {
        return container;
    }

    @Override
    public void updateLogicalState(final float tpf) {

        final JmeFxContainerInternal container = getContainer();
        final JmeContext jmeContext = container.getJmeContext();
        try {

            final EmbeddedStageInterface stageInterface = container.getStageInterface();
            if (stageInterface == null) {
                return;
            }

            final int windowWidth = JmeWindowUtils.getWidth(jmeContext);
            final int windowHeight = JmeWindowUtils.getHeight(jmeContext);

            if (windowWidth != container.getSceneWidth() || windowHeight != container.getSceneHeight()) {
                container.fitSceneToWindowSize();
            }

            final int currentX = getX(jmeContext);
            final int currentY = getY(jmeContext);

            if (container.getPositionX() != currentX || container.getPositionY() != currentY) {

                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("moved window to [original: " + currentX + ", " + currentY + "]");
                }

                container.move(currentX, currentY);
            }

        } finally {
            super.updateLogicalState(tpf);
        }
    }
}
