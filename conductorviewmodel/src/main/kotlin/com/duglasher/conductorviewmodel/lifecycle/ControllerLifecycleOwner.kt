package com.duglasher.conductorviewmodel.lifecycle

import android.content.Context
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Controller.LifecycleListener


internal class ControllerLifecycleOwner<T>(lifecycleController: T) :
    LifecycleOwner
        where T : Controller,
              T : LifecycleOwner {

    private val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(lifecycleController) // --> State.INITIALIZED

    init {
        lifecycleController.addLifecycleListener(object : LifecycleListener() {
            override fun postContextAvailable(controller: Controller, context: Context) {
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE) // --> State.CREATED;
            }

            override fun postCreateView(controller: Controller, view: View) {
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START) // --> State.STARTED;
            }

            override fun postAttach(controller: Controller, view: View) {
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME) // --> State.RESUMED;
            }

            override fun preDetach(controller: Controller, view: View) {
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE) // --> State.PAUSED;
            }

            override fun preDestroyView(controller: Controller, view: View) {
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP) // --> State.STOPPED;
            }

            override fun preContextUnavailable(controller: Controller, context: Context) {
                // do nothing
            }

            override fun preDestroy(controller: Controller) {
                lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY) // --> State.DESTROYED;
            }
        })
    }

    override fun getLifecycle(): Lifecycle = lifecycleRegistry

}