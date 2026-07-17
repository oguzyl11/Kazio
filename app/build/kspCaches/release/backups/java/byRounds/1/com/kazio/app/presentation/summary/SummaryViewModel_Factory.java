package com.kazio.app.presentation.summary;

import com.kazio.app.data.local.datastore.DataStoreRepository;
import com.kazio.app.domain.usecase.GenerateReportUseCase;
import com.kazio.app.domain.usecase.GetPersonalRecordsUseCase;
import com.kazio.app.domain.usecase.GetSummaryUseCase;
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
public final class SummaryViewModel_Factory implements Factory<SummaryViewModel> {
  private final Provider<GetSummaryUseCase> getSummaryUseCaseProvider;

  private final Provider<GenerateReportUseCase> generateReportUseCaseProvider;

  private final Provider<GetPersonalRecordsUseCase> getPersonalRecordsUseCaseProvider;

  private final Provider<DataStoreRepository> dataStoreRepositoryProvider;

  public SummaryViewModel_Factory(Provider<GetSummaryUseCase> getSummaryUseCaseProvider,
      Provider<GenerateReportUseCase> generateReportUseCaseProvider,
      Provider<GetPersonalRecordsUseCase> getPersonalRecordsUseCaseProvider,
      Provider<DataStoreRepository> dataStoreRepositoryProvider) {
    this.getSummaryUseCaseProvider = getSummaryUseCaseProvider;
    this.generateReportUseCaseProvider = generateReportUseCaseProvider;
    this.getPersonalRecordsUseCaseProvider = getPersonalRecordsUseCaseProvider;
    this.dataStoreRepositoryProvider = dataStoreRepositoryProvider;
  }

  @Override
  public SummaryViewModel get() {
    return newInstance(getSummaryUseCaseProvider.get(), generateReportUseCaseProvider.get(), getPersonalRecordsUseCaseProvider.get(), dataStoreRepositoryProvider.get());
  }

  public static SummaryViewModel_Factory create(
      Provider<GetSummaryUseCase> getSummaryUseCaseProvider,
      Provider<GenerateReportUseCase> generateReportUseCaseProvider,
      Provider<GetPersonalRecordsUseCase> getPersonalRecordsUseCaseProvider,
      Provider<DataStoreRepository> dataStoreRepositoryProvider) {
    return new SummaryViewModel_Factory(getSummaryUseCaseProvider, generateReportUseCaseProvider, getPersonalRecordsUseCaseProvider, dataStoreRepositoryProvider);
  }

  public static SummaryViewModel newInstance(GetSummaryUseCase getSummaryUseCase,
      GenerateReportUseCase generateReportUseCase,
      GetPersonalRecordsUseCase getPersonalRecordsUseCase,
      DataStoreRepository dataStoreRepository) {
    return new SummaryViewModel(getSummaryUseCase, generateReportUseCase, getPersonalRecordsUseCase, dataStoreRepository);
  }
}
