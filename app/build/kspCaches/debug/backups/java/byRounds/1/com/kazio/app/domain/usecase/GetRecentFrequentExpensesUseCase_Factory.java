package com.kazio.app.domain.usecase;

import com.kazio.app.domain.repository.ExpenseRepository;
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
public final class GetRecentFrequentExpensesUseCase_Factory implements Factory<GetRecentFrequentExpensesUseCase> {
  private final Provider<ExpenseRepository> expenseRepositoryProvider;

  public GetRecentFrequentExpensesUseCase_Factory(
      Provider<ExpenseRepository> expenseRepositoryProvider) {
    this.expenseRepositoryProvider = expenseRepositoryProvider;
  }

  @Override
  public GetRecentFrequentExpensesUseCase get() {
    return newInstance(expenseRepositoryProvider.get());
  }

  public static GetRecentFrequentExpensesUseCase_Factory create(
      Provider<ExpenseRepository> expenseRepositoryProvider) {
    return new GetRecentFrequentExpensesUseCase_Factory(expenseRepositoryProvider);
  }

  public static GetRecentFrequentExpensesUseCase newInstance(ExpenseRepository expenseRepository) {
    return new GetRecentFrequentExpensesUseCase(expenseRepository);
  }
}
