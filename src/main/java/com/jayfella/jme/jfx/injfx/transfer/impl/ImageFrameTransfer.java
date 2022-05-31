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
package com.jayfella.jme.jfx.injfx.transfer.impl;

import com.jayfella.jme.jfx.injfx.processor.FrameTransferSceneProcessor.TransferMode;
import com.jayfella.jme.jfx.util.JfxPlatform;
import com.jme3.texture.FrameBuffer;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * The class for transferring a frame from jME to {@link ImageView}.
 *
 * @author JavaSaBr
 */
public class ImageFrameTransfer extends AbstractFrameTransfer<ImageView> {

    private WritableImage writableImage;

    public ImageFrameTransfer(ImageView imageView, TransferMode transferMode, int width, int height) {
        this(imageView, transferMode, null, width, height);
    }

    public ImageFrameTransfer(
            ImageView imageView,
            TransferMode transferMode,
            FrameBuffer frameBuffer,
            int width,
            int height
    ) {
        super(imageView, transferMode, frameBuffer, width, height);
        JfxPlatform.runInFxThread(() -> imageView.setImage(writableImage));
    }

    @Override
    protected PixelWriter getPixelWriter(
            ImageView destination,
            FrameBuffer frameBuffer,
            int width,
            int height
    ) {
        writableImage = new WritableImage(width, height);
        return writableImage.getPixelWriter();
    }
}
