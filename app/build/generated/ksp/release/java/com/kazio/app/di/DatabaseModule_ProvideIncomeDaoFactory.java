package com.kazio.app.di;

import com.kazio.app.data.local.room.IncomeDao;
import com.kazio.app.data.local.room.KazioDatabase;
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
public final class DatabaseModule_ProvideIncomeDaoFactory implements Factory<IncomeDao> {
  private final Provider<KazioDatabase> databaseProvider;

  public DatabaseModule_ProvideIncomeDaoFactory(Provider<KazioDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public IncomeDao get() {
    return provideIncomeDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideIncomeDaoFactory create(
      Provider<KazioDatabase> databaseProvider) {
    return new DatabaseModule_ProvideIncomeDaoFactory(databaseProvider);
  }

  public static IncomeDao provideIncomeDao(KazioDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideIncomeDao(database));
  }
}
