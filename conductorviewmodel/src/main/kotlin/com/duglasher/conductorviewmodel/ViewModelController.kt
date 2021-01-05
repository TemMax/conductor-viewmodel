package com.duglasher.conductorviewmodel

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import com.duglasher.conductorviewmodel.lifecycle.LifecycleController


abstract class ViewModelController @JvmOverloads constructor(args: Bundle? = null) :
    LifecycleController(args),
    ViewModelStoreOwner,
    SavedStateRegistryOwner {

    private val savedStateRegistryController = SavedStateRegistryController.create(this)

    private val vmStore = ViewModelStore()

    val defaultViewModelProviderFactory: ViewModelProvider.Factory by lazy {
        //        ViewModelProvider.AndroidViewModelFactory(activity!!.application)
        SavedStateViewModelFactory(
            activity!!.application,
            this,
            args
        )
    }

    fun viewModelProvider(): ViewModelProvider =
        viewModelProvider(defaultViewModelProviderFactory)

    fun viewModelProvider(factory: ViewModelProvider.Factory): ViewModelProvider =
        ViewModelProvider(viewModelStore, factory)

    override fun getViewModelStore() = vmStore

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        savedStateRegistryController.performSave(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedStateRegistryController.performRestore(savedInstanceState)
    }

    override fun getSavedStateRegistry(): SavedStateRegistry {
        return savedStateRegistryController.savedStateRegistry
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        viewModelStore.clear()
    }

}