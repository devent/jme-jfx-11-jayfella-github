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
package com.jayfella.jme.jfx;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jme3.input.RawInputListener;
import com.jme3.input.event.JoyAxisEvent;
import com.jme3.input.event.JoyButtonEvent;
import com.jme3.input.event.KeyInputEvent;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.input.event.MouseMotionEvent;
import com.jme3.input.event.TouchEvent;

/**
 * This {@link RawInputListener} will be executed after the
 * FX Input Listener. So pressed Keys and mouse buttons 
 * can be considered as JME Pressed Input.
 * 
 * @author Klaus Pichler
 *
 */
public class JmeMemoryInputHandler implements RawInputListener {

	private static final Logger log = LoggerFactory.getLogger(JmeMemoryInputHandler.class);
	
	private final Map<Integer, KeyInputEvent> jmeKeyInputEvents = new HashMap<>();
	private final Map<Integer, MouseButtonEvent> jmeMouseButtonEvents = new HashMap<>();
	
	@Override
	public void beginInput() {
		// Nothing to do
	}

	@Override
	public void endInput() {
		// Nothing to do
	}

	@Override
	public void onJoyAxisEvent(JoyAxisEvent evt) {
		// Nothing to do	
	}

	@Override
	public void onJoyButtonEvent(JoyButtonEvent evt) {
		// Nothing to do
	}

	@Override
	public void onMouseMotionEvent(MouseMotionEvent evt) {
		// Nothing to do	
	}

	@Override
	public void onMouseButtonEvent(MouseButtonEvent evt) {
		
		// Storing or removing now JME Mouse button events	
		MouseButtonEvent existingEvent = jmeMouseButtonEvents.get(evt.getButtonIndex());

		if (evt.isPressed()) {
			if (existingEvent == null) {
				jmeMouseButtonEvents.put(evt.getButtonIndex(), evt);
				log.debug("JME - MouseEvent ADDED: " + evt.toString() + " - SIZE: " + jmeMouseButtonEvents.size());
			}
		}

		else if (evt.isReleased()) {
			if (existingEvent != null) {
				if (jmeMouseButtonEvents.remove(evt.getButtonIndex()) != null) {
					log.debug("JME - MouseEvent REMOVED: " + evt.toString() + " - SIZE: "
							+ jmeMouseButtonEvents.size());
				}
			}
		}		
	}

	@Override
	public void onKeyEvent(KeyInputEvent evt) {
		
		// Storing or removing now JME key pressed events.	
		if (evt.getKeyCode() == 0) {
			return;
		}

		KeyInputEvent existingEvent = jmeKeyInputEvents.get(evt.getKeyCode());

		if (evt.isPressed()) {
			if (existingEvent == null) {
				jmeKeyInputEvents.put(evt.getKeyCode(), evt);
				log.debug("JME - KeyEvent ADDED: " + evt.toString() + " - SIZE: " + jmeKeyInputEvents.size());
			}
		}

		else if (evt.isReleased() && !evt.isRepeating()) {
			if (existingEvent != null) {
				if (jmeKeyInputEvents.remove(evt.getKeyCode()) != null) {
					log.debug("JME - KeyEvent REMOVED: " + evt.toString() + " - SIZE: " + jmeKeyInputEvents.size());
				}
			}
		}
		
	}

	@Override
	public void onTouchEvent(TouchEvent evt) {
		// Nothing to do
		
	}

	public Map<Integer, KeyInputEvent> getJmeKeyInputEvents() {
		return jmeKeyInputEvents;
	}

	public Map<Integer, MouseButtonEvent> getJmeMouseButtonEvents() {
		return jmeMouseButtonEvents;
	}

}
