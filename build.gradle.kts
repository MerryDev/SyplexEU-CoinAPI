import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    kotlin("jvm") version "1.8.0"
    id("java")

    id("xyz.jpenilla.run-paper") version "2.0.1"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
}

group = "eu.syplex"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://eldonexus.de/repository/maven-public")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19.3-R0.1-SNAPSHOT")
    implementation("de.chojo.sadu", "sadu", "1.2.0")
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }

    runServer {
        minecraftVersion("1.19.3")
    }
}

kotlin {
    jvmToolchain(17)
}

bukkit {
    name = "CoinAPI"
    version = "1.0.0"
    main = "eu.eyplex.coinapi.CoinAPIPlugin"

    apiVersion = "1.19"
    author = "Merry"

    load = BukkitPluginDescription.PluginLoadOrder.STARTUP

    commands {
        register("coins") {
            description = "Main command of this api"
        }
    }
}