package md.elway.mapper

import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.full.primaryConstructor

inline fun <reified T> Any.mapTo(init: MapperBuilder.() -> Unit): T {
    val builder = MapperBuilder().apply(init)
    val targetClass = (T::class as KClass<*>)
    val targetParameters = targetClass.primaryConstructor?.parameters ?: emptyList()
    val converters = builder.converters.map { it as Convert<Any, Any> }.toTypedArray()

    val argsMap: MutableMap<KParameter, Any?> = targetParameters
        .associateWith { prop -> getPropertyValue(prop)?.let { applyConverter(it, *converters) } }
        .toMutableMap()

    builder.binds.forEach { binder ->
        targetParameters.find { it.name == binder.prop.second }?.let { param ->
            getPropertyValue(binder.prop.first)?.let { value ->
                val converter = binder.convert as Convert<Any, Any>?
                argsMap[param] = converter?.let { applyConverter(value, it) } ?: value
            }
        }

    }

    return targetClass.primaryConstructor?.callBy(argsMap) as T
}