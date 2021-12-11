package md.elway.mapper

class Bind<S, T>(val prop: Pair<String, String>, val convert: Convert<S, T>? = null)