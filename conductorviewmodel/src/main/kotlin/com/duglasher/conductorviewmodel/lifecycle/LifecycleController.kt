package com.duglasher.conductorviewmodel.lifecycle

import android.os.Bundle
import androidx.annotation.MainThread
import androidx.annotation.NonNull
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.bluelinelabs.conductor.Controller


abstract class LifecycleController @JvmOverloads constructor(bundle: Bundle? = null) :
    Controller(bundle),
    LifecycleOwner {

    private val lifecycleOwner: ControllerLifecycleOwner<*> = ControllerLifecycleOwner(this)

    // This is initialized in performCreateView and unavailable outside of the
    // onCreateView/onDestroyView lifecycle
    private var _viewLifecycleOwner: ControllerViewLifecycleOwner<*>? = null
    val viewLifecycleOwner: LifecycleOwner
        @MainThread
        get() {
            val owner = _viewLifecycleOwner
            checkNotNull(owner) {
                "Can't access the Controller View's LifecycleOwner when getView() is null i.e., before" +
                        "onCreateView() or after onDestroyView()"
            }
            return owner
        }

    init {
        addLifecycleListener(object : Controller.LifecycleListener() {
            override fun preCreateView(controller: Controller) {
                _viewLifecycleOwner = ControllerViewLifecycleOwner(this@LifecycleController)
            }

            override fun postDestroyView(controller: Controller) {
                _viewLifecycleOwner = null
            }
        })
    }

    @NonNull
    override fun getLifecycle(): Lifecycle = lifecycleOwner.lifecycle

}