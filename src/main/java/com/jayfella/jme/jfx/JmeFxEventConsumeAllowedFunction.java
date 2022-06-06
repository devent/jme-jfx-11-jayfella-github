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
package com.jayfella.jme.jfx;

import java.util.Map;
import java.util.function.Function;

import com.jme3.input.event.InputEvent;
import com.jme3.input.event.KeyInputEvent;
import com.jme3.input.event.MouseButtonEvent;

/**
 * The Functions determines, if a certain Input event is allowed to be 
 * set as 'consumed' by the JmeFXInput Listener.
 * @author Klaus Pichler
 *
 */
public class JmeFxEventConsumeAllowedFunction implements Function<InputEvent, Boolean> {

	private final Map<Integer, KeyInputEvent> jmeKeyInputEvents;
	private final Map<Integer, MouseButtonEvent> jmeMouseButtonEvents;
	
	public JmeFxEventConsumeAllowedFunction(JmeMemoryInputHandler inputHandler) {
		jmeKeyInputEvents = inputHandler.getJmeKeyInputEvents();
		jmeMouseButtonEvents = inputHandler.getJmeMouseButtonEvents();
	}
	
	@Override
	public Boolean apply(InputEvent event) {
		
		if(event instanceof MouseButtonEvent) {
			return checkMouseEvent((MouseButtonEvent) event);	
		}
		
		if(event instanceof KeyInputEvent) {
			return checkKeyEvent((KeyInputEvent) event);
		}
		
		
		return true;
	}

	private Boolean checkMouseEvent(MouseButtonEvent event) {
		
		if(event.isReleased()) {
			if(jmeMouseButtonEvents.get(event.getButtonIndex())!=null) {
				//Mouse Button was pressed by JME. FX is not allowed to consume the event.
				return false;
			}
		}
		
		
		return true;
	}
	
	private Boolean checkKeyEvent(KeyInputEvent event) {
		
		if(event.isReleased()) {
			if(jmeKeyInputEvents.get(event.getKeyCode())!=null) {
				//Key was pressed by JME. FX is not allowed to consume the event.
				return false;
			}
		}
		
		
		return true;
	}

}
