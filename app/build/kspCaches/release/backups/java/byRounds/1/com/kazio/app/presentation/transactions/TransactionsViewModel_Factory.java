package com.kazio.app.presentation.transactions;

import com.kazio.app.domain.repository.ExpenseRepository;
import com.kazio.app.domain.repository.IncomeRepository;
import com.kazio.app.domain.usecase.DeleteExpenseUseCase;
import com.kazio.app.domain.usecase.DeleteIncomeUseCase;
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
public final class TransactionsViewModel_Factory implements Factory<TransactionsViewModel> {
  private final Provider<IncomeRepository> incomeRepositoryProvider;

  private final Provider<ExpenseRepository> expenseRepositoryProvider;

  private final Provider<DeleteIncomeUseCase> deleteIncomeUseCaseProvider;

  private final Provider<DeleteExpenseUseCase> deleteExpenseUseCaseProvider;

  public TransactionsViewModel_Factory(Provider<IncomeRepository> incomeRepositoryProvider,
      Provider<ExpenseRepository> expenseRepositoryProvider,
      Provider<DeleteIncomeUseCase> deleteIncomeUseCaseProvider,
      Provider<DeleteExpenseUseCase> deleteExpenseUseCaseProvider) {
    this.incomeRepositoryProvider = incomeRepositoryProvider;
    this.expenseRepositoryProvider = expenseRepositoryProvider;
    this.deleteIncomeUseCaseProvider = deleteIncomeUseCaseProvider;
    this.deleteExpenseUseCaseProvider = deleteExpenseUseCaseProvider;
  }

  @Override
  public TransactionsViewModel get() {
    return newInstance(incomeRepositoryProvider.get(), expenseRepositoryProvider.get(), deleteIncomeUseCaseProvider.get(), deleteExpenseUseCaseProvider.get());
  }

  public static TransactionsViewModel_Factory create(
      Provider<IncomeRepository> incomeRepositoryProvider,
      Provider<ExpenseRepository> expenseRepositoryProvider,
      Provider<DeleteIncomeUseCase> deleteIncomeUseCaseProvider,
      Provider<DeleteExpenseUseCase> deleteExpenseUseCaseProvider) {
    return new TransactionsViewModel_Factory(incomeRepositoryProvider, expenseRepositoryProvider, deleteIncomeUseCaseProvider, deleteExpenseUseCaseProvider);
  }

  public static TransactionsViewModel newInstance(IncomeRepository incomeRepository,
      ExpenseRepository expenseRepository, DeleteIncomeUseCase deleteIncomeUseCase,
      DeleteExpenseUseCase deleteExpenseUseCase) {
    return new TransactionsViewModel(incomeRepository, expenseRepository, deleteIncomeUseCase, deleteExpenseUseCase);
  }
}
