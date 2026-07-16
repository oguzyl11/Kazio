package com.kazio.app.data.repository;

import com.kazio.app.data.local.room.PersonalRecordDao;
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
public final class PersonalRecordRepositoryImpl_Factory implements Factory<PersonalRecordRepositoryImpl> {
  private final Provider<PersonalRecordDao> daoProvider;

  public PersonalRecordRepositoryImpl_Factory(Provider<PersonalRecordDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public PersonalRecordRepositoryImpl get() {
    return newInstance(daoProvider.get());
  }

  public static PersonalRecordRepositoryImpl_Factory create(
      Provider<PersonalRecordDao> daoProvider) {
    return new PersonalRecordRepositoryImpl_Factory(daoProvider);
  }

  public static PersonalRecordRepositoryImpl newInstance(PersonalRecordDao dao) {
    return new PersonalRecordRepositoryImpl(dao);
  }
}
