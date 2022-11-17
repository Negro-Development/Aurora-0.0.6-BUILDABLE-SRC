import net.minecraftforge.gradle.userdev.UserDevExtension
import org.spongepowered.asm.gradle.plugins.MixinExtension
// gradle skiddado do joao :trollface:
plugins {
    idea
    java
    kotlin("jvm") version "1.7.10"
}

apply {
    plugin("net.minecraftforge.gradle")
    plugin("org.spongepowered.mixin")
}

repositories {
    mavenCentral()
    maven("https://repo.spongepowered.org/repository/maven-public/")
    maven("https://jitpack.io")
    maven("https://impactdevelopment.github.io/maven/")
}

println("Java: " + System.getProperty("java.version") + " JVM: " + System.getProperty("java.vm.version") + '(' + System.getProperty("java.vendor") + ") Arch: " + System.getProperty("os.arch"))

configure<UserDevExtension> {
    mappings("stable", "39-1.12")
    accessTransformer("src/main/resources/aurora_at.cfg")

    runs {
        create("client") {
            workingDirectory = project.file("run").path

            properties(
                    mapOf(
                            "forge.logging.markers" to "SCAN,REGISTRIES,REGISTRYDUMP",
                            "forge.logging.console.level" to "info",
                            "fml.coreMods.load" to "me.halqq.aurora.loader.MixinLoader",
                            "mixin.env.disableRefMap" to "true"
                    )
            )
        }
    }
}

val library: Configuration by configurations.creating

dependencies {
    fun ModuleDependency.exclude(moduleName: String): ModuleDependency {
        return exclude(mapOf("module" to moduleName))
    }

    fun minecraft(dependencyNotation: Any): Dependency? = "minecraft"(dependencyNotation)

    minecraft("net.minecraftforge:forge:1.12.2-14.23.5.2860")


    library("com.googlecode.json-simple:json-simple:1.1.1")
    library("club.minnced:java-discord-rpc:2.0.2")
    library("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.7.0")

    library("org.spongepowered:mixin:0.8.5-SNAPSHOT") {
        exclude("commons-io")
        exclude("gson")
        exclude("guava")
        exclude("launchwrapper")
        exclude("log4j-core")
    }

    annotationProcessor("org.spongepowered:mixin:0.8.5:processor") {
        exclude("gson")
    }
    implementation(library)
}

configure<MixinExtension> {
    defaultObfuscationEnv = "searge"
    add(sourceSets["main"], "mixins.aurora.refmap.json")
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
    }

    compileKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf(
                    "-Xopt-in=kotlin.RequiresOptIn",
                    "-Xopt-in=kotlin.contracts.ExperimentalContracts",
                    "-Xlambdas=indy",
                    "-Xjvm-default=all"
            )
        }
    }

    processResources {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        rename("(.+_at.cfg)", "META-INF/$1")
    }

    jar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest {
            attributes(
                    "FMLCorePluginContainsFMLMod" to "true",
                    "FMLCorePlugin" to "me.halqq.aurora.loader.MixinLoader",
                    "MixinConfigs" to "mixins.aurora.json",
                    "TweakClass" to "org.spongepowered.asm.launch.MixinTweaker",
                    "TweakOrder" to 0,
                    "ForceLoadAsMod" to "true",
                    "FMLAT" to "aurora_at.cfg"
            )
        }

        from(
                library.map {
                    if (it.isDirectory) it
                    else zipTree(it)
                }
        )

        exclude(
                "com/**",
                "junit/**",
                "org/hamcrest/**",
                "org/intellij/**",
                "org/jetbrains/**",
                "org/junit/**",
                "META-INF/maven/**",
                "META-INF/versions/**",
                "META-INF/*.kotlin_module"
        )
    }

    @Suppress("DEPRECATION")
    register<Jar>("clientJar") {
        from(zipTree("$buildDir/libs/Aurora.jar")) {
            include("me/**")
            exclude("dev/")
        }
        baseName = ""
        version = ""
        classifier = "client"
        exclude(
                "LICENSE.txt",
                "module-info.class",
                "META-INF/MUMFREY.RSA",
        )
    }

    @Suppress("DEPRECATION")
    register<Jar>("loaderJar") {
        from(zipTree("$buildDir/libs/Aurora.jar")) {
            exclude("me/**")
        }
        version = ""
        classifier = "loader"
        manifest {
            attributes(
                    "FMLCorePluginContainsFMLMod" to "true",
                    "FMLCorePlugin" to "dev.joaoshiozo.loader.LoaderCoreMod",
                    "MixinConfigs" to "mixins.aurora.json",
                    "TweakClass" to "org.spongepowered.asm.launch.MixinTweaker",
                    "TweakOrder" to 0,
                    "ForceLoadAsMod" to "true",
                    "FMLAT" to "aurora_at.cfg"
            )
        }
        exclude(
                "LICENSE.txt",
                "module-info.class",
                "META-INF/MUMFREY.RSA",
        )
    }

    named("build") {
        dependsOn("clientJar")
        dependsOn("loaderJar")
        findByName("clientJar")?.mustRunAfter("reobfJar")
        findByName("loaderJar")?.mustRunAfter("clientJar")
    }
}