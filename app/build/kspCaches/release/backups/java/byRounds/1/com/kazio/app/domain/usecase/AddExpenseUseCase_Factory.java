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
public final class AddExpenseUseCase_Factory implements Factory<AddExpenseUseCase> {
  private final Provider<ExpenseRepository> expenseRepositoryProvider;

  private final Provider<CheckAndUpdateRecordsUseCase> checkAndUpdateRecordsUseCaseProvider;

  public AddExpenseUseCase_Factory(Provider<ExpenseRepository> expenseRepositoryProvider,
      Provider<CheckAndUpdateRecordsUseCase> checkAndUpdateRecordsUseCaseProvider) {
    this.expenseRepositoryProvider = expenseRepositoryProvider;
    this.checkAndUpdateRecordsUseCaseProvider = checkAndUpdateRecordsUseCaseProvider;
  }

  @Override
  public AddExpenseUseCase get() {
    return newInstance(expenseRepositoryProvider.get(), checkAndUpdateRecordsUseCaseProvider.get());
  }

  public static AddExpenseUseCase_Factory create(
      Provider<ExpenseRepository> expenseRepositoryProvider,
      Provider<CheckAndUpdateRecordsUseCase> checkAndUpdateRecordsUseCaseProvider) {
    return new AddExpenseUseCase_Factory(expenseRepositoryProvider, checkAndUpdateRecordsUseCaseProvider);
  }

  public static AddExpenseUseCase newInstance(ExpenseRepository expenseRepository,
      CheckAndUpdateRecordsUseCase checkAndUpdateRecordsUseCase) {
    return new AddExpenseUseCase(expenseRepository, checkAndUpdateRecordsUseCase);
  }
}
