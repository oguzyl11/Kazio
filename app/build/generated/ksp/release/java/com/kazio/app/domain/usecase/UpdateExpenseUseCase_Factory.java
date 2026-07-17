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
public final class UpdateExpenseUseCase_Factory implements Factory<UpdateExpenseUseCase> {
  private final Provider<ExpenseRepository> expenseRepositoryProvider;

  public UpdateExpenseUseCase_Factory(Provider<ExpenseRepository> expenseRepositoryProvider) {
    this.expenseRepositoryProvider = expenseRepositoryProvider;
  }

  @Override
  public UpdateExpenseUseCase get() {
    return newInstance(expenseRepositoryProvider.get());
  }

  public static UpdateExpenseUseCase_Factory create(
      Provider<ExpenseRepository> expenseRepositoryProvider) {
    return new UpdateExpenseUseCase_Factory(expenseRepositoryProvider);
  }

  public static UpdateExpenseUseCase newInstance(ExpenseRepository expenseRepository) {
    return new UpdateExpenseUseCase(expenseRepository);
  }
}
