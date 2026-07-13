package com.kazio.app;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.kazio.app.data.local.datastore.DataStoreRepository;
import com.kazio.app.data.local.room.ExpenseDao;
import com.kazio.app.data.local.room.IncomeDao;
import com.kazio.app.data.local.room.KazioDatabase;
import com.kazio.app.data.local.room.PlatformDao;
import com.kazio.app.data.local.room.ShiftDao;
import com.kazio.app.data.repository.ExpenseRepositoryImpl;
import com.kazio.app.data.repository.IncomeRepositoryImpl;
import com.kazio.app.data.repository.PlatformRepositoryImpl;
import com.kazio.app.data.repository.ShiftRepositoryImpl;
import com.kazio.app.di.DatabaseModule_ProvideExpenseDaoFactory;
import com.kazio.app.di.DatabaseModule_ProvideIncomeDaoFactory;
import com.kazio.app.di.DatabaseModule_ProvideKazioDatabaseFactory;
import com.kazio.app.di.DatabaseModule_ProvidePlatformDaoFactory;
import com.kazio.app.di.DatabaseModule_ProvideShiftDaoFactory;
import com.kazio.app.domain.repository.ExpenseRepository;
import com.kazio.app.domain.repository.IncomeRepository;
import com.kazio.app.domain.repository.PlatformRepository;
import com.kazio.app.domain.repository.ShiftRepository;
import com.kazio.app.domain.usecase.AddExpenseUseCase;
import com.kazio.app.domain.usecase.AddIncomeUseCase;
import com.kazio.app.domain.usecase.EndShiftUseCase;
import com.kazio.app.domain.usecase.GetActiveShiftUseCase;
import com.kazio.app.domain.usecase.GetPlatformsUseCase;
import com.kazio.app.domain.usecase.GetRecommendationsUseCase;
import com.kazio.app.domain.usecase.GetSummaryUseCase;
import com.kazio.app.domain.usecase.StartShiftUseCase;
import com.kazio.app.presentation.addexpense.AddExpenseViewModel;
import com.kazio.app.presentation.addexpense.AddExpenseViewModel_HiltModules;
import com.kazio.app.presentation.addincome.AddIncomeViewModel;
import com.kazio.app.presentation.addincome.AddIncomeViewModel_HiltModules;
import com.kazio.app.presentation.auth.AuthViewModel;
import com.kazio.app.presentation.auth.AuthViewModel_HiltModules;
import com.kazio.app.presentation.dashboard.DashboardViewModel;
import com.kazio.app.presentation.dashboard.DashboardViewModel_HiltModules;
import com.kazio.app.presentation.summary.SummaryViewModel;
import com.kazio.app.presentation.summary.SummaryViewModel_HiltModules;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DelegateFactory;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

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
public final class DaggerKazioApplication_HiltComponents_SingletonC {
  private DaggerKazioApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public KazioApplication_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements KazioApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public KazioApplication_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements KazioApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public KazioApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements KazioApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public KazioApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements KazioApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public KazioApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements KazioApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public KazioApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements KazioApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public KazioApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements KazioApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public KazioApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends KazioApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends KazioApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends KazioApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends KazioApplication_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
      injectMainActivity2(mainActivity);
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(MapBuilder.<String, Boolean>newMapBuilder(5).put(LazyClassKeyProvider.com_kazio_app_presentation_addexpense_AddExpenseViewModel, AddExpenseViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_kazio_app_presentation_addincome_AddIncomeViewModel, AddIncomeViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_kazio_app_presentation_auth_AuthViewModel, AuthViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_kazio_app_presentation_dashboard_DashboardViewModel, DashboardViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_kazio_app_presentation_summary_SummaryViewModel, SummaryViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    private MainActivity injectMainActivity2(MainActivity instance) {
      MainActivity_MembersInjector.injectDataStoreRepository(instance, singletonCImpl.dataStoreRepositoryProvider.get());
      return instance;
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_kazio_app_presentation_dashboard_DashboardViewModel = "com.kazio.app.presentation.dashboard.DashboardViewModel";

      static String com_kazio_app_presentation_addincome_AddIncomeViewModel = "com.kazio.app.presentation.addincome.AddIncomeViewModel";

      static String com_kazio_app_presentation_auth_AuthViewModel = "com.kazio.app.presentation.auth.AuthViewModel";

      static String com_kazio_app_presentation_addexpense_AddExpenseViewModel = "com.kazio.app.presentation.addexpense.AddExpenseViewModel";

      static String com_kazio_app_presentation_summary_SummaryViewModel = "com.kazio.app.presentation.summary.SummaryViewModel";

      @KeepFieldType
      DashboardViewModel com_kazio_app_presentation_dashboard_DashboardViewModel2;

      @KeepFieldType
      AddIncomeViewModel com_kazio_app_presentation_addincome_AddIncomeViewModel2;

      @KeepFieldType
      AuthViewModel com_kazio_app_presentation_auth_AuthViewModel2;

      @KeepFieldType
      AddExpenseViewModel com_kazio_app_presentation_addexpense_AddExpenseViewModel2;

      @KeepFieldType
      SummaryViewModel com_kazio_app_presentation_summary_SummaryViewModel2;
    }
  }

  private static final class ViewModelCImpl extends KazioApplication_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AddExpenseViewModel> addExpenseViewModelProvider;

    private Provider<AddIncomeViewModel> addIncomeViewModelProvider;

    private Provider<AuthViewModel> authViewModelProvider;

    private Provider<DashboardViewModel> dashboardViewModelProvider;

    private Provider<SummaryViewModel> summaryViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    private GetActiveShiftUseCase getActiveShiftUseCase() {
      return new GetActiveShiftUseCase(singletonCImpl.bindShiftRepositoryProvider.get());
    }

    private AddExpenseUseCase addExpenseUseCase() {
      return new AddExpenseUseCase(singletonCImpl.bindExpenseRepositoryProvider.get());
    }

    private GetPlatformsUseCase getPlatformsUseCase() {
      return new GetPlatformsUseCase(singletonCImpl.bindPlatformRepositoryProvider.get());
    }

    private AddIncomeUseCase addIncomeUseCase() {
      return new AddIncomeUseCase(singletonCImpl.bindIncomeRepositoryProvider.get());
    }

    private StartShiftUseCase startShiftUseCase() {
      return new StartShiftUseCase(singletonCImpl.bindShiftRepositoryProvider.get(), getActiveShiftUseCase());
    }

    private EndShiftUseCase endShiftUseCase() {
      return new EndShiftUseCase(singletonCImpl.bindShiftRepositoryProvider.get(), getActiveShiftUseCase());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.addExpenseViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.addIncomeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.authViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.dashboardViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.summaryViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(5).put(LazyClassKeyProvider.com_kazio_app_presentation_addexpense_AddExpenseViewModel, ((Provider) addExpenseViewModelProvider)).put(LazyClassKeyProvider.com_kazio_app_presentation_addincome_AddIncomeViewModel, ((Provider) addIncomeViewModelProvider)).put(LazyClassKeyProvider.com_kazio_app_presentation_auth_AuthViewModel, ((Provider) authViewModelProvider)).put(LazyClassKeyProvider.com_kazio_app_presentation_dashboard_DashboardViewModel, ((Provider) dashboardViewModelProvider)).put(LazyClassKeyProvider.com_kazio_app_presentation_summary_SummaryViewModel, ((Provider) summaryViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return Collections.<Class<?>, Object>emptyMap();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_kazio_app_presentation_addexpense_AddExpenseViewModel = "com.kazio.app.presentation.addexpense.AddExpenseViewModel";

      static String com_kazio_app_presentation_dashboard_DashboardViewModel = "com.kazio.app.presentation.dashboard.DashboardViewModel";

      static String com_kazio_app_presentation_auth_AuthViewModel = "com.kazio.app.presentation.auth.AuthViewModel";

      static String com_kazio_app_presentation_summary_SummaryViewModel = "com.kazio.app.presentation.summary.SummaryViewModel";

      static String com_kazio_app_presentation_addincome_AddIncomeViewModel = "com.kazio.app.presentation.addincome.AddIncomeViewModel";

      @KeepFieldType
      AddExpenseViewModel com_kazio_app_presentation_addexpense_AddExpenseViewModel2;

      @KeepFieldType
      DashboardViewModel com_kazio_app_presentation_dashboard_DashboardViewModel2;

      @KeepFieldType
      AuthViewModel com_kazio_app_presentation_auth_AuthViewModel2;

      @KeepFieldType
      SummaryViewModel com_kazio_app_presentation_summary_SummaryViewModel2;

      @KeepFieldType
      AddIncomeViewModel com_kazio_app_presentation_addincome_AddIncomeViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.kazio.app.presentation.addexpense.AddExpenseViewModel 
          return (T) new AddExpenseViewModel(viewModelCImpl.getActiveShiftUseCase(), viewModelCImpl.addExpenseUseCase());

          case 1: // com.kazio.app.presentation.addincome.AddIncomeViewModel 
          return (T) new AddIncomeViewModel(viewModelCImpl.getPlatformsUseCase(), viewModelCImpl.getActiveShiftUseCase(), viewModelCImpl.addIncomeUseCase());

          case 2: // com.kazio.app.presentation.auth.AuthViewModel 
          return (T) new AuthViewModel(singletonCImpl.dataStoreRepositoryProvider.get());

          case 3: // com.kazio.app.presentation.dashboard.DashboardViewModel 
          return (T) new DashboardViewModel(singletonCImpl.getSummaryUseCase(), singletonCImpl.getRecommendationsUseCase(), viewModelCImpl.getActiveShiftUseCase(), viewModelCImpl.startShiftUseCase(), viewModelCImpl.endShiftUseCase(), singletonCImpl.dataStoreRepositoryProvider.get());

          case 4: // com.kazio.app.presentation.summary.SummaryViewModel 
          return (T) new SummaryViewModel(singletonCImpl.getSummaryUseCase());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends KazioApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends KazioApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends KazioApplication_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<KazioDatabase> provideKazioDatabaseProvider;

    private Provider<IncomeRepositoryImpl> incomeRepositoryImplProvider;

    private Provider<IncomeRepository> bindIncomeRepositoryProvider;

    private Provider<ExpenseRepositoryImpl> expenseRepositoryImplProvider;

    private Provider<ExpenseRepository> bindExpenseRepositoryProvider;

    private Provider<PlatformRepositoryImpl> platformRepositoryImplProvider;

    private Provider<PlatformRepository> bindPlatformRepositoryProvider;

    private Provider<DataStoreRepository> dataStoreRepositoryProvider;

    private Provider<ShiftRepositoryImpl> shiftRepositoryImplProvider;

    private Provider<ShiftRepository> bindShiftRepositoryProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private IncomeDao incomeDao() {
      return DatabaseModule_ProvideIncomeDaoFactory.provideIncomeDao(provideKazioDatabaseProvider.get());
    }

    private ExpenseDao expenseDao() {
      return DatabaseModule_ProvideExpenseDaoFactory.provideExpenseDao(provideKazioDatabaseProvider.get());
    }

    private PlatformDao platformDao() {
      return DatabaseModule_ProvidePlatformDaoFactory.providePlatformDao(provideKazioDatabaseProvider.get());
    }

    private ShiftDao shiftDao() {
      return DatabaseModule_ProvideShiftDaoFactory.provideShiftDao(provideKazioDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideKazioDatabaseProvider = new DelegateFactory<>();
      DelegateFactory.setDelegate(provideKazioDatabaseProvider, DoubleCheck.provider(new SwitchingProvider<KazioDatabase>(singletonCImpl, 1)));
      this.incomeRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 0);
      this.bindIncomeRepositoryProvider = DoubleCheck.provider((Provider) incomeRepositoryImplProvider);
      this.expenseRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 2);
      this.bindExpenseRepositoryProvider = DoubleCheck.provider((Provider) expenseRepositoryImplProvider);
      this.platformRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 3);
      this.bindPlatformRepositoryProvider = DoubleCheck.provider((Provider) platformRepositoryImplProvider);
      this.dataStoreRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<DataStoreRepository>(singletonCImpl, 4));
      this.shiftRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 5);
      this.bindShiftRepositoryProvider = DoubleCheck.provider((Provider) shiftRepositoryImplProvider);
    }

