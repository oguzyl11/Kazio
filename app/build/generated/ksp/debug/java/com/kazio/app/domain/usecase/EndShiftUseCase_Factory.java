package com.kazio.app.domain.usecase;

import com.kazio.app.domain.repository.ShiftRepository;
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
public final class EndShiftUseCase_Factory implements Factory<EndShiftUseCase> {
  private final Provider<ShiftRepository> shiftRepositoryProvider;

  private final Provider<GetActiveShiftUseCase> getActiveShiftUseCaseProvider;

  private final Provider<GetActiveShiftIncomeUseCase> getActiveShiftIncomeUseCaseProvider;

  private final Provider<CheckAndUpdateRecordsUseCase> checkAndUpdateRecordsUseCaseProvider;

  public EndShiftUseCase_Factory(Provider<ShiftRepository> shiftRepositoryProvider,
      Provider<GetActiveShiftUseCase> getActiveShiftUseCaseProvider,
      Provider<GetActiveShiftIncomeUseCase> getActiveShiftIncomeUseCaseProvider,
      Provider<CheckAndUpdateRecordsUseCase> checkAndUpdateRecordsUseCaseProvider) {
    this.shiftRepositoryProvider = shiftRepositoryProvider;
    this.getActiveShiftUseCaseProvider = getActiveShiftUseCaseProvider;
    this.getActiveShiftIncomeUseCaseProvider = getActiveShiftIncomeUseCaseProvider;
    this.checkAndUpdateRecordsUseCaseProvider = checkAndUpdateRecordsUseCaseProvider;
  }

  @Override
  public EndShiftUseCase get() {
    return newInstance(shiftRepositoryProvider.get(), getActiveShiftUseCaseProvider.get(), getActiveShiftIncomeUseCaseProvider.get(), checkAndUpdateRecordsUseCaseProvider.get());
  }

  public static EndShiftUseCase_Factory create(Provider<ShiftRepository> shiftRepositoryProvider,
      Provider<GetActiveShiftUseCase> getActiveShiftUseCaseProvider,
      Provider<GetActiveShiftIncomeUseCase> getActiveShiftIncomeUseCaseProvider,
      Provider<CheckAndUpdateRecordsUseCase> checkAndUpdateRecordsUseCaseProvider) {
    return new EndShiftUseCase_Factory(shiftRepositoryProvider, getActiveShiftUseCaseProvider, getActiveShiftIncomeUseCaseProvider, checkAndUpdateRecordsUseCaseProvider);
  }

  public static EndShiftUseCase newInstance(ShiftRepository shiftRepository,
      GetActiveShiftUseCase getActiveShiftUseCase,
      GetActiveShiftIncomeUseCase getActiveShiftIncomeUseCase,
      CheckAndUpdateRecordsUseCase checkAndUpdateRecordsUseCase) {
    return new EndShiftUseCase(shiftRepository, getActiveShiftUseCase, getActiveShiftIncomeUseCase, checkAndUpdateRecordsUseCase);
  }
}
