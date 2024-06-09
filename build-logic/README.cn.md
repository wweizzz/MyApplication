# 约定插件

`build-logic` 文件夹定义了项目特定的约定插件，用于保持常见模块配置的单一标准。

这种方法主要基于
[https://developer.squareup.com/blog/herding-elephants/](https://developer.squareup.com/blog/herding-elephants/)
和
[https://github.com/jjohannes/idiomatic-gradle](https://github.com/jjohannes/idiomatic-gradle)。

通过在 `build-logic` 中设置约定插件，我们可以避免重复的构建脚本设置、杂乱无章的 `subproject`
配置，而不会出现 `buildSrc` 目录的缺点。

`build-logic` 是一个包含的构建，如根目录下的 [`settings.gradle.kts`](../settings.gradle.kts) 中所配置的那样。

在 `build-logic` 中有一个 `convention` 模块，它定义了一组插件，所有普通模块都可以使用这些插件来配置自身。

`build-logic` 还包括一组 `Kotlin` 文件，用于在插件之间共享逻辑，这对于配置具有共享代码的 Android
组件（库与应用程序）非常有用。

这些插件是*可添加*和*可组合*的，并且尝试只完成单一的职责。然后，模块可以选择他们需要的配置。
如果有一次性的逻辑针对某个没有共享代码的模块，则最好直接在该模块的 `build.gradle`
中定义，而不是创建一个具有模块特定设置的约定插件。

当前约定插件列表包括：

- [`nowinandroid.android.application`](convention/src/main/kotlin/AndroidApplicationConventionPlugin.kt)
  ,
  [`nowinandroid.android.library`](convention/src/main/kotlin/AndroidLibraryConventionPlugin.kt),
  配置常见的 Android 和 Kotlin 选项。
- [`nowinandroid.android.application.compose`](convention/src/main/kotlin/AndroidApplicationComposeConventionPlugin.kt)
  ,
  [`nowinandroid.android.library.compose`](convention/src/main/kotlin/AndroidLibraryComposeConventionPlugin.kt):
  配置 Jetpack Compose 选项。