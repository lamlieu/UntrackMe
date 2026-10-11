package io.github.lamlieu.untrackme.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.github.lamlieu.untrackme.core.data.UserPreferenceRepository
import io.github.lamlieu.untrackme.core.data.testing.FakeUserPreferenceRepository
import javax.inject.Singleton

@Module
@TestInstallIn(
  components = [SingletonComponent::class],
  replaces = [UserPreferenceModule::class]
)
abstract class FakeUserPreferenceModule {

  @Binds
  @Singleton
  abstract fun bindUserPreferenceRepository(
    fakeImpl: FakeUserPreferenceRepository
  ): UserPreferenceRepository
}
