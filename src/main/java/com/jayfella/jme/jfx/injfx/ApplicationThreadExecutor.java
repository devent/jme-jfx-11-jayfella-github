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
package com.jayfella.jme.jfx.injfx;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The executor for executing tasks in application thread.
 *
 * @author JavaSaBr
 */
public class ApplicationThreadExecutor {

    private static final ApplicationThreadExecutor INSTANCE = new ApplicationThreadExecutor();

    public static ApplicationThreadExecutor getInstance() {
        return INSTANCE;
    }

    /**
     * The list of waiting tasks.
     */
    // private final ConcurrentArray<Runnable> waitTasks;
    private final CopyOnWriteArrayList<Runnable> waitTasks;

    /**
     * The list of tasks to execute.
     */
    // private final Array<Runnable> execute;
    private final List<Runnable> execute;

    private ApplicationThreadExecutor() {
        // this.waitTasks = ArrayFactory.newConcurrentAtomicARSWLockArray(Runnable.class);
        // this.execute = ArrayFactory.newArray(Runnable.class);
        this.waitTasks = new CopyOnWriteArrayList<>();
        this.execute = new ArrayList<>();
    }

    /**
     * Add the task to execute.
     *
     * @param task the new task.
     */
    public void addToExecute(Runnable task) {
        // ArrayUtils.runInWriteLock(waitTasks, task, Array::add);
        waitTasks.add(task);
    }

    /**
     * Execute the waiting tasks.
     */
    public void execute() {

        if (waitTasks.isEmpty()) {
            return;
        }

        // ArrayUtils.runInWriteLock(waitTasks, execute, ArrayUtils::move);

        //long stamp

        try {
            execute.forEach(Runnable::run);
        } finally {
            execute.clear();
        }

    }
}
