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
public final class UpdateIncomeUseCase_Factory implements Factory<UpdateIncomeUseCase> {
  private final Provider<IncomeRepository> incomeRepositoryProvider;

  public UpdateIncomeUseCase_Factory(Provider<IncomeRepository> incomeRepositoryProvider) {
    this.incomeRepositoryProvider = incomeRepositoryProvider;
  }

  @Override
  public UpdateIncomeUseCase get() {
    return newInstance(incomeRepositoryProvider.get());
  }

  public static UpdateIncomeUseCase_Factory create(
      Provider<IncomeRepository> incomeRepositoryProvider) {
    return new UpdateIncomeUseCase_Factory(incomeRepositoryProvider);
  }

  public static UpdateIncomeUseCase newInstance(IncomeRepository incomeRepository) {
    return new UpdateIncomeUseCase(incomeRepository);
  }
}
