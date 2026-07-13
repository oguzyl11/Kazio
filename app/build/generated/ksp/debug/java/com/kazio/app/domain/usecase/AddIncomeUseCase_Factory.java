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

  public AddIncomeUseCase_Factory(Provider<IncomeRepository> incomeRepositoryProvider) {
    this.incomeRepositoryProvider = incomeRepositoryProvider;
  }

  @Override
  public AddIncomeUseCase get() {
    return newInstance(incomeRepositoryProvider.get());
  }

  public static AddIncomeUseCase_Factory create(
      Provider<IncomeRepository> incomeRepositoryProvider) {
    return new AddIncomeUseCase_Factory(incomeRepositoryProvider);
  }

  public static AddIncomeUseCase newInstance(IncomeRepository incomeRepository) {
    return new AddIncomeUseCase(incomeRepository);
  }
}
