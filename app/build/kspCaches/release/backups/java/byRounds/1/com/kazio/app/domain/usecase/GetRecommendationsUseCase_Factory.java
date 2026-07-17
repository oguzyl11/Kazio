package com.kazio.app.domain.usecase;

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
public final class GetRecommendationsUseCase_Factory implements Factory<GetRecommendationsUseCase> {
  private final Provider<GetSummaryUseCase> getSummaryUseCaseProvider;

  public GetRecommendationsUseCase_Factory(Provider<GetSummaryUseCase> getSummaryUseCaseProvider) {
    this.getSummaryUseCaseProvider = getSummaryUseCaseProvider;
  }

  @Override
  public GetRecommendationsUseCase get() {
    return newInstance(getSummaryUseCaseProvider.get());
  }

  public static GetRecommendationsUseCase_Factory create(
      Provider<GetSummaryUseCase> getSummaryUseCaseProvider) {
    return new GetRecommendationsUseCase_Factory(getSummaryUseCaseProvider);
  }

  public static GetRecommendationsUseCase newInstance(GetSummaryUseCase getSummaryUseCase) {
    return new GetRecommendationsUseCase(getSummaryUseCase);
  }
}
