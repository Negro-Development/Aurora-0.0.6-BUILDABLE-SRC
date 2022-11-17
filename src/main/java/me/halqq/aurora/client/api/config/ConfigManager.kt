// Decompiled with CFR 0.152
// Recompile with -Xlint for deprecapted features
package me.halqq.aurora.client.api.config

import com.google.gson.*
import me.halqq.aurora.client.api.command.CommandManager
import me.halqq.aurora.client.api.friend.FriendManager
import me.halqq.aurora.client.api.module.Module
import me.halqq.aurora.client.api.module.ModuleManager
import me.halqq.aurora.client.api.setting.SettingManager
import me.halqq.aurora.client.api.setting.settings.*
import java.awt.Color
import java.io.*
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.util.function.Consumer

class ConfigManager {
    fun saveConfigs() {
        try {
            if (!Files.exists(Paths.get(fileName))) {
                Files.createDirectories(Paths.get(fileName))
            } else if (!Files.exists(Paths.get(fileName + moduleName))) {
                Files.createDirectories(Paths.get(fileName + moduleName))
            }
            saveModules()
            saveEnabledModules()
            saveModuleKeybinds()
            saveCommandPrefix()
            saveFriendsList()
        } catch (ignored: IOException) {
        }
    }

    @Throws(IOException::class)
    private fun saveModules() {
        for (module in ModuleManager.INSTANCE.modules!!) {
            try {
                saveModuleDirect(module)
            } catch (ignored: IOException) {
            }
        }
    }

    @Throws(IOException::class)
    private fun saveModuleDirect(module: Module) {
        registerFiles(fileName + moduleName, module.name)
        val gson = GsonBuilder().setPrettyPrinting().create()
        val fileOutputStreamWriter =
            OutputStreamWriter(FileOutputStream(fileName + moduleName + module.name + ".json"), StandardCharsets.UTF_8)
        val moduleObject = JsonObject()
        val settingObject = JsonObject()
        moduleObject.add("Module", JsonPrimitive(module.name))
        for (setting in SettingManager.INSTANCE.getSettingsInModule(module)) {
            if (setting is SettingBoolean) {
                settingObject.add(setting.getName(), JsonPrimitive(setting.value))
            } else if (setting is SettingColor) {
                settingObject.add(setting.getName(), JsonPrimitive(setting.red))
                settingObject.add(setting.getName(), JsonPrimitive(setting.green))
                settingObject.add(setting.getName(), JsonPrimitive(setting.blue))
                settingObject.add(setting.getName() + "-alpha", JsonPrimitive(setting.alpha))
            } else if (setting is SettingDouble) {
                settingObject.add(setting.getName(), JsonPrimitive(setting.value))
            } else if (setting is SettingInteger) {
                settingObject.add(setting.getName(), JsonPrimitive(setting.value))
            } else if (setting is SettingMode) {
                settingObject.add(setting.getName(), JsonPrimitive(setting.value))
            } else if (setting is SettingString) {
                settingObject.add(setting.getName(), JsonPrimitive(setting.value))
            }
        }
        moduleObject.add("Settings", settingObject)
        val jsonString = gson.toJson(JsonParser().parse(moduleObject.toString()))
        fileOutputStreamWriter.write(jsonString)
        fileOutputStreamWriter.close()
    }

    @Throws(IOException::class)
    private fun saveEnabledModules() {
        registerFiles(fileName, "Toggle")
        val gson = GsonBuilder().setPrettyPrinting().create()
        val fileOutputStreamWriter =
            OutputStreamWriter(FileOutputStream(fileName + "Toggle" + ".json"), StandardCharsets.UTF_8)
        val moduleObject = JsonObject()
        val enabledObject = JsonObject()
        for (module in ModuleManager.INSTANCE.modules!!) {
            enabledObject.add(module.name, JsonPrimitive(module.isEnabled))
        }
        moduleObject.add("Modules", enabledObject)
        val jsonString = gson.toJson(JsonParser().parse(moduleObject.toString()))
        fileOutputStreamWriter.write(jsonString)
        fileOutputStreamWriter.close()
    }

