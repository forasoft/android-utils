# Android Utils

Android Utils is a library of useful helpers that allow to decrease amount of
boilerplate code in Android development. Helpers are divided into modules according to
their specifics.

# Modules

## Clean

Contains components that implement Clean Architecture patterns.

Notable examples:

* [UseCase](clean/src/main/java/com/forasoft/androidutils/clean/usecase/UseCase.kt)
* [FlowUseCase](clean/src/main/java/com/forasoft/androidutils/clean/usecase/FlowUseCase.kt)

Min SDK: 14

## Kotlin Common

Contains helpers related to Kotlin language.

Notable examples:

* [valueOrNull](kotlin/common/src/main/java/com/forasoft/androidutils/kotlin/common/nullability/ValueOrNull.kt)
  runs operation and returns its result catching expected exception.
* [Size](kotlin/common/src/main/java/com/forasoft/androidutils/kotlin/common/size)
  information size units abstraction, allowing for basic arithmetic operations, conversions and
  formatting

## Kotlin Coroutines

Contains helpers related to Kotlin Coroutines library.

Notable examples:

* [JobHolder](kotlin/coroutines/src/main/java/com/forasoft/androidutils/kotlin/coroutines/JobHolder.kt)
  provides thread-safe methods to interact with `Job`.
* [OperationTracker](kotlin/coroutines/src/main/java/com/forasoft/androidutils/kotlin/coroutines/operationtracker/OperationTracker.kt)
  tracks ongoing operations.

## Platform Android

Contains helpers related to Android platform.

Notable examples:

* [Context extensions](platform/android/src/main/java/com/forasoft/androidutils/platform/android/Context.kt)
* [ToastLogger](platform/android/src/main/java/com/forasoft/androidutils/platform/android/ToastLogger.kt)
  displays messages with `Toast`s.
* [Timber.plantTree](platform/android/src/main/java/com/forasoft/androidutils/platform/android/TimberTree.kt)
  allows to plant custom logging trees.

Min SDK: 14

## UI View

Contains helpers related to Android View framework.

Notable examples:

* [Text](ui/view/src/main/java/com/forasoft/androidutils/ui/view/Text.kt) allows to present text
  in different forms.
* [View.fade*](ui/view/src/main/java/com/forasoft/androidutils/ui/view/visibility/Fade.kt)
  extensions allow to show and hide views with fade effect.

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

Min SDK: 21

## WebRTC

Contains helpers related to Android WebRTC library.

Min SDK: 14

## OkHttp

Contains helpers related to OkHttp.

Notable examples:

* [LoggingInterceptor](thirdparty/okhttp/src/main/java/com/forasoft/androidutils/thirdparty/okhttp/logging/LoggingInterceptor.kt)
  for logging HTTP requests, responses and request exceptions in an opinionated way.
* [UriRequestBody](thirdparty/okhttp/src/main/java/com/forasoft/androidutils/thirdparty/okhttp/UriRequestBody.kt)
  quick way to convert the Uri's content to RequestBody

Min SDK: 21

# Download

Write when the library is published on GitHub.
