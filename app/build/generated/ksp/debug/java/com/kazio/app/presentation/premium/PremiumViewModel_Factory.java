package com.kazio.app.presentation.premium;

import com.kazio.app.data.billing.BillingManager;
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
public final class PremiumViewModel_Factory implements Factory<PremiumViewModel> {
  private final Provider<BillingManager> billingManagerProvider;

  private final Provider<DataStoreRepository> dataStoreRepositoryProvider;

  public PremiumViewModel_Factory(Provider<BillingManager> billingManagerProvider,
      Provider<DataStoreRepository> dataStoreRepositoryProvider) {
    this.billingManagerProvider = billingManagerProvider;
    this.dataStoreRepositoryProvider = dataStoreRepositoryProvider;
  }

  @Override
  public PremiumViewModel get() {
    return newInstance(billingManagerProvider.get(), dataStoreRepositoryProvider.get());
  }

  public static PremiumViewModel_Factory create(Provider<BillingManager> billingManagerProvider,
      Provider<DataStoreRepository> dataStoreRepositoryProvider) {
    return new PremiumViewModel_Factory(billingManagerProvider, dataStoreRepositoryProvider);
  }

  public static PremiumViewModel newInstance(BillingManager billingManager,
      DataStoreRepository dataStoreRepository) {
    return new PremiumViewModel(billingManager, dataStoreRepository);
  }
}
