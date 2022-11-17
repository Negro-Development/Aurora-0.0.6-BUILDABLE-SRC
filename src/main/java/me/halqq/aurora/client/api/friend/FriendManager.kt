// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.friend
object FriendManager {
    val friends: ArrayList<Friend?> = ArrayList<Friend?>()

    @JvmStatic

    fun isFriend(name: String?): Boolean {
        return friends.stream().anyMatch { friend: Friend? ->
            friend!!.username.equals(
                name,
                ignoreCase = true
            )
        }
    }
    @JvmStatic

    fun getFriendObject(name: String): Friend {
        return Friend(name)
    }

    @JvmStatic
    fun addfriend(name: String) {
        friends.add(Friend(name))
    }
    @JvmStatic
    fun removeFriend(name: String?) {
        friends.remove(getFriend(name))
    }
    @JvmStatic

    fun getFriend(name: String?): Friend? {
        for (friend in friends) {
            if (!friend!!.username.equals(name, ignoreCase = true)) continue
            return friend
        }
        return null
    }

        val friendname: ArrayList<String>
            get() {
                val friendNames = ArrayList<String>()
                friends.stream().forEach { friend: Friend? ->
                    friendNames.add(
                        friend!!.username
                    )
                }
                return friendNames
            }

    class Friend(var username: String)
}