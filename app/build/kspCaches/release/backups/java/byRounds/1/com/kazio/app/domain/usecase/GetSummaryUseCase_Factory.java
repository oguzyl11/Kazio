package com.kazio.app.domain.usecase;

import com.kazio.app.domain.repository.ExpenseRepository;
import com.kazio.app.domain.repository.IncomeRepository;
import com.kazio.app.domain.repository.PlatformRepository;
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
public final class GetSummaryUseCase_Factory implements Factory<GetSummaryUseCase> {
  private final Provider<IncomeRepository> incomeRepositoryProvider;

  private final Provider<ExpenseRepository> expenseRepositoryProvider;

  private final Provider<PlatformRepository> platformRepositoryProvider;

  private final Provider<ShiftRepository> shiftRepositoryProvider;

  public GetSummaryUseCase_Factory(Provider<IncomeRepository> incomeRepositoryProvider,
      Provider<ExpenseRepository> expenseRepositoryProvider,
      Provider<PlatformRepository> platformRepositoryProvider,
      Provider<ShiftRepository> shiftRepositoryProvider) {
    this.incomeRepositoryProvider = incomeRepositoryProvider;
    this.expenseRepositoryProvider = expenseRepositoryProvider;
    this.platformRepositoryProvider = platformRepositoryProvider;
    this.shiftRepositoryProvider = shiftRepositoryProvider;
  }

  @Override
  public GetSummaryUseCase get() {
    return newInstance(incomeRepositoryProvider.get(), expenseRepositoryProvider.get(), platformRepositoryProvider.get(), shiftRepositoryProvider.get());
  }

  public static GetSummaryUseCase_Factory create(
      Provider<IncomeRepository> incomeRepositoryProvider,
      Provider<ExpenseRepository> expenseRepositoryProvider,
      Provider<PlatformRepository> platformRepositoryProvider,
      Provider<ShiftRepository> shiftRepositoryProvider) {
    return new GetSummaryUseCase_Factory(incomeRepositoryProvider, expenseRepositoryProvider, platformRepositoryProvider, shiftRepositoryProvider);
  }

  public static GetSummaryUseCase newInstance(IncomeRepository incomeRepository,
      ExpenseRepository expenseRepository, PlatformRepository platformRepository,
      ShiftRepository shiftRepository) {
    return new GetSummaryUseCase(incomeRepository, expenseRepository, platformRepository, shiftRepository);
  }
}
