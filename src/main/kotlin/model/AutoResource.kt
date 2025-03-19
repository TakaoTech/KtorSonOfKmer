package model

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Resource("/auto")
@Serializable
class AutoResource {

    @Resource("")
    @Serializable
    class All(val parent: AutoResource = AutoResource())

    @Resource("{id}")
    @Serializable
    class ById(val parent: AutoResource = AutoResource(), val id: Int)

    @Resource("init")
    @Serializable
    class Init(val parent: AutoResource = AutoResource())
}