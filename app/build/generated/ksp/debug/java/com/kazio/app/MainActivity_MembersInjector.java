package com.kazio.app;

import com.kazio.app.data.local.datastore.DataStoreRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<DataStoreRepository> dataStoreRepositoryProvider;

  public MainActivity_MembersInjector(Provider<DataStoreRepository> dataStoreRepositoryProvider) {
    this.dataStoreRepositoryProvider = dataStoreRepositoryProvider;
  }

  public static MembersInjector<MainActivity> create(
      Provider<DataStoreRepository> dataStoreRepositoryProvider) {
    return new MainActivity_MembersInjector(dataStoreRepositoryProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectDataStoreRepository(instance, dataStoreRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.kazio.app.MainActivity.dataStoreRepository")
  public static void injectDataStoreRepository(MainActivity instance,
      DataStoreRepository dataStoreRepository) {
    instance.dataStoreRepository = dataStoreRepository;
  }
}
