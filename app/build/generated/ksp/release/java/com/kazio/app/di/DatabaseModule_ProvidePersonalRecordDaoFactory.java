package com.kazio.app.di;

import com.kazio.app.data.local.room.KazioDatabase;
import com.kazio.app.data.local.room.PersonalRecordDao;
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
public final class DatabaseModule_ProvidePersonalRecordDaoFactory implements Factory<PersonalRecordDao> {
  private final Provider<KazioDatabase> databaseProvider;

  public DatabaseModule_ProvidePersonalRecordDaoFactory(Provider<KazioDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public PersonalRecordDao get() {
    return providePersonalRecordDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvidePersonalRecordDaoFactory create(
      Provider<KazioDatabase> databaseProvider) {
    return new DatabaseModule_ProvidePersonalRecordDaoFactory(databaseProvider);
  }

  public static PersonalRecordDao providePersonalRecordDao(KazioDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.providePersonalRecordDao(database));
  }
}
