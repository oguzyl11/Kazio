package com.kazio.app.data.repository;

import com.kazio.app.data.local.room.ShiftDao;
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
public final class ShiftRepositoryImpl_Factory implements Factory<ShiftRepositoryImpl> {
  private final Provider<ShiftDao> shiftDaoProvider;

  public ShiftRepositoryImpl_Factory(Provider<ShiftDao> shiftDaoProvider) {
    this.shiftDaoProvider = shiftDaoProvider;
  }

  @Override
  public ShiftRepositoryImpl get() {
    return newInstance(shiftDaoProvider.get());
  }

  public static ShiftRepositoryImpl_Factory create(Provider<ShiftDao> shiftDaoProvider) {
    return new ShiftRepositoryImpl_Factory(shiftDaoProvider);
  }

  public static ShiftRepositoryImpl newInstance(ShiftDao shiftDao) {
    return new ShiftRepositoryImpl(shiftDao);
  }
}
