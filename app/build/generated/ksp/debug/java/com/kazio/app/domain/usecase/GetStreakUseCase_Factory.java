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
public final class GetStreakUseCase_Factory implements Factory<GetStreakUseCase> {
  private final Provider<IncomeRepository> incomeRepositoryProvider;

  private final Provider<ExpenseRepository> expenseRepositoryProvider;

  public GetStreakUseCase_Factory(Provider<IncomeRepository> incomeRepositoryProvider,
      Provider<ExpenseRepository> expenseRepositoryProvider) {
    this.incomeRepositoryProvider = incomeRepositoryProvider;
    this.expenseRepositoryProvider = expenseRepositoryProvider;
  }

  @Override
  public GetStreakUseCase get() {
    return newInstance(incomeRepositoryProvider.get(), expenseRepositoryProvider.get());
  }

  public static GetStreakUseCase_Factory create(Provider<IncomeRepository> incomeRepositoryProvider,
      Provider<ExpenseRepository> expenseRepositoryProvider) {
    return new GetStreakUseCase_Factory(incomeRepositoryProvider, expenseRepositoryProvider);
  }

  public static GetStreakUseCase newInstance(IncomeRepository incomeRepository,
      ExpenseRepository expenseRepository) {
    return new GetStreakUseCase(incomeRepository, expenseRepository);
  }
}
