# EVMapper - Simple object mapper for Kotlin

### Simple mapping

```kotlin
data class AData(val p1: Int, val p2: Int)
data class BData(val p1: Int, val p2: Int)

val aData = AData(1, 2)
val bData = aData.mapTo<BData> {}
```

### Mapping with binding

```kotlin
data class AData(val p1: Int, val p2: Int)
data class BData(val p1: Int, val p3: Int)

val aData = AData(1, 2)
val bData = aData.mapTo<BData> {
    binds = listOf(
        Bind<Int, Int>("p2" to "p3")
    )
}
```

### Mapping with binding and type converting

```kotlin
data class AData(val p1: Int, val p2: Int)
data class BData(val p1: String, val p2: String)

val aData = AData(1, 2)
val bData = aData.mapTo<BData> {
    binds = listOf(
        Bind<Int, String>("p1" to "p1", Convert { it.toString() }),
        Bind<Int, String>("p2" to "p2", Convert { it.toString() })
    )
}
```

### Mapping global converting

```kotlin
data class AData(val p1: Instant, val p2: Instant)
data class BData(val p1: LocalDateTime, val p2: LocalDateTime)

val aData = AData(Instant.now(), Instant.now())
val bData = aData.mapTo<BData> {
    converters = listOf(
        Convert<Instant, LocalDateTime> { LocalDateTime.ofInstant(it, ZoneOffset.UTC) }
    )
}
```

## How to install

```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.ipleac:EVMapper:Tag'
}
```