    @Override
    public void injectKazioApplication(KazioApplication kazioApplication) {
    }

    @Override
    public GetSummaryUseCase getSummaryUseCase() {
      return new GetSummaryUseCase(bindIncomeRepositoryProvider.get(), bindExpenseRepositoryProvider.get(), bindPlatformRepositoryProvider.get());
    }

    @Override
    public GetRecommendationsUseCase getRecommendationsUseCase() {
      return new GetRecommendationsUseCase(getSummaryUseCase());
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.kazio.app.data.repository.IncomeRepositoryImpl 
          return (T) new IncomeRepositoryImpl(singletonCImpl.incomeDao());

          case 1: // com.kazio.app.data.local.room.KazioDatabase 
          return (T) DatabaseModule_ProvideKazioDatabaseFactory.provideKazioDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.provideKazioDatabaseProvider);

          case 2: // com.kazio.app.data.repository.ExpenseRepositoryImpl 
          return (T) new ExpenseRepositoryImpl(singletonCImpl.expenseDao());

          case 3: // com.kazio.app.data.repository.PlatformRepositoryImpl 
          return (T) new PlatformRepositoryImpl(singletonCImpl.platformDao());

          case 4: // com.kazio.app.data.local.datastore.DataStoreRepository 
          return (T) new DataStoreRepository(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 5: // com.kazio.app.data.repository.ShiftRepositoryImpl 
          return (T) new ShiftRepositoryImpl(singletonCImpl.shiftDao());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
