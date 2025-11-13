package com.youxiang8727.firebaserealtimedatabasechat.data.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import com.youxiang8727.firebaserealtimedatabasechat.data.repository.FirebaseAuthenticationRepositoryImpl
import com.youxiang8727.firebaserealtimedatabasechat.data.repository.FirebaseRealTimeDatabaseRepositoryImpl
import com.youxiang8727.firebaserealtimedatabasechat.domain.repository.FirebaseAuthenticationRepository
import com.youxiang8727.firebaserealtimedatabasechat.domain.repository.FirebaseRealTimeDatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    @Singleton
    fun providesFirebaseRealTimeDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance()

    @Provides
    @Singleton
    fun providesFirebaseReelTimeDatabaseRepository(
        database: FirebaseDatabase
    ): FirebaseRealTimeDatabaseRepository =
        FirebaseRealTimeDatabaseRepositoryImpl(
            database = database
        )

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun provideFirebaseAuthenticationRepository(
        auth: FirebaseAuth
    ): FirebaseAuthenticationRepository =
        FirebaseAuthenticationRepositoryImpl(
            auth = auth
        )
}