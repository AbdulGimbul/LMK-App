package com.abdl.mylmk_app.di

import android.app.Application
import com.abdl.mylmk_app.data.repository.UserRepository
import com.abdl.mylmk_app.data.source.local.room.LmkDatabase
import com.abdl.mylmk_app.data.source.remote.services.ApiService
import com.abdl.mylmk_app.data.source.remote.services.NetworkConnectionInterceptor
import com.abdl.mylmk_app.ui.auth.AuthViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MVVMApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MVVMApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { ApiService(instance()) }
        bind() from singleton { LmkDatabase(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
    }
}