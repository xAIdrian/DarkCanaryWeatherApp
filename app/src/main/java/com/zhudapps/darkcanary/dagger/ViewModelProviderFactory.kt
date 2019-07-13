package com.zhudapps.darkcanary.dagger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zhudapps.darkcanary.main.MainViewModel
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by adrian mohnacs on 2019-07-01
 */
@Singleton
class ViewModelProviderFactory @Inject constructor() : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return when {
            //clazz.isAssignableFrom(Foo.class) will be true whenever the class represented by the clazz object is a superclass or superinterface of Foo
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel() as T
            else -> throw IllegalArgumentException("ViewModelProviderFactory Unknown ViewModel class: " + modelClass.name)
        }
    }
}