package com.kazio.app.data.repository;

import com.kazio.app.data.local.room.IncomeDao;
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
public final class IncomeRepositoryImpl_Factory implements Factory<IncomeRepositoryImpl> {
  private final Provider<IncomeDao> incomeDaoProvider;

  public IncomeRepositoryImpl_Factory(Provider<IncomeDao> incomeDaoProvider) {
    this.incomeDaoProvider = incomeDaoProvider;
  }

  @Override
  public IncomeRepositoryImpl get() {
    return newInstance(incomeDaoProvider.get());
  }

  public static IncomeRepositoryImpl_Factory create(Provider<IncomeDao> incomeDaoProvider) {
    return new IncomeRepositoryImpl_Factory(incomeDaoProvider);
  }

  public static IncomeRepositoryImpl newInstance(IncomeDao incomeDao) {
    return new IncomeRepositoryImpl(incomeDao);
  }
}
