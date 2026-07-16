package com.kazio.app.presentation.dashboard;

import com.kazio.app.data.local.datastore.DataStoreRepository;
import com.kazio.app.domain.repository.PersonalRecordRepository;
import com.kazio.app.domain.usecase.EndShiftUseCase;
import com.kazio.app.domain.usecase.GenerateReportUseCase;
import com.kazio.app.domain.usecase.GetActiveShiftIncomeUseCase;
import com.kazio.app.domain.usecase.GetActiveShiftUseCase;
import com.kazio.app.domain.usecase.GetRecommendationsUseCase;
import com.kazio.app.domain.usecase.GetSummaryUseCase;
import com.kazio.app.domain.usecase.PauseShiftUseCase;
import com.kazio.app.domain.usecase.ResumeShiftUseCase;
import com.kazio.app.domain.usecase.StartShiftUseCase;
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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<GetSummaryUseCase> getSummaryUseCaseProvider;

  private final Provider<GetRecommendationsUseCase> getRecommendationsUseCaseProvider;

  private final Provider<GetActiveShiftUseCase> getActiveShiftUseCaseProvider;

  private final Provider<GetActiveShiftIncomeUseCase> getActiveShiftIncomeUseCaseProvider;

  private final Provider<StartShiftUseCase> startShiftUseCaseProvider;

  private final Provider<EndShiftUseCase> endShiftUseCaseProvider;

  private final Provider<PauseShiftUseCase> pauseShiftUseCaseProvider;

  private final Provider<ResumeShiftUseCase> resumeShiftUseCaseProvider;

  private final Provider<GenerateReportUseCase> generateReportUseCaseProvider;

  private final Provider<DataStoreRepository> dataStoreRepositoryProvider;

  private final Provider<PersonalRecordRepository> personalRecordRepositoryProvider;

  public DashboardViewModel_Factory(Provider<GetSummaryUseCase> getSummaryUseCaseProvider,
      Provider<GetRecommendationsUseCase> getRecommendationsUseCaseProvider,
      Provider<GetActiveShiftUseCase> getActiveShiftUseCaseProvider,
      Provider<GetActiveShiftIncomeUseCase> getActiveShiftIncomeUseCaseProvider,
      Provider<StartShiftUseCase> startShiftUseCaseProvider,
      Provider<EndShiftUseCase> endShiftUseCaseProvider,
      Provider<PauseShiftUseCase> pauseShiftUseCaseProvider,
      Provider<ResumeShiftUseCase> resumeShiftUseCaseProvider,
      Provider<GenerateReportUseCase> generateReportUseCaseProvider,
      Provider<DataStoreRepository> dataStoreRepositoryProvider,
      Provider<PersonalRecordRepository> personalRecordRepositoryProvider) {
    this.getSummaryUseCaseProvider = getSummaryUseCaseProvider;
    this.getRecommendationsUseCaseProvider = getRecommendationsUseCaseProvider;
    this.getActiveShiftUseCaseProvider = getActiveShiftUseCaseProvider;
    this.getActiveShiftIncomeUseCaseProvider = getActiveShiftIncomeUseCaseProvider;
    this.startShiftUseCaseProvider = startShiftUseCaseProvider;
    this.endShiftUseCaseProvider = endShiftUseCaseProvider;
    this.pauseShiftUseCaseProvider = pauseShiftUseCaseProvider;
    this.resumeShiftUseCaseProvider = resumeShiftUseCaseProvider;
    this.generateReportUseCaseProvider = generateReportUseCaseProvider;
    this.dataStoreRepositoryProvider = dataStoreRepositoryProvider;
    this.personalRecordRepositoryProvider = personalRecordRepositoryProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(getSummaryUseCaseProvider.get(), getRecommendationsUseCaseProvider.get(), getActiveShiftUseCaseProvider.get(), getActiveShiftIncomeUseCaseProvider.get(), startShiftUseCaseProvider.get(), endShiftUseCaseProvider.get(), pauseShiftUseCaseProvider.get(), resumeShiftUseCaseProvider.get(), generateReportUseCaseProvider.get(), dataStoreRepositoryProvider.get(), personalRecordRepositoryProvider.get());
  }

  public static DashboardViewModel_Factory create(
      Provider<GetSummaryUseCase> getSummaryUseCaseProvider,
      Provider<GetRecommendationsUseCase> getRecommendationsUseCaseProvider,
      Provider<GetActiveShiftUseCase> getActiveShiftUseCaseProvider,
      Provider<GetActiveShiftIncomeUseCase> getActiveShiftIncomeUseCaseProvider,
      Provider<StartShiftUseCase> startShiftUseCaseProvider,
      Provider<EndShiftUseCase> endShiftUseCaseProvider,
      Provider<PauseShiftUseCase> pauseShiftUseCaseProvider,
      Provider<ResumeShiftUseCase> resumeShiftUseCaseProvider,
      Provider<GenerateReportUseCase> generateReportUseCaseProvider,
      Provider<DataStoreRepository> dataStoreRepositoryProvider,
      Provider<PersonalRecordRepository> personalRecordRepositoryProvider) {
    return new DashboardViewModel_Factory(getSummaryUseCaseProvider, getRecommendationsUseCaseProvider, getActiveShiftUseCaseProvider, getActiveShiftIncomeUseCaseProvider, startShiftUseCaseProvider, endShiftUseCaseProvider, pauseShiftUseCaseProvider, resumeShiftUseCaseProvider, generateReportUseCaseProvider, dataStoreRepositoryProvider, personalRecordRepositoryProvider);
  }

  public static DashboardViewModel newInstance(GetSummaryUseCase getSummaryUseCase,
      GetRecommendationsUseCase getRecommendationsUseCase,
      GetActiveShiftUseCase getActiveShiftUseCase,
      GetActiveShiftIncomeUseCase getActiveShiftIncomeUseCase, StartShiftUseCase startShiftUseCase,
      EndShiftUseCase endShiftUseCase, PauseShiftUseCase pauseShiftUseCase,
      ResumeShiftUseCase resumeShiftUseCase, GenerateReportUseCase generateReportUseCase,
      DataStoreRepository dataStoreRepository, PersonalRecordRepository personalRecordRepository) {
    return new DashboardViewModel(getSummaryUseCase, getRecommendationsUseCase, getActiveShiftUseCase, getActiveShiftIncomeUseCase, startShiftUseCase, endShiftUseCase, pauseShiftUseCase, resumeShiftUseCase, generateReportUseCase, dataStoreRepository, personalRecordRepository);
  }
}