    @Throws(IOException::class)
    private fun saveModuleKeybinds() {
        registerFiles(fileName, "Bind")
        val gson = GsonBuilder().setPrettyPrinting().create()
        val fileOutputStreamWriter =
            OutputStreamWriter(FileOutputStream(fileName + "Bind" + ".json"), StandardCharsets.UTF_8)
        val moduleObject = JsonObject()
        val bindObject = JsonObject()
        for (module in ModuleManager.INSTANCE.modules!!) {
            bindObject.add(module.name, JsonPrimitive(module.key))
        }
        moduleObject.add("Modules", bindObject)
        val jsonString = gson.toJson(JsonParser().parse(moduleObject.toString()))
        fileOutputStreamWriter.write(jsonString)
        fileOutputStreamWriter.close()
    }

    @Throws(IOException::class)
    private fun saveCommandPrefix() {
        registerFiles(fileName, "CommandPrefix")
        val gson = GsonBuilder().setPrettyPrinting().create()
        val fileOutputStreamWriter =
            OutputStreamWriter(FileOutputStream(fileName + "CommandPrefix" + ".json"), StandardCharsets.UTF_8)
        val prefixObject = JsonObject()
        prefixObject.add("Prefix", JsonPrimitive(CommandManager.INSTANCE!!.prefix))
        val jsonString = gson.toJson(JsonParser().parse(prefixObject.toString()))
        fileOutputStreamWriter.write(jsonString)
        fileOutputStreamWriter.close()
    }

    @Throws(IOException::class)
    private fun saveFriendsList() {
        registerFiles(fileName, "Friends")
        val gson = GsonBuilder().setPrettyPrinting().create()
        val fileOutputStreamWriter =
            OutputStreamWriter(FileOutputStream(fileName + "Friends" + ".json"), StandardCharsets.UTF_8)
        val mainObject = JsonObject()
        val friendArray = JsonArray()
        for (friend in FriendManager.friends) {
            friendArray.add(friend!!.username)
        }
        mainObject.add("Friends", friendArray)
        val jsonString = gson.toJson(JsonParser().parse(mainObject.toString()))
        fileOutputStreamWriter.write(jsonString)
        fileOutputStreamWriter.close()
    }

    fun loadConfigs() {
        try {
            loadModules()
            loadEnabledModules()
            loadModuleKeybinds()
            loadCommandPrefix()
            loadFriendsList()
        } catch (ignored: Exception) {
        }
    }

    private fun loadModules() {
        val moduleLocation = fileName + moduleName
        for (module in ModuleManager.INSTANCE.modules!!) {
            try {
                loadModuleDirect(moduleLocation, module)
            } catch (ignored: IOException) {
                println(module.name)
            }
        }
    }

    @Throws(IOException::class)
    fun loadModuleDirect(moduleLocation: String, module: Module) {
        if (!Files.exists(Paths.get(moduleLocation + module.name + ".json"))) {
            return
        }
        val inputStream = Files.newInputStream(Paths.get(moduleLocation + module.name + ".json"))
        val moduleObject: JsonObject
        moduleObject = try {
            JsonParser().parse(InputStreamReader(inputStream)).asJsonObject
        } catch (e: IllegalStateException) {
            return
        }
        if (moduleObject["Module"] == null) {
            return
        }
        val settingObject = moduleObject["Settings"].asJsonObject
        for (setting in SettingManager.INSTANCE.getSettingsInModule(module)) {
            val dataObject = settingObject[setting.name]
            try {
                if (dataObject != null && dataObject.isJsonPrimitive) {
                    if (setting is SettingBoolean) {
                        setting.value = dataObject.asBoolean
                    } else if (setting is SettingDouble) {
                        setting.value = dataObject.asDouble
                    } else if (setting is SettingInteger) {
                        setting.value = dataObject.asInt
                    } else if (setting is SettingMode) {
                        setting.value = dataObject.asString
                    } else if (setting is SettingString) {
                        setting.value = dataObject.asString
                    } else if (setting is SettingColor) {
                        val alphaObject = settingObject[setting.getName() + "-alpha"]
                        val a = Color(dataObject.asInt)
                        setting.value = Color(a.red, a.green, a.blue, alphaObject.asInt)
                    }
                }
            } catch (ignored: NumberFormatException) {
            }
        }
        inputStream.close()
    }

