package com.kazio.app.presentation.auth;

import com.kazio.app.data.local.datastore.DataStoreRepository;
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
public final class AuthViewModel_Factory implements Factory<AuthViewModel> {
  private final Provider<DataStoreRepository> dataStoreRepositoryProvider;

  public AuthViewModel_Factory(Provider<DataStoreRepository> dataStoreRepositoryProvider) {
    this.dataStoreRepositoryProvider = dataStoreRepositoryProvider;
  }

  @Override
  public AuthViewModel get() {
    return newInstance(dataStoreRepositoryProvider.get());
  }

  public static AuthViewModel_Factory create(
      Provider<DataStoreRepository> dataStoreRepositoryProvider) {
    return new AuthViewModel_Factory(dataStoreRepositoryProvider);
  }

  public static AuthViewModel newInstance(DataStoreRepository dataStoreRepository) {
    return new AuthViewModel(dataStoreRepository);
  }
}
