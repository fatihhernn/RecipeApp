<table>
  <tr>
     <td>Onboarding</td>
     <td>Login And Register</td>
    <td>List of Recipes</td>
    <td>Detail of Recipe</td>
    <td>Your Favorites</td>
    <td>Settings</td>
  </tr>
  
  <tr>
    <td>
           <img src="https://github.com/fatihhernn/RecipeApp/blob/main/onBoardingScreen.jpg" width="600" height="300" alt=".android">
   </td>
   <td>
           <img src="https://github.com/fatihhernn/RecipeApp/blob/main/loginAndRegisterScreen.jpg" width="600" height="300" alt=".android">
   </td>
   <td>
           <img src="https://github.com/fatihhernn/RecipeApp/blob/main/listOfAllFoodsScreen.jpg" width="600"  height="300" alt=".android">
   </td>
   <td>
           <img src="https://github.com/fatihhernn/RecipeApp/blob/main/detailOfFood.jpg" width="600" height="300" alt=".android">
   </td>
   <td>
           <img src="https://github.com/fatihhernn/RecipeApp/blob/main/favoriteFoodsScreen.jpg" width="600" height="300" alt=".android">
   </td>
   <td>
           <img src="https://github.com/fatihhernn/RecipeApp/blob/main/otherOptionsScreen.jpg" width="600" height="300" alt=".android">
   </td>
 </table>

## Built With 🛠
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
  - [Room](https://developer.android.com/topic/libraries/architecture/room) - SQLite object mapping library.
  - [DataStore Preferences](https://developer.android.com/topic/libraries/architecture/datastore) - Jetpack DataStore is a data storage solution that allows you to store key-value pairs or typed objects with protocol buffers.
- [Dependency Injection](https://developer.android.com/training/dependency-injection) - 
  - [Hilt-Dagger](https://dagger.dev/hilt/) - Standard library to incorporate Dagger dependency injection into an Android application.
  - [Hilt-ViewModel](https://developer.android.com/training/dependency-injection/hilt-jetpack) - DI for injecting `ViewModel`.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Gson](https://github.com/google/gson) - Gson is a Java library that can be used to convert Java Objects into their JSON representation
- [Coil-kt](https://coil-kt.github.io/coil/) - An image loading library for Android backed by Kotlin Coroutines.
- [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started) - Navigation occurs between your app's destinations—that is, anywhere in your app to which users can navigate. These destinations are connected via actions.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.
- [Menus - Contextual Menu](https://developer.android.com/guide/topics/ui/menus)- Menus are a common user interface component in many types of applications. 
- [Shimmer Effect](https://github.com/omtodkar/ShimmerRecyclerView) - ShimmerRecyclerView is an custom RecyclerView library based on Facebook's Shimmer effect for Android library
- [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) - For writing Gradle build scripts using Kotlin.

## Architecture
This app uses [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture.

![](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)

# Package Structure
    
    com.fatihhernn.recipe   # Root Package
    .
    ├── data                # For data handling.
    │   ├── local           # Local Persistence Database. Room (SQLite) database
    |   │   ├── dao         # Data Access Object for Room   
    │   ├── remote          # Remote Data Handlers     
    |   │   ├── api         # Retrofit API for remote end point.
    │   └── repository      # Single source of data.
    |
    ├── model               # Model classes
    |
    ├── di                  # Dependency Injection             
    │   ├── builder         # Activity Builder
    │   ├── component       # DI Components       
    │   └── module          # DI Modules
    |
    ├── ui                  # Activity/View layer
    │   ├── base            # Base View
    │   ├── main            # Main Screen Activity & ViewModel
    |   │   ├── adapter     # Adapter for RecyclerView
    |   │   └── viewmodel   # ViewHolder for RecyclerView   
    │   └── details         # Detail Screen Activity and ViewModel
    |
    └── utils               # Utility Classes / Kotlin extensions
