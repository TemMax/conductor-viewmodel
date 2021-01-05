package com.duglasher.conductorviewmodel.lifecycle

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.bluelinelabs.conductor.Controller


internal class ControllerViewLifecycleOwner<T>(lifecycleController: T) : LifecycleOwner
        where T : Controller,
              T : LifecycleOwner {

    private var lifecycleRegistry: LifecycleRegistry? = null

    init {
        lifecycleController.addLifecycleListener(object : Controller.LifecycleListener() {
            override fun postCreateView(controller: Controller, view: View) {
                initialize()
                handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
            }

            override fun preAttach(controller: Controller, view: View) {
                handleLifecycleEvent(Lifecycle.Event.ON_START)
            }

            override fun postAttach(controller: Controller, view: View) {
                handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
            }

            override fun preDetach(controller: Controller, view: View) {
                handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            }

            override fun postDetach(controller: Controller, view: View) {
                handleLifecycleEvent(Lifecycle.Event.ON_STOP)
            }

            override fun preDestroyView(controller: Controller, view: View) {
                handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            }
        })
    }

    private fun initialize() {
        if (lifecycleRegistry == null) {
            lifecycleRegistry = LifecycleRegistry(this)
        }
    }

    //    private fun isInitialized(): Boolean = lifecycleRegistry != null

    private fun handleLifecycleEvent(event: Lifecycle.Event) {
        lifecycleRegistry!!.handleLifecycleEvent(event)
    }

    override fun getLifecycle(): Lifecycle {
        initialize()
        return lifecycleRegistry!!
    }

}