    @Throws(IOException::class)
    private fun loadEnabledModules() {
        val toggleLocation = fileName
        if (!Files.exists(Paths.get(toggleLocation + "Toggle" + ".json"))) {
            return
        }
        val inputStream = Files.newInputStream(Paths.get(toggleLocation + "Toggle" + ".json"))
        val moduleObject = JsonParser().parse(InputStreamReader(inputStream)).asJsonObject
        if (moduleObject["Modules"] == null) {
            return
        }
        val settingObject = moduleObject["Modules"].asJsonObject
        for (module in ModuleManager.INSTANCE.modules!!) {
            val dataObject = settingObject[module.name]
            if (dataObject != null && dataObject.isJsonPrimitive) {
                if (dataObject.asBoolean) {
                    try {
                        module.setEnabled()
                    } catch (ignored: NullPointerException) {
                    }
                }
            }
        }
        inputStream.close()
    }

    @Throws(IOException::class)
    private fun loadModuleKeybinds() {
        val bindLocation = fileName
        if (!Files.exists(Paths.get(bindLocation + "Bind" + ".json"))) {
            return
        }
        val inputStream = Files.newInputStream(Paths.get(bindLocation + "Bind" + ".json"))
        val moduleObject = JsonParser().parse(InputStreamReader(inputStream)).asJsonObject
        if (moduleObject["Modules"] == null) {
            return
        }
        val settingObject = moduleObject["Modules"].asJsonObject
        for (module in ModuleManager.INSTANCE.modules!!) {
            val dataObject = settingObject[module.name]
            if (dataObject != null && dataObject.isJsonPrimitive) {
                module.key = dataObject.asInt
            }
        }
        inputStream.close()
    }

    @Throws(IOException::class)
    private fun loadCommandPrefix() {
        val prefixLocation = fileName
        if (!Files.exists(Paths.get(prefixLocation + "CommandPrefix" + ".json"))) {
            return
        }
        val inputStream = Files.newInputStream(Paths.get(prefixLocation + "CommandPrefix" + ".json"))
        val mainObject = JsonParser().parse(InputStreamReader(inputStream)).asJsonObject
        if (mainObject["Prefix"] == null) {
            return
        }
        val prefixObject = mainObject["Prefix"]
        if (prefixObject != null && prefixObject.isJsonPrimitive) {
            CommandManager.INSTANCE.prefix = prefixObject.asString
        }
        inputStream.close()
    }

    @Throws(IOException::class)
    private fun loadFriendsList() {
        val friendLocation = fileName
        if (!Files.exists(Paths.get(friendLocation + "Friends" + ".json"))) {
            return
        }
        val inputStream = Files.newInputStream(Paths.get(friendLocation + "Friends" + ".json"))
        val mainObject = JsonParser().parse(InputStreamReader(inputStream)).asJsonObject
        if (mainObject["Friends"] == null) {
            return
        }
        val friendObject = mainObject["Friends"].asJsonArray
        friendObject.forEach(Consumer { `object`: JsonElement ->
            FriendManager.friends.add(
                FriendManager.getFriendObject(`object`.asString)
            )
        })
        inputStream.close()
    }

    @Throws(IOException::class)
    private fun registerFiles(location: String, name: String) {
        if (Files.exists(Paths.get("$location$name.json"))) {
            val file = File("$location$name.json")
            file.delete()
        }
        Files.createFile(Paths.get("$location$name.json"))
    }

    companion object {
        lateinit var INSTANCE: ConfigManager
        private const val fileName = "Aurora/"
        private const val moduleName = "modules/"
    }
}