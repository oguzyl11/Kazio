package com.kazio.app.data.repository;

import com.kazio.app.data.local.room.PlatformDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class PlatformRepositoryImpl_Factory implements Factory<PlatformRepositoryImpl> {
  private final Provider<PlatformDao> platformDaoProvider;

  public PlatformRepositoryImpl_Factory(Provider<PlatformDao> platformDaoProvider) {
    this.platformDaoProvider = platformDaoProvider;
  }

  @Override
  public PlatformRepositoryImpl get() {
    return newInstance(platformDaoProvider.get());
  }

  public static PlatformRepositoryImpl_Factory create(Provider<PlatformDao> platformDaoProvider) {
    return new PlatformRepositoryImpl_Factory(platformDaoProvider);
  }

  public static PlatformRepositoryImpl newInstance(PlatformDao platformDao) {
    return new PlatformRepositoryImpl(platformDao);
  }
}
