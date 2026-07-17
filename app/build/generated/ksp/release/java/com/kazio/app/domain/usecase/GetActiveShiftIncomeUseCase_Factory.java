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
public final class GetActiveShiftIncomeUseCase_Factory implements Factory<GetActiveShiftIncomeUseCase> {
  private final Provider<IncomeRepository> incomeRepositoryProvider;

  public GetActiveShiftIncomeUseCase_Factory(Provider<IncomeRepository> incomeRepositoryProvider) {
    this.incomeRepositoryProvider = incomeRepositoryProvider;
  }

  @Override
  public GetActiveShiftIncomeUseCase get() {
    return newInstance(incomeRepositoryProvider.get());
  }

  public static GetActiveShiftIncomeUseCase_Factory create(
      Provider<IncomeRepository> incomeRepositoryProvider) {
    return new GetActiveShiftIncomeUseCase_Factory(incomeRepositoryProvider);
  }

  public static GetActiveShiftIncomeUseCase newInstance(IncomeRepository incomeRepository) {
    return new GetActiveShiftIncomeUseCase(incomeRepository);
  }
}
