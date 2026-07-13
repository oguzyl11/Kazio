package com.kazio.app.domain.usecase;

import com.kazio.app.domain.repository.ExpenseRepository;
import com.kazio.app.domain.repository.IncomeRepository;
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
public final class CalculateDailyNetUseCase_Factory implements Factory<CalculateDailyNetUseCase> {
  private final Provider<IncomeRepository> incomeRepositoryProvider;

  private final Provider<ExpenseRepository> expenseRepositoryProvider;

  public CalculateDailyNetUseCase_Factory(Provider<IncomeRepository> incomeRepositoryProvider,
      Provider<ExpenseRepository> expenseRepositoryProvider) {
    this.incomeRepositoryProvider = incomeRepositoryProvider;
    this.expenseRepositoryProvider = expenseRepositoryProvider;
  }

  @Override
  public CalculateDailyNetUseCase get() {
    return newInstance(incomeRepositoryProvider.get(), expenseRepositoryProvider.get());
  }

  public static CalculateDailyNetUseCase_Factory create(
      Provider<IncomeRepository> incomeRepositoryProvider,
      Provider<ExpenseRepository> expenseRepositoryProvider) {
    return new CalculateDailyNetUseCase_Factory(incomeRepositoryProvider, expenseRepositoryProvider);
  }

  public static CalculateDailyNetUseCase newInstance(IncomeRepository incomeRepository,
      ExpenseRepository expenseRepository) {
    return new CalculateDailyNetUseCase(incomeRepository, expenseRepository);
  }
}
