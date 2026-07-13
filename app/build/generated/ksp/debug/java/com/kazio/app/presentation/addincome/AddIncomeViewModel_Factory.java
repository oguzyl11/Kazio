package com.kazio.app.presentation.addincome;

import com.kazio.app.domain.usecase.AddIncomeUseCase;
import com.kazio.app.domain.usecase.GetActiveShiftUseCase;
import com.kazio.app.domain.usecase.GetPlatformsUseCase;
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
public final class AddIncomeViewModel_Factory implements Factory<AddIncomeViewModel> {
  private final Provider<GetPlatformsUseCase> getPlatformsUseCaseProvider;

  private final Provider<GetActiveShiftUseCase> getActiveShiftUseCaseProvider;

  private final Provider<AddIncomeUseCase> addIncomeUseCaseProvider;

  public AddIncomeViewModel_Factory(Provider<GetPlatformsUseCase> getPlatformsUseCaseProvider,
      Provider<GetActiveShiftUseCase> getActiveShiftUseCaseProvider,
      Provider<AddIncomeUseCase> addIncomeUseCaseProvider) {
    this.getPlatformsUseCaseProvider = getPlatformsUseCaseProvider;
    this.getActiveShiftUseCaseProvider = getActiveShiftUseCaseProvider;
    this.addIncomeUseCaseProvider = addIncomeUseCaseProvider;
  }

  @Override
  public AddIncomeViewModel get() {
    return newInstance(getPlatformsUseCaseProvider.get(), getActiveShiftUseCaseProvider.get(), addIncomeUseCaseProvider.get());
  }

  public static AddIncomeViewModel_Factory create(
      Provider<GetPlatformsUseCase> getPlatformsUseCaseProvider,
      Provider<GetActiveShiftUseCase> getActiveShiftUseCaseProvider,
      Provider<AddIncomeUseCase> addIncomeUseCaseProvider) {
    return new AddIncomeViewModel_Factory(getPlatformsUseCaseProvider, getActiveShiftUseCaseProvider, addIncomeUseCaseProvider);
  }

  public static AddIncomeViewModel newInstance(GetPlatformsUseCase getPlatformsUseCase,
      GetActiveShiftUseCase getActiveShiftUseCase, AddIncomeUseCase addIncomeUseCase) {
    return new AddIncomeViewModel(getPlatformsUseCase, getActiveShiftUseCase, addIncomeUseCase);
  }
}
