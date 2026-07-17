package com.kazio.app.di;

import com.kazio.app.data.local.room.KazioDatabase;
import com.kazio.app.data.local.room.PlatformDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvidePlatformDaoFactory implements Factory<PlatformDao> {
  private final Provider<KazioDatabase> databaseProvider;

  public DatabaseModule_ProvidePlatformDaoFactory(Provider<KazioDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public PlatformDao get() {
    return providePlatformDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvidePlatformDaoFactory create(
      Provider<KazioDatabase> databaseProvider) {
    return new DatabaseModule_ProvidePlatformDaoFactory(databaseProvider);
  }

  public static PlatformDao providePlatformDao(KazioDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.providePlatformDao(database));
  }
}
