package com.kazio.app.data.billing;

import android.content.Context;
import com.kazio.app.data.local.datastore.DataStoreRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class BillingManager_Factory implements Factory<BillingManager> {
  private final Provider<Context> contextProvider;

  private final Provider<DataStoreRepository> dataStoreRepositoryProvider;

  public BillingManager_Factory(Provider<Context> contextProvider,
      Provider<DataStoreRepository> dataStoreRepositoryProvider) {
    this.contextProvider = contextProvider;
    this.dataStoreRepositoryProvider = dataStoreRepositoryProvider;
  }

  @Override
  public BillingManager get() {
    return newInstance(contextProvider.get(), dataStoreRepositoryProvider.get());
  }

  public static BillingManager_Factory create(Provider<Context> contextProvider,
      Provider<DataStoreRepository> dataStoreRepositoryProvider) {
    return new BillingManager_Factory(contextProvider, dataStoreRepositoryProvider);
  }

  public static BillingManager newInstance(Context context,
      DataStoreRepository dataStoreRepository) {
    return new BillingManager(context, dataStoreRepository);
  }
}
