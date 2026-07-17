package com.kazio.app.domain.usecase;

import com.kazio.app.domain.repository.PersonalRecordRepository;
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
public final class GetPersonalRecordsUseCase_Factory implements Factory<GetPersonalRecordsUseCase> {
  private final Provider<PersonalRecordRepository> personalRecordRepositoryProvider;

  public GetPersonalRecordsUseCase_Factory(
      Provider<PersonalRecordRepository> personalRecordRepositoryProvider) {
    this.personalRecordRepositoryProvider = personalRecordRepositoryProvider;
  }

  @Override
  public GetPersonalRecordsUseCase get() {
    return newInstance(personalRecordRepositoryProvider.get());
  }

  public static GetPersonalRecordsUseCase_Factory create(
      Provider<PersonalRecordRepository> personalRecordRepositoryProvider) {
    return new GetPersonalRecordsUseCase_Factory(personalRecordRepositoryProvider);
  }

  public static GetPersonalRecordsUseCase newInstance(
      PersonalRecordRepository personalRecordRepository) {
    return new GetPersonalRecordsUseCase(personalRecordRepository);
  }
}
