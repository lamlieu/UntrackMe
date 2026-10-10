package io.github.lamlieu.untrackme.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.lamlieu.untrackme.core.data.UserPreferenceRepository
import io.github.lamlieu.untrackme.core.data.UserPreferenceRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserPreferenceModule {

  @Binds
  @Singleton
  abstract fun bindUserPreferenceRepository(
    impl: UserPreferenceRepositoryImpl
  ): UserPreferenceRepository
}
