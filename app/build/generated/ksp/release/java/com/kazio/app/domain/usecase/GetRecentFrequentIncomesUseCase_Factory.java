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
public final class GetRecentFrequentIncomesUseCase_Factory implements Factory<GetRecentFrequentIncomesUseCase> {
  private final Provider<IncomeRepository> incomeRepositoryProvider;

  public GetRecentFrequentIncomesUseCase_Factory(
      Provider<IncomeRepository> incomeRepositoryProvider) {
    this.incomeRepositoryProvider = incomeRepositoryProvider;
  }

  @Override
  public GetRecentFrequentIncomesUseCase get() {
    return newInstance(incomeRepositoryProvider.get());
  }

  public static GetRecentFrequentIncomesUseCase_Factory create(
      Provider<IncomeRepository> incomeRepositoryProvider) {
    return new GetRecentFrequentIncomesUseCase_Factory(incomeRepositoryProvider);
  }

  public static GetRecentFrequentIncomesUseCase newInstance(IncomeRepository incomeRepository) {
    return new GetRecentFrequentIncomesUseCase(incomeRepository);
  }
}
