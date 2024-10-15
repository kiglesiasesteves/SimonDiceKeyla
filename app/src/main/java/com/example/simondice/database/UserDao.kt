import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @get:Query("SELECT * FROM user")
    val allUsers: List<User?>?

    @Query("SELECT * FROM user WHERE name = :name")
    fun getUserByName(name: String?): User?

    @Insert
    fun insertUser(user: User?)

    @Delete
    fun deleteUser(user: User?)
}