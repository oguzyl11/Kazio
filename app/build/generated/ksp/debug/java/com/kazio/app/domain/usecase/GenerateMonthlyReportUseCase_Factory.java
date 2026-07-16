package com.kazio.app.domain.usecase;

import com.kazio.app.domain.repository.ExpenseRepository;
import com.kazio.app.domain.repository.IncomeRepository;
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
public final class GenerateMonthlyReportUseCase_Factory implements Factory<GenerateMonthlyReportUseCase> {
  private final Provider<ShiftRepository> shiftRepositoryProvider;

  private final Provider<IncomeRepository> incomeRepositoryProvider;

  private final Provider<ExpenseRepository> expenseRepositoryProvider;

  public GenerateMonthlyReportUseCase_Factory(Provider<ShiftRepository> shiftRepositoryProvider,
      Provider<IncomeRepository> incomeRepositoryProvider,
      Provider<ExpenseRepository> expenseRepositoryProvider) {
    this.shiftRepositoryProvider = shiftRepositoryProvider;
    this.incomeRepositoryProvider = incomeRepositoryProvider;
    this.expenseRepositoryProvider = expenseRepositoryProvider;
  }

  @Override
  public GenerateMonthlyReportUseCase get() {
    return newInstance(shiftRepositoryProvider.get(), incomeRepositoryProvider.get(), expenseRepositoryProvider.get());
  }

  public static GenerateMonthlyReportUseCase_Factory create(
      Provider<ShiftRepository> shiftRepositoryProvider,
      Provider<IncomeRepository> incomeRepositoryProvider,
      Provider<ExpenseRepository> expenseRepositoryProvider) {
    return new GenerateMonthlyReportUseCase_Factory(shiftRepositoryProvider, incomeRepositoryProvider, expenseRepositoryProvider);
  }

  public static GenerateMonthlyReportUseCase newInstance(ShiftRepository shiftRepository,
      IncomeRepository incomeRepository, ExpenseRepository expenseRepository) {
    return new GenerateMonthlyReportUseCase(shiftRepository, incomeRepository, expenseRepository);
  }
}
