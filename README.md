# DarkCanaryWeatherApp

**Dark Canary** is an Android client that displays the week's forecast using the *Dark Sky API*.  The data requested using Retrofit and RxJava is stored locally using Room and made available offline.  A user is able to click into the daily forecast and view an hourly breakdown of the day.

#### MVVM and Repository

Model-View-View Model is the design pattern that is used for this project.  The ViewModel acts as an abstraction of the view and is responsible for carrying out the business logic that would otherwise be handled in our Activity or Fragments. This results in a seperation of responsibilites and looser coupling throughout our application as the ViewModel is responsible for the delegation of functionality.  The ViewModel also retains the state of the view independent of the lifecycle.

Becuase of the need for our user's location to be available throughout the application we are taking advantage of the new Android Architecture framework's `ViewModelProviders` allowing us to share a single instance of a ViewModel accessible across views.  This is used to pass `MainViewModel` as a parameter to `ForecastViewModel` which is shared between the various fragments in the ViewPager2.  We don't want them to each have their own ViewModel as their lifecycle is short and managed by the ViewPager's backing RecyclerView and its adapter.

We are making our domain responsibilities using the repository pattern which is another abstraction of our data that the ViewModel can call and does not need to know how or where it is getting that data from.  This again provides a loose coupling between the business and domain logic.  The repository and datamanagers are accessed via interface to ensure the contract and allow for additional implementations from different sources in the future.

(We have a `DataManager` exposed to our ViewModel in case the need ever arises that we are pulling from multiple repositories and unique combinations of data are required both for caching and display purposes. This is also where we would like to store our logic for caching, timeouts, and the updating of "stale" data in the future)

Improves :
- Testability
- Maintainability
- Development Speed
 - Extensibility
 
 ![repo pattern](https://user-images.githubusercontent.com/7444521/61232835-1107f800-a6e4-11e9-8bab-bafe03bdf91c.png)

[more from wikipedia](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel)

#### Jetpack and Single Activity Apps

We decided to host all of our fragments in a single activity because our `MainActivity` is responsible for hosting our `ViewPager2` and dynamically generating fragments.  This also aligns with Jetpacks NAvigation Architecture when we decide to introduce that as we navigation away from the ViewPager and down through the view hierarchy.

[Android jetpack](https://android-developers.googleblog.com/2018/05/use-android-jetpack-to-accelerate-your.html?m=1)

#### Sharpeing our Dagger

Using Dagger 2.2 we take advantage of the `DaggerApplication` and `DaggerAppCompatActivity`
![dagger](https://user-images.githubusercontent.com/7444521/61232956-51677600-a6e4-11e9-8ce5-797a5bcb2df5.png)

We have a dependency graph that is handled by our Android Injectors which are supplied instances of our activities and fragments using `@ContributesAndroidInjector` which are then injected when needed using the encapsulated functionality of `DaggerApplication`.  Visualized here as a number of Doctor Octopus arms, each arm allows the injection into a different Android component.  The arms grabs instances when they are contributed to the grabs and then shoves them where they are needed.
![20190715_094603](https://user-images.githubusercontent.com/7444521/61233587-ba032280-a6e5-11e9-980e-7e3cc1125930.jpg)

There is an application graph that handles our application wide instances as well.  Not nearly as exciting though because there are no Spider-Man references :/

#### Make Room

I chose to use room becuase it is **highly recommended** by the team at Google.  A tradeoff of using it is that it can be difficult to track relationships across entities and a little too much is done "auto-magically" with different annotated operations that carry out various queries.  Using Room also forces us to get a little creative with syncing with the application in the background as we do not have the luxery of wrapping our `SQLiteDatabase` in a `ContentProvider` and pluggin' that into a `SyncAdapter`

#### Where's the DataBinding?

For such a small application on a limited timeline I decided not to use DataBinding as it would increase the complexity of the application "somewhat" and I invisioned several occurances where we would have logic in our XML like with displaying specific images from enums.  This could be handled with two-way binding and `@InverseBindingAdapter`s but I felt like tackling these problems were outside of the scope of this application.
