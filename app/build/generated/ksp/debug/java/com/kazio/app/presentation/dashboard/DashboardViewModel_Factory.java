package com.kazio.app.presentation.dashboard;

import com.kazio.app.data.local.datastore.DataStoreRepository;
import com.kazio.app.domain.usecase.EndShiftUseCase;
import com.kazio.app.domain.usecase.GetActiveShiftUseCase;
import com.kazio.app.domain.usecase.GetSummaryUseCase;
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

  private final Provider<GetActiveShiftUseCase> getActiveShiftUseCaseProvider;

  private final Provider<StartShiftUseCase> startShiftUseCaseProvider;

  private final Provider<EndShiftUseCase> endShiftUseCaseProvider;

  private final Provider<DataStoreRepository> dataStoreRepositoryProvider;

  public DashboardViewModel_Factory(Provider<GetSummaryUseCase> getSummaryUseCaseProvider,
      Provider<GetActiveShiftUseCase> getActiveShiftUseCaseProvider,
      Provider<StartShiftUseCase> startShiftUseCaseProvider,
      Provider<EndShiftUseCase> endShiftUseCaseProvider,
      Provider<DataStoreRepository> dataStoreRepositoryProvider) {
    this.getSummaryUseCaseProvider = getSummaryUseCaseProvider;
    this.getActiveShiftUseCaseProvider = getActiveShiftUseCaseProvider;
    this.startShiftUseCaseProvider = startShiftUseCaseProvider;
    this.endShiftUseCaseProvider = endShiftUseCaseProvider;
    this.dataStoreRepositoryProvider = dataStoreRepositoryProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(getSummaryUseCaseProvider.get(), getActiveShiftUseCaseProvider.get(), startShiftUseCaseProvider.get(), endShiftUseCaseProvider.get(), dataStoreRepositoryProvider.get());
  }

  public static DashboardViewModel_Factory create(
      Provider<GetSummaryUseCase> getSummaryUseCaseProvider,
      Provider<GetActiveShiftUseCase> getActiveShiftUseCaseProvider,
      Provider<StartShiftUseCase> startShiftUseCaseProvider,
      Provider<EndShiftUseCase> endShiftUseCaseProvider,
      Provider<DataStoreRepository> dataStoreRepositoryProvider) {
    return new DashboardViewModel_Factory(getSummaryUseCaseProvider, getActiveShiftUseCaseProvider, startShiftUseCaseProvider, endShiftUseCaseProvider, dataStoreRepositoryProvider);
  }

  public static DashboardViewModel newInstance(GetSummaryUseCase getSummaryUseCase,
      GetActiveShiftUseCase getActiveShiftUseCase, StartShiftUseCase startShiftUseCase,
      EndShiftUseCase endShiftUseCase, DataStoreRepository dataStoreRepository) {
    return new DashboardViewModel(getSummaryUseCase, getActiveShiftUseCase, startShiftUseCase, endShiftUseCase, dataStoreRepository);
  }
}
