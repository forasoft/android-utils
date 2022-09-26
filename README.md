## Modules

### Clean

Contains components that implement Clean Architecture patterns.

Notable examples:

* [UseCase](clean/src/main/java/com/forasoft/androidutils/clean/usecase/UseCase.kt)
* [FlowUseCase](clean/src/main/java/com/forasoft/androidutils/clean/usecase/FlowUseCase.kt)

Min SDK: 14

### Kotlin Common

Contains helpers related to Kotlin language.

Notable examples:

* [valueOrNull](kotlin/common/src/main/java/com/forasoft/androidutils/kotlin/common/nullability/ValueOrNull.kt)
  runs operation and returns its result catching expected exception.

### Kotlin Coroutines

Contains helpers related to Kotlin Coroutines library.

Notable examples:

* [JobHolder](kotlin/coroutines/src/main/java/com/forasoft/androidutils/kotlin/coroutines/JobHolder.kt)
  provides thread-safe methods to interact with `Job`.
* [OperationTracker](kotlin/coroutines/src/main/java/com/forasoft/androidutils/kotlin/coroutines/operationtracker/OperationTracker.kt)
  tracks ongoing operations.

### Platform Android

Contains helpers related to Android platform.

Notable examples:

* [Context extensions](platform/android/src/main/java/com/forasoft/androidutils/platform/android/Context.kt)

Min SDK: 14

### UI View

Contains helpers related to Android View framework.

Notable examples:

* [Text](ui/view/src/main/java/com/forasoft/androidutils/ui/view/Text.kt) allows to present text
  in different forms.
* [View.fade*](ui/view/src/main/java/com/forasoft/androidutils/ui/view/visibility/Fade.kt)
  extensions allow to show and hide views with fade effect.

Min SDK: 16

## Download

Write when the library is published on GitHub.
