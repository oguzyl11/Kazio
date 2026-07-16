package com.kazio.app.presentation.summary;

import com.kazio.app.domain.usecase.GenerateReportUseCase;
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

  private final Provider<GenerateReportUseCase> generateReportUseCaseProvider;

  public SummaryViewModel_Factory(Provider<GetSummaryUseCase> getSummaryUseCaseProvider,
      Provider<GenerateReportUseCase> generateReportUseCaseProvider) {
    this.getSummaryUseCaseProvider = getSummaryUseCaseProvider;
    this.generateReportUseCaseProvider = generateReportUseCaseProvider;
  }

  @Override
  public SummaryViewModel get() {
    return newInstance(getSummaryUseCaseProvider.get(), generateReportUseCaseProvider.get());
  }

  public static SummaryViewModel_Factory create(
      Provider<GetSummaryUseCase> getSummaryUseCaseProvider,
      Provider<GenerateReportUseCase> generateReportUseCaseProvider) {
    return new SummaryViewModel_Factory(getSummaryUseCaseProvider, generateReportUseCaseProvider);
  }

  public static SummaryViewModel newInstance(GetSummaryUseCase getSummaryUseCase,
      GenerateReportUseCase generateReportUseCase) {
    return new SummaryViewModel(getSummaryUseCase, generateReportUseCase);
  }
}
