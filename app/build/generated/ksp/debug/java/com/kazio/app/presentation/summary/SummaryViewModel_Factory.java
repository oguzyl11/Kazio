package com.kazio.app.presentation.summary;

import com.kazio.app.domain.usecase.GetSummaryUseCase;
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
public final class SummaryViewModel_Factory implements Factory<SummaryViewModel> {
  private final Provider<GetSummaryUseCase> getSummaryUseCaseProvider;

  public SummaryViewModel_Factory(Provider<GetSummaryUseCase> getSummaryUseCaseProvider) {
    this.getSummaryUseCaseProvider = getSummaryUseCaseProvider;
  }

  @Override
  public SummaryViewModel get() {
    return newInstance(getSummaryUseCaseProvider.get());
  }

  public static SummaryViewModel_Factory create(
      Provider<GetSummaryUseCase> getSummaryUseCaseProvider) {
    return new SummaryViewModel_Factory(getSummaryUseCaseProvider);
  }

  public static SummaryViewModel newInstance(GetSummaryUseCase getSummaryUseCase) {
    return new SummaryViewModel(getSummaryUseCase);
  }
}
