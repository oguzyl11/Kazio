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
public final class GetActiveShiftUseCase_Factory implements Factory<GetActiveShiftUseCase> {
  private final Provider<ShiftRepository> shiftRepositoryProvider;

  public GetActiveShiftUseCase_Factory(Provider<ShiftRepository> shiftRepositoryProvider) {
    this.shiftRepositoryProvider = shiftRepositoryProvider;
  }

  @Override
  public GetActiveShiftUseCase get() {
    return newInstance(shiftRepositoryProvider.get());
  }

  public static GetActiveShiftUseCase_Factory create(
      Provider<ShiftRepository> shiftRepositoryProvider) {
    return new GetActiveShiftUseCase_Factory(shiftRepositoryProvider);
  }

  public static GetActiveShiftUseCase newInstance(ShiftRepository shiftRepository) {
    return new GetActiveShiftUseCase(shiftRepository);
  }
}
