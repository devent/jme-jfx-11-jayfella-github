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
package com.jayfella.jme.jfx.injfx.transfer;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.Renderer;

/**
 * The class for transferring content from a jME frame buffer to somewhere.
 *
 * @author JavaSaBr
 */
public interface FrameTransfer {

    /**
     * Init this transfer for the render.
     *
     * @param renderer the render.
     * @param main     true if this transfer is main.
     */
    default void initFor(Renderer renderer, boolean main) {
    }

    /**
     * Gets the width.
     *
     * @return the width.
     */
    int getWidth();

    /**
     * Gets the height.
     *
     * @return the height.
     */
    int getHeight();

    /**
     * Copy the content from render to the frameByteBuffer and write this content to javaFX.
     *
     * @param renderManager the render manager.
     */
    void copyFrameBufferToImage(RenderManager renderManager);

    /**
     * Dispose this transfer.
     */
    void dispose();
}
