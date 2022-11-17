buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://files.minecraftforge.net/maven")
        maven("https://repo.spongepowered.org/repository/maven-public/")
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
    }
    dependencies {
        classpath("net.minecraftforge.gradle:ForgeGradle:5.+")
        classpath("org.spongepowered:mixingradle:0.7-SNAPSHOT")
    }
}

rootProject.name = "Aurora"