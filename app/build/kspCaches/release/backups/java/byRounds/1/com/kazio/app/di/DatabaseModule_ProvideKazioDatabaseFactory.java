package com.kazio.app.di;

import android.content.Context;
import com.kazio.app.data.local.room.KazioDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideKazioDatabaseFactory implements Factory<KazioDatabase> {
  private final Provider<Context> contextProvider;

  private final Provider<KazioDatabase> providerProvider;

  public DatabaseModule_ProvideKazioDatabaseFactory(Provider<Context> contextProvider,
      Provider<KazioDatabase> providerProvider) {
    this.contextProvider = contextProvider;
    this.providerProvider = providerProvider;
  }

  @Override
  public KazioDatabase get() {
    return provideKazioDatabase(contextProvider.get(), providerProvider);
  }

  public static DatabaseModule_ProvideKazioDatabaseFactory create(Provider<Context> contextProvider,
      Provider<KazioDatabase> providerProvider) {
    return new DatabaseModule_ProvideKazioDatabaseFactory(contextProvider, providerProvider);
  }

  public static KazioDatabase provideKazioDatabase(Context context,
      Provider<KazioDatabase> provider) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideKazioDatabase(context, provider));
  }
}
