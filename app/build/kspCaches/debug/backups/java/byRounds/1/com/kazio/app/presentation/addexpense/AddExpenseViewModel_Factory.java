package com.kazio.app.presentation.addexpense;

import com.kazio.app.domain.usecase.AddExpenseUseCase;
import com.kazio.app.domain.usecase.GetActiveShiftUseCase;
import com.kazio.app.domain.usecase.GetRecentFrequentExpensesUseCase;
import com.kazio.app.domain.usecase.UpdateExpenseUseCase;
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
public final class AddExpenseViewModel_Factory implements Factory<AddExpenseViewModel> {
  private final Provider<GetActiveShiftUseCase> getActiveShiftUseCaseProvider;

  private final Provider<AddExpenseUseCase> addExpenseUseCaseProvider;

  private final Provider<UpdateExpenseUseCase> updateExpenseUseCaseProvider;

  private final Provider<GetRecentFrequentExpensesUseCase> getRecentFrequentExpensesUseCaseProvider;

  public AddExpenseViewModel_Factory(Provider<GetActiveShiftUseCase> getActiveShiftUseCaseProvider,
      Provider<AddExpenseUseCase> addExpenseUseCaseProvider,
      Provider<UpdateExpenseUseCase> updateExpenseUseCaseProvider,
      Provider<GetRecentFrequentExpensesUseCase> getRecentFrequentExpensesUseCaseProvider) {
    this.getActiveShiftUseCaseProvider = getActiveShiftUseCaseProvider;
    this.addExpenseUseCaseProvider = addExpenseUseCaseProvider;
    this.updateExpenseUseCaseProvider = updateExpenseUseCaseProvider;
    this.getRecentFrequentExpensesUseCaseProvider = getRecentFrequentExpensesUseCaseProvider;
  }

  @Override
  public AddExpenseViewModel get() {
    return newInstance(getActiveShiftUseCaseProvider.get(), addExpenseUseCaseProvider.get(), updateExpenseUseCaseProvider.get(), getRecentFrequentExpensesUseCaseProvider.get());
  }

  public static AddExpenseViewModel_Factory create(
      Provider<GetActiveShiftUseCase> getActiveShiftUseCaseProvider,
      Provider<AddExpenseUseCase> addExpenseUseCaseProvider,
      Provider<UpdateExpenseUseCase> updateExpenseUseCaseProvider,
      Provider<GetRecentFrequentExpensesUseCase> getRecentFrequentExpensesUseCaseProvider) {
    return new AddExpenseViewModel_Factory(getActiveShiftUseCaseProvider, addExpenseUseCaseProvider, updateExpenseUseCaseProvider, getRecentFrequentExpensesUseCaseProvider);
  }

  public static AddExpenseViewModel newInstance(GetActiveShiftUseCase getActiveShiftUseCase,
      AddExpenseUseCase addExpenseUseCase, UpdateExpenseUseCase updateExpenseUseCase,
      GetRecentFrequentExpensesUseCase getRecentFrequentExpensesUseCase) {
    return new AddExpenseViewModel(getActiveShiftUseCase, addExpenseUseCase, updateExpenseUseCase, getRecentFrequentExpensesUseCase);
  }
}
