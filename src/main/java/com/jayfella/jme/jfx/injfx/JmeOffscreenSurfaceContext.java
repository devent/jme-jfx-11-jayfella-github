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
package com.jayfella.jme.jfx.injfx;

import com.jayfella.jme.jfx.injfx.input.JfxKeyInput;
import com.jayfella.jme.jfx.injfx.input.JfxMouseInput;
import com.jme3.input.JoyInput;
import com.jme3.input.TouchInput;
import com.jme3.opencl.Context;
import com.jme3.renderer.Renderer;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeContext;
import com.jme3.system.JmeSystem;
import com.jme3.system.SystemListener;
import com.jme3.system.Timer;

/**
 * The implementation of the {@link JmeContext} for integrating to JavaFX.
 *
 * @author empirephoenix
 */
public class JmeOffscreenSurfaceContext implements JmeContext {

    /**
     * The settings.
     */
    protected final AppSettings settings;

    /**
     * The key input.
     */
    protected final JfxKeyInput keyInput;

    /**
     * The mouse input.
     */
    protected final JfxMouseInput mouseInput;

    /**
     * The current width.
     */
    private volatile int width;

    /**
     * The current height.
     */
    private volatile int height;

    /**
     * The background context.
     */
    protected JmeContext backgroundContext;

    public JmeOffscreenSurfaceContext() {
        this.keyInput = new JfxKeyInput(this);
        this.mouseInput = new JfxMouseInput(this);
        this.settings = createSettings();
        this.backgroundContext = createBackgroundContext();
        this.height = 1;
        this.width = 1;
    }

    /**
     * Gets the current height.
     *
     * @return the current height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the current height.
     *
     * @param height the current height.
     */
    public void setHeight(final int height) {
        this.height = height;
    }

    /**
     * Gets the current width.
     *
     * @return the current width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the current width.
     *
     * @param width the current width.
     */
    public void setWidth(final int width) {
        this.width = width;
    }

    /**
     * Creates a new app settings.
     *
     * @return the new app settings.
     */
    protected AppSettings createSettings() {
        var settings = new AppSettings(true);
        settings.setRenderer(AppSettings.LWJGL_OPENGL32);
        return settings;
    }

    /**
     * Creates a new background jme context.
     *
     * @return the new background jme context.
     */
    protected JmeContext createBackgroundContext() {
        return JmeSystem.newContext(settings, Type.OffscreenSurface);
    }

    /**
     * Gets the current background context.
     *
     * @return the current background context.
     */
    protected JmeContext getBackgroundContext() {
        return backgroundContext;
    }

    @Override
    public Type getType() {
        return Type.OffscreenSurface;
    }

    @Override
    public void setSettings(AppSettings settings) {
        this.settings.copyFrom(settings);
        this.settings.setRenderer(AppSettings.LWJGL_OPENGL32);

        getBackgroundContext().setSettings(settings);
    }

    @Override
    public void setSystemListener(SystemListener listener) {
        getBackgroundContext().setSystemListener(listener);
    }

    @Override
    public AppSettings getSettings() {
        return settings;
    }

    @Override
    public Renderer getRenderer() {
        return getBackgroundContext().getRenderer();
    }

    @Override
    public Context getOpenCLContext() {
        return null;
    }

    @Override
    public JfxMouseInput getMouseInput() {
        return mouseInput;
    }

    @Override
    public JfxKeyInput getKeyInput() {
        return keyInput;
    }

    @Override
    public JoyInput getJoyInput() {
        return null;
    }

    @Override
    public TouchInput getTouchInput() {
        return null;
    }

    @Override
    public Timer getTimer() {
        return getBackgroundContext().getTimer();
    }

    @Override
    public void setTitle(String title) {
    }

    @Override
    public boolean isCreated() {
        return backgroundContext != null && backgroundContext.isCreated();
    }

    @Override
    public boolean isRenderable() {
        return backgroundContext != null && backgroundContext.isRenderable();
    }

    @Override
    public void setAutoFlushFrames(boolean enabled) {
    }

    @Override
    public void create(boolean waitFor) {
        var render = System.getProperty("jfx.background.render", AppSettings.LWJGL_OPENGL32);
        var backgroundContext = getBackgroundContext();
        backgroundContext.getSettings().setRenderer(render);
        backgroundContext.create(waitFor);
    }

    @Override
    public void restart() {
    }

    @Override
    public void destroy(boolean waitFor) {

        if (backgroundContext == null) {
            throw new IllegalStateException("Not created");
        }

        // destroy wrapped context
        backgroundContext.destroy(waitFor);
    }

    @Override
    public SystemListener getSystemListener() {
        return getBackgroundContext().getSystemListener();
    }

    @Override
    public int getFramebufferHeight() {
        return getBackgroundContext().getFramebufferHeight();
    }

    @Override
    public int getFramebufferWidth() {
        return getBackgroundContext().getFramebufferWidth();
    }

    @Override
    public int getWindowXPosition() {
        return getBackgroundContext().getWindowXPosition();
    }

    @Override
    public int getWindowYPosition() {
        return getBackgroundContext().getWindowYPosition();
    }
}