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
package com.jayfella.jme.jfx.injfx.input;

import com.jme3.input.Input;
import com.jme3.input.RawInputListener;
import com.jayfella.jme.jfx.injfx.ApplicationThreadExecutor;
import com.jayfella.jme.jfx.injfx.JmeOffscreenSurfaceContext;
import javafx.scene.Node;
import javafx.scene.Scene;

/**
 * The base implementation of the {@link Input} for using in the ImageView.
 *
 * @author JavaSaBr
 */
public class JfxInput implements Input {

    protected static final ApplicationThreadExecutor EXECUTOR = ApplicationThreadExecutor.getInstance();

    /**
     * The context.
     */
    protected final JmeOffscreenSurfaceContext context;

    /**
     * The raw listener.
     */
    protected RawInputListener listener;

    /**
     * The input node.
     */
    protected Node node;

    /**
     * The scene.
     */
    protected Scene scene;

    /**
     * The flag of initializing this.
     */
    protected boolean initialized;

    public JfxInput(JmeOffscreenSurfaceContext context) {
        this.context = context;
    }

    /**
     * Checks of existing the node.
     *
     * @return true if the node is exist.
     */
    protected boolean hasNode() {
        return node != null;
    }

    /**
     * Gets the bound node.
     *
     * @return the bound node.
     */
    protected Node getNode() {
        return node;
    }

    /**
     * Gets the raw listener.
     *
     * @return the raw listener.
     */
    protected RawInputListener getListener() {
        return listener;
    }

    /**
     * Bind this input to the node.
     *
     * @param node the node.
     */
    public void bind(Node node) {
        this.node = node;
        this.scene = node.getScene();
    }

    /**
     * Unbind.
     */
    public void unbind() {
        this.node = null;
        this.scene = null;
    }

    @Override
    public void initialize() {
        if (isInitialized()) return;
        initializeImpl();
        initialized = true;
    }

    /**
     * Initialize.
     */
    protected void initializeImpl() {
    }

    @Override
    public void update() {
        if (!context.isRenderable()) return;
        updateImpl();
    }

    /**
     * Update.
     */
    protected void updateImpl() {
    }

    @Override
    public void destroy() {
        unbind();
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public void setInputListener(RawInputListener listener) {
        this.listener = listener;
    }

    @Override
    public long getInputTimeNanos() {
        return System.nanoTime();
    }
}
