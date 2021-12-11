package md.elway.mapper

class MapperBuilder {
    var binds = listOf<Bind<*, *>>()
    var converters = listOf<Convert<*, *>>()
}