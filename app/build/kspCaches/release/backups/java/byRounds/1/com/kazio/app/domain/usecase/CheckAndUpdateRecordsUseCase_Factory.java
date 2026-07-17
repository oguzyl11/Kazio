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
public final class CheckAndUpdateRecordsUseCase_Factory implements Factory<CheckAndUpdateRecordsUseCase> {
  private final Provider<PersonalRecordRepository> personalRecordRepositoryProvider;

  private final Provider<GetSummaryUseCase> getSummaryUseCaseProvider;

  public CheckAndUpdateRecordsUseCase_Factory(
      Provider<PersonalRecordRepository> personalRecordRepositoryProvider,
      Provider<GetSummaryUseCase> getSummaryUseCaseProvider) {
    this.personalRecordRepositoryProvider = personalRecordRepositoryProvider;
    this.getSummaryUseCaseProvider = getSummaryUseCaseProvider;
  }

  @Override
  public CheckAndUpdateRecordsUseCase get() {
    return newInstance(personalRecordRepositoryProvider.get(), getSummaryUseCaseProvider.get());
  }

  public static CheckAndUpdateRecordsUseCase_Factory create(
      Provider<PersonalRecordRepository> personalRecordRepositoryProvider,
      Provider<GetSummaryUseCase> getSummaryUseCaseProvider) {
    return new CheckAndUpdateRecordsUseCase_Factory(personalRecordRepositoryProvider, getSummaryUseCaseProvider);
  }

  public static CheckAndUpdateRecordsUseCase newInstance(
      PersonalRecordRepository personalRecordRepository, GetSummaryUseCase getSummaryUseCase) {
    return new CheckAndUpdateRecordsUseCase(personalRecordRepository, getSummaryUseCase);
  }
}
