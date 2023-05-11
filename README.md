# Android Utils

Android Utils is a library of useful helpers that allow to decrease amount of
boilerplate code in Android development. Helpers are divided into modules according to
their specifics.

[![](https://jitpack.io/v/forasoft/android-utils.svg)](https://jitpack.io/#forasoft/android-utils)

* [Android Utils](#android-utils)
* [Modules](#modules)
  * [Kotlin Common](#kotlin-common)
  * [Kotlin Coroutines](#kotlin-coroutines)
  * [Clean](#clean)
  * [Platform Android](#platform-android)
  * [UI View](#ui-view)
  * [UI Compose](#ui-compose)
  * [LogPecker](#logpecker)
  * [WebRTC](#webrtc)
  * [OkHttp](#okhttp)
* [Download](#download)

# Modules

## Kotlin Common

Contains helpers related to Kotlin language.

Notable examples:

* [valueOrNull](kotlin/common/src/main/java/com/forasoft/androidutils/kotlin/common/nullability/ValueOrNull.kt)
  runs operation and returns its result catching expected exception.
* [Size](kotlin/common/src/main/java/com/forasoft/androidutils/kotlin/common/size)
  information size units abstraction, allowing for basic arithmetic operations, conversions and
  formatting

#### Dependency

```kotlin
implementation("com.github.forasoft.android-utils:kotlin-common:<version>")
```

## Kotlin Coroutines

Contains helpers related to Kotlin Coroutines library.

Notable examples:

* [JobHolder](kotlin/coroutines/src/main/java/com/forasoft/androidutils/kotlin/coroutines/JobHolder.kt)
  provides thread-safe methods to interact with `Job`.
* [OperationTracker](kotlin/coroutines/src/main/java/com/forasoft/androidutils/kotlin/coroutines/operationtracker/OperationTracker.kt)
  tracks ongoing operations.

#### Dependency

```kotlin
implementation("com.github.forasoft.android-utils:kotlin-coroutines:<version>")
```

## Clean

Contains components that implement Clean Architecture patterns.

Notable examples:

* [UseCase](clean/src/main/java/com/forasoft/androidutils/clean/usecase/UseCase.kt)
* [FlowUseCase](clean/src/main/java/com/forasoft/androidutils/clean/usecase/FlowUseCase.kt)

#### Dependency

```kotlin
implementation("com.github.forasoft.android-utils:clean:<version>")
```

Min SDK: 14

## Platform Android

Contains helpers related to Android platform.

Notable examples:

* [Context extensions](platform/android/src/main/java/com/forasoft/androidutils/platform/android/Context.kt)
* [ToastLogger](platform/android/src/main/java/com/forasoft/androidutils/platform/android/ToastLogger.kt)
  displays messages with `Toast`s.
* [Timber.plantTree](platform/android/src/main/java/com/forasoft/androidutils/platform/android/TimberTree.kt)
  allows to plant custom logging trees.

#### Dependency

```kotlin
implementation("com.github.forasoft.android-utils:platform-android:<version>")
```

Min SDK: 14

## UI View

Contains helpers related to Android View framework.

Notable examples:

* [Text](ui/view/src/main/java/com/forasoft/androidutils/ui/view/Text.kt) allows to present text
  in different forms.
* [View.fade*](ui/view/src/main/java/com/forasoft/androidutils/ui/view/visibility/Fade.kt)
  extensions allow to show and hide views with fade effect.

#### Dependency

```kotlin
implementation("com.github.forasoft.android-utils:ui-view:<version>")
```

Min SDK: 16

## UI Compose

Contains helpers related to Jetpack Compose framework.

Notable examples:

* [Text](ui/compose/src/main/java/com/forasoft/androidutils/ui/compose/Text.kt) allows to present
  text in different forms.
* [Navigation helpers](ui/compose/src/main/java/com/forasoft/androidutils/ui/compose/navigation)
  constructs abstraction layer over Jetpack Navigation providing components such as
  `Destination` and `NavGraphBuilder` extensions.
* [Side-effects](ui/compose/src/main/java/com/forasoft/androidutils/ui/compose/effect)
  for frequently used actions such as observing Lifecycle events, requesting screen orientation,
  controlling system bars state, etc.
* [CollapsingTopBarLayout](ui/compose/src/main/java/com/forasoft/androidutils/ui/compose/collapsingtopbar/CollapsingTopBarLayout.kt)
  is Jetpack Compose implementation of CollapsingTopBar layout.

#### Dependency

```kotlin
implementation("com.github.forasoft.android-utils:ui-compose:<version>")
```

Min SDK: 21

## LogPecker

Library that automatically saves application logs on the device.

Default limits:

* 5 MB — the maximum size of a single log file. When the limit is reached, the current file is
  closed and a new one is created.
* 20 — the maximum number of log files that can be stored on the device at the same time.
  When the limit is reached, the oldest file will be deleted to free space for a new one.

Default limits can be overridden with resources. See [Customization](#customization)
paragraph for more information.

Log files are stored in the cache directory.

#### Usage

* Add the dependency for build types you need, for example, using `debugImplementation`.
* Run the application.
* Open Logs activity either via launcher icon or application main activity shortcut.

#### Customization

Override the following resources within your application to change LogPecker default behavior:

* `forasoftandroidutils_log_pecker_is_enabled` — boolean resource that specifies if LogPecker
  is enabled and should write logs. Default value is `true`.
* `forasoftandroidutils_log_pecker_is_dynamic_shortcut_enabled` — boolean resource that specifies
  if a dynamic shortcut that leads to LogPecker activity should be added to the application
  main activity. Default value is `true`.
* `forasoftandroidutils_log_pecker_file_max_size_bytes` — integer resource that specifies the
  maximum size of a single log file in bytes. When the limit is reached, the current file is closed
  and a new one is created. Default value is `5000000`, which corresponds to 5 MB.
* `forasoftandroidutils_log_pecker_file_max_count` — integer resource that specifies the maximum
  number of log files that can be stored on the device at the same time. When the limit is reached,
  the oldest file will be deleted to free space for a new one. Default value is `20`.

#### Dependency

```kotlin
debugImplementation("com.github.forasoft.android-utils:logpecker:<version>")
```

Min SDK: 14

## WebRTC

Contains helpers related to Android WebRTC library.

#### Dependency

```kotlin
implementation("com.github.forasoft.android-utils:webrtc:<version>")
```

Min SDK: 21

## OkHttp

Contains helpers related to OkHttp.

Notable examples:

* [LoggingInterceptor](thirdparty/okhttp/src/main/java/com/forasoft/androidutils/thirdparty/okhttp/logging/LoggingInterceptor.kt)
  for logging HTTP requests, responses and request exceptions in an opinionated way.
* [UriRequestBody](thirdparty/okhttp/src/main/java/com/forasoft/androidutils/thirdparty/okhttp/UriRequestBody.kt)
  quick way to convert the Uri's content to RequestBody

#### Dependency

```kotlin
implementation("com.github.forasoft.android-utils:thirdparty-okhttp:<version>")
```

Min SDK: 21

# Download

* Add Jitpack repository to the repository list in your project-level `settings.gradle` file
  ```kotlin
  dependencyResolutionManagement {
    repositories {
        // ...
        maven { url = URI.create("https://jitpack.io") }
    }
  }
  ```
* Add the dependency of the needed module
