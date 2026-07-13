package com.kazio.app.di;

import com.kazio.app.data.local.room.KazioDatabase;
import com.kazio.app.data.local.room.ShiftDao;
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
public final class DatabaseModule_ProvideShiftDaoFactory implements Factory<ShiftDao> {
  private final Provider<KazioDatabase> databaseProvider;

  public DatabaseModule_ProvideShiftDaoFactory(Provider<KazioDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public ShiftDao get() {
    return provideShiftDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideShiftDaoFactory create(
      Provider<KazioDatabase> databaseProvider) {
    return new DatabaseModule_ProvideShiftDaoFactory(databaseProvider);
  }

  public static ShiftDao provideShiftDao(KazioDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideShiftDao(database));
  }
}
