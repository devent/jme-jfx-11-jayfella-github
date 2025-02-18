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
package com.jayfella.jme.jfx.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * The implementation of the {@link Lock} based on using {@link AtomicInteger} without supporting
 * reentrant calls.
 *
 * @author JavaSaBr
 */
public class AtomicLock implements Lock {

    private static final int STATUS_LOCKED = 1;
    private static final int STATUS_UNLOCKED = 0;

    /**
     * The status of lock.
     */
    protected final AtomicInteger status;

    /**
     * The field for consuming CPU.
     */
    protected int sink;

    /**
     * Instantiates a new Atomic lock.
     */
    public AtomicLock() {
        this.status = new AtomicInteger();
        this.sink = 1;
    }

    @Override
    public void lock() {
        while (!tryLock()) consumeCPU();
    }

    /**
     * Consume cpu.
     */
    protected void consumeCPU() {

        final int value = sink;
        int newValue = value * value;
        newValue += value >>> 1;
        newValue += value & newValue;
        newValue += value ^ newValue;
        newValue += newValue << value;
        newValue += newValue | value;
        newValue += value & newValue;
        newValue += value ^ newValue;
        newValue += newValue << value;
        newValue += newValue | value;

        sink = newValue;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean tryLock() {
        return status.compareAndSet(STATUS_UNLOCKED, STATUS_LOCKED);
    }

    @Override
    public boolean tryLock(final long time, final TimeUnit unit) throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void unlock() {
        status.set(STATUS_UNLOCKED);
    }

    @Override
    public String toString() {
        return "AtomicLock{" +
                "status=" + status +
                '}';
    }
}
