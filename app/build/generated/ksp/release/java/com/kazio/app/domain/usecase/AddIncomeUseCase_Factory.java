package com.kazio.app.domain.usecase;

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
public final class AddIncomeUseCase_Factory implements Factory<AddIncomeUseCase> {
  private final Provider<IncomeRepository> incomeRepositoryProvider;

  private final Provider<CheckAndUpdateRecordsUseCase> checkAndUpdateRecordsUseCaseProvider;

  public AddIncomeUseCase_Factory(Provider<IncomeRepository> incomeRepositoryProvider,
      Provider<CheckAndUpdateRecordsUseCase> checkAndUpdateRecordsUseCaseProvider) {
    this.incomeRepositoryProvider = incomeRepositoryProvider;
    this.checkAndUpdateRecordsUseCaseProvider = checkAndUpdateRecordsUseCaseProvider;
  }

  @Override
  public AddIncomeUseCase get() {
    return newInstance(incomeRepositoryProvider.get(), checkAndUpdateRecordsUseCaseProvider.get());
  }

  public static AddIncomeUseCase_Factory create(Provider<IncomeRepository> incomeRepositoryProvider,
      Provider<CheckAndUpdateRecordsUseCase> checkAndUpdateRecordsUseCaseProvider) {
    return new AddIncomeUseCase_Factory(incomeRepositoryProvider, checkAndUpdateRecordsUseCaseProvider);
  }

  public static AddIncomeUseCase newInstance(IncomeRepository incomeRepository,
      CheckAndUpdateRecordsUseCase checkAndUpdateRecordsUseCase) {
    return new AddIncomeUseCase(incomeRepository, checkAndUpdateRecordsUseCase);
  }
}
