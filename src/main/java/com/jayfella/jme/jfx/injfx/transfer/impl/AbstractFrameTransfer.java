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
package com.jayfella.jme.jfx.injfx.transfer.impl;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import com.jayfella.jme.jfx.injfx.processor.FrameTransferSceneProcessor.TransferMode;
import com.jayfella.jme.jfx.injfx.transfer.FrameTransfer;
import com.jayfella.jme.jfx.util.JfxPlatform;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.Renderer;
import com.jme3.texture.FrameBuffer;
import com.jme3.texture.FrameBuffer.FrameBufferTarget;
import com.jme3.texture.Image;
import com.jme3.util.BufferUtils;

import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

/**
 * The base implementation of a frame transfer.
 *
 * @param <T> the destination's type.
 * @author JavaSaBr
 */
public abstract class AbstractFrameTransfer<T> implements FrameTransfer {

    protected static final int RUNNING_STATE = 1;
    protected static final int WAITING_STATE = 2;
    protected static final int DISPOSING_STATE = 3;
    protected static final int DISPOSED_STATE = 4;

    /**
     * The Frame state.
     */
    protected final AtomicInteger frameState;

    /**
     * The Image state.
     */
    protected final AtomicInteger imageState;

    /**
     * The Frame buffer.
     */
    protected final FrameBuffer frameBuffer;

    /**
     * The Pixel writer.
     */
    protected final PixelWriter pixelWriter;

    /**
     * The Frame byte buffer.
     */
    protected final ByteBuffer frameByteBuffer;

    /**
     * The transfer mode.
     */
    protected final TransferMode transferMode;

    /**
     * The byte buffer.
     */
    protected final byte[] byteBuffer;

    /**
     * The image byte buffer.
     */
    protected final byte[] imageByteBuffer;

    /**
     * The prev image byte buffer.
     */
    protected final byte[] prevImageByteBuffer;

    /**
     * How many frames need to write else.
     */
    protected int frameCount;

    /**
     * The width.
     */
    private final int width;

    /**
     * The height.
     */
    private final int height;

    public AbstractFrameTransfer(T destination, int width, int height, TransferMode transferMode) {
        this(destination, transferMode, null, width, height);
    }

    public AbstractFrameTransfer(
            T destination,
            TransferMode transferMode,
            FrameBuffer frameBuffer,
            int width,
            int height
    ) {
        this.transferMode = transferMode;
        this.frameState = new AtomicInteger(WAITING_STATE);
        this.imageState = new AtomicInteger(WAITING_STATE);
        this.width = frameBuffer != null ? frameBuffer.getWidth() : width;
        this.height = frameBuffer != null ? frameBuffer.getHeight() : height;
        this.frameCount = 0;

        if (frameBuffer != null) {
            this.frameBuffer = frameBuffer;
        } else {
            this.frameBuffer = new FrameBuffer(width, height, 1);
            this.frameBuffer.setDepthTarget(FrameBufferTarget.newTarget(Image.Format.Depth));
            this.frameBuffer.addColorTarget(FrameBufferTarget.newTarget(Image.Format.RGBA8));
            this.frameBuffer.setSrgb(true);
        }

        frameByteBuffer = BufferUtils.createByteBuffer(getWidth() * getHeight() * 4);
        byteBuffer = new byte[getWidth() * getHeight() * 4];
        prevImageByteBuffer = new byte[getWidth() * getHeight() * 4];
        imageByteBuffer = new byte[getWidth() * getHeight() * 4];
        pixelWriter = getPixelWriter(destination, this.frameBuffer, width, height);
    }

    @Override
    public void initFor(Renderer renderer, boolean main) {
        if (main) {
            renderer.setMainFrameBufferOverride(frameBuffer);
        }
    }

    /**
     * Get the pixel writer.
     *
     * @param destination the destination.
     * @param frameBuffer the frame buffer.
     * @param width       the width.
     * @param height      the height.
     * @return the pixel writer.
     */
    protected PixelWriter getPixelWriter(T destination, FrameBuffer frameBuffer, int width, int height) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void copyFrameBufferToImage(RenderManager renderManager) {

        while (!frameState.compareAndSet(WAITING_STATE, RUNNING_STATE)) {
            if (frameState.get() == DISPOSED_STATE) {
                return;
            }
        }

        // Convert screenshot.
        try {

            frameByteBuffer.clear();

            var renderer = renderManager.getRenderer();
            renderer.readFrameBufferWithFormat(frameBuffer, frameByteBuffer, Image.Format.RGBA8);

        } finally {
            if (!frameState.compareAndSet(RUNNING_STATE, WAITING_STATE)) {
                throw new RuntimeException("unknown problem with the frame state");
            }
        }

        synchronized (byteBuffer) {
            frameByteBuffer.get(byteBuffer);

            if (transferMode == TransferMode.ON_CHANGES) {

                final byte[] prevBuffer = getPrevImageByteBuffer();

                if (Arrays.equals(prevBuffer, byteBuffer)) {
                    if (frameCount == 0) return;
                } else {
                    frameCount = 2;
                    System.arraycopy(byteBuffer, 0, prevBuffer, 0, byteBuffer.length);
                }

                frameByteBuffer.position(0);
                frameCount--;
            }
        }

        JfxPlatform.runInFxThread(this::writeFrame);
    }

    /**
     * Write content to image.
     */
    protected void writeFrame() {

        while (!imageState.compareAndSet(WAITING_STATE, RUNNING_STATE)) {
            if (imageState.get() == DISPOSED_STATE) {
                return;
            }
        }

        try {

            var imageByteBuffer = getImageByteBuffer();

            synchronized (byteBuffer) {
                System.arraycopy(byteBuffer, 0, imageByteBuffer, 0, byteBuffer.length);
            }

            for (int i = 0, length = width * height * 4; i < length; i += 4) {
                byte r = imageByteBuffer[i];
                byte g = imageByteBuffer[i + 1];
                byte b = imageByteBuffer[i + 2];
                byte a = imageByteBuffer[i + 3];
                imageByteBuffer[i] = b;
                imageByteBuffer[i + 1] = g;
                imageByteBuffer[i + 2] = r;
                imageByteBuffer[i + 3] = a;
            }

            var pixelFormat = PixelFormat.getByteBgraInstance();

            pixelWriter.setPixels(0, 0, width, height, pixelFormat, imageByteBuffer, 0, width * 4);

        } finally {
            if (!imageState.compareAndSet(RUNNING_STATE, WAITING_STATE)) {
                throw new RuntimeException("unknown problem with the image state");
            }
        }
    }

    /**
     * Get the image byte buffer.
     *
     * @return the image byte buffer.
     */
    protected byte[] getImageByteBuffer() {
        return imageByteBuffer;
    }

    /**
     * Get the prev image byte buffer.
     *
     * @return the prev image byte buffer.
     */
    protected byte[] getPrevImageByteBuffer() {
        return prevImageByteBuffer;
    }

    @Override
    public void dispose() {
        while (!frameState.compareAndSet(WAITING_STATE, DISPOSING_STATE)) ;
        while (!imageState.compareAndSet(WAITING_STATE, DISPOSING_STATE)) ;
        disposeImpl();
        frameState.compareAndSet(DISPOSING_STATE, DISPOSED_STATE);
        imageState.compareAndSet(DISPOSING_STATE, DISPOSED_STATE);
    }

    /**
     * Dispose.
     */
    protected void disposeImpl() {
        frameBuffer.dispose();
        BufferUtils.destroyDirectBuffer(frameByteBuffer);
    }
}
