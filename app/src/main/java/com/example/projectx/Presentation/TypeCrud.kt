
import com.example.projectx.dataLocal.DataClient
import java.io.Serializable

enum class TypeCrud {
    UPDATE,
    CREATE,
    DELETE,
    DELETE_ALL
}

data class ActionCrud(
    val client: DataClient?,
    val typeCrud: String
):Serializable