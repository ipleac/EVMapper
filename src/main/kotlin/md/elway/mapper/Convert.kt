package md.elway.mapper

open class Convert<S, T>(val convert: (s: S) -> T)