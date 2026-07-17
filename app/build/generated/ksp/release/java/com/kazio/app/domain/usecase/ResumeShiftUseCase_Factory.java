package com.kazio.app.domain.usecase;

import com.kazio.app.domain.repository.ShiftRepository;
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
public final class ResumeShiftUseCase_Factory implements Factory<ResumeShiftUseCase> {
  private final Provider<ShiftRepository> shiftRepositoryProvider;

  private final Provider<GetActiveShiftUseCase> getActiveShiftUseCaseProvider;

  public ResumeShiftUseCase_Factory(Provider<ShiftRepository> shiftRepositoryProvider,
      Provider<GetActiveShiftUseCase> getActiveShiftUseCaseProvider) {
    this.shiftRepositoryProvider = shiftRepositoryProvider;
    this.getActiveShiftUseCaseProvider = getActiveShiftUseCaseProvider;
  }

  @Override
  public ResumeShiftUseCase get() {
    return newInstance(shiftRepositoryProvider.get(), getActiveShiftUseCaseProvider.get());
  }

  public static ResumeShiftUseCase_Factory create(Provider<ShiftRepository> shiftRepositoryProvider,
      Provider<GetActiveShiftUseCase> getActiveShiftUseCaseProvider) {
    return new ResumeShiftUseCase_Factory(shiftRepositoryProvider, getActiveShiftUseCaseProvider);
  }

  public static ResumeShiftUseCase newInstance(ShiftRepository shiftRepository,
      GetActiveShiftUseCase getActiveShiftUseCase) {
    return new ResumeShiftUseCase(shiftRepository, getActiveShiftUseCase);
  }
}
