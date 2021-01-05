package com.duglasher.conductorviewmodel

import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.lifecycle.*
import kotlin.reflect.KClass


@MainThread
inline fun <reified VM : ViewModel> ViewModelController.viewModels(
    noinline ownerProducer: () -> ViewModelStoreOwner = { this },
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
) = createViewModelLazy(VM::class, { ownerProducer().viewModelStore }, factoryProducer)

@MainThread
inline fun <reified VM : ViewModel> ViewModelController.activityViewModels(
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
) = createViewModelLazy(
    VM::class,
    { (activity as ComponentActivity).viewModelStore },
    factoryProducer ?: { (activity as ComponentActivity).defaultViewModelProviderFactory })

@MainThread
fun <VM : ViewModel> ViewModelController.createViewModelLazy(
    viewModelClass: KClass<VM>,
    storeProducer: () -> ViewModelStore,
    factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    val factoryPromise = factoryProducer ?: {
        defaultViewModelProviderFactory
    }
    return ViewModelLazy(viewModelClass, storeProducer, factoryPromise)
}