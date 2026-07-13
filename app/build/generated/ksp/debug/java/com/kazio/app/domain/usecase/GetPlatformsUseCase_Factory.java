package com.kazio.app.domain.usecase;

import com.kazio.app.domain.repository.PlatformRepository;
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
public final class GetPlatformsUseCase_Factory implements Factory<GetPlatformsUseCase> {
  private final Provider<PlatformRepository> platformRepositoryProvider;

  public GetPlatformsUseCase_Factory(Provider<PlatformRepository> platformRepositoryProvider) {
    this.platformRepositoryProvider = platformRepositoryProvider;
  }

  @Override
  public GetPlatformsUseCase get() {
    return newInstance(platformRepositoryProvider.get());
  }

  public static GetPlatformsUseCase_Factory create(
      Provider<PlatformRepository> platformRepositoryProvider) {
    return new GetPlatformsUseCase_Factory(platformRepositoryProvider);
  }

  public static GetPlatformsUseCase newInstance(PlatformRepository platformRepository) {
    return new GetPlatformsUseCase(platformRepository);
  }
}
