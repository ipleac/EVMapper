package md.elway.mapper

import kotlin.reflect.KParameter
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMembers

fun Any.getPropertyValue(param: KParameter) = param.name?.let { getPropertyValue(it) }

fun Any.getPropertyValue(name: String) = this::class
    .declaredMembers
    .filterIsInstance<KProperty1<*, *>>()
    .firstOrNull { it.name == name }
    ?.call(this)

fun applyConverter(input: Any, vararg converters: Convert<Any, Any>): Any {
    for (converter in converters) {
        try {
            return converter.convert.invoke(input)
        } catch (_: ClassCastException) {
        }
    }
    return input